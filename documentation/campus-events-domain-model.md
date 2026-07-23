# Campus events domain model : business rules, methods, and layering

package structure: `domain` (entity + embeddable classes, Builder pattern, only self-contained logic), `factory` (constructs entities via Builder), `repository` (JPA persistence + queries), `service` (business rules that need to check _other_ entities), `controller` (REST endpoints).

**Rule of thumb**: if a method only reads/writes the entity's own fields, it stays in `domain`. If it needs to check another entity, count rows, or enforce a cross-entity rule, it belongs in `service` — the domain object shouldn't reach into a repository itself.

---

## Student — `domain.Student`

**Business rules**

- `email`, `firstName`, `lastName`, `studentNumber` mandatory; return `null` on construction rather than throw if missing.
- `studentNumber` unique (enforced at DB + service level, not something `Student` can check itself).
- `verificationStatus` defaults `PENDING`; only `Admin` moves it to `APPROVED`/`REJECTED`.
- `facultyId` — the faculty the student is enrolled in (drives faculty-scoped promo discounts).
- Cannot buy a `Ticket` while `verificationStatus != APPROVED` — checked in `service`, not on `Student` itself, since it also needs to check the `Event`'s state.

**Domain methods** (self-contained, live on the entity)

- `+getId/getFirstName/getLastName/getEmail/getStudentNumber/getVerificationStatus/getFaculty/getCreatedAt`
- `+toString() : String`

**Service methods** (`StudentService` — need other entities)

- `+registerForEvent(studentId, eventId) : Ticket` — checks student's `verificationStatus`, event's `isFull()`, faculty-scoped promo eligibility, then creates the `Ticket` via `TicketService`/`TicketFactory`.
- `+cancelTicket(ticketId) : void` — delegates to `TicketService.cancel()`.

---

## Organiser — `domain.Organiser`

**Business rules**

- `email` mandatory, unique.
- `verificationStatus` defaults `PENDING` — set on self-registration when requesting to join a `Faculty`.
- Cannot create/update/close an `Event` until `verificationStatus = APPROVED` **and** their linked `Faculty.status = ACTIVE`.

**Domain methods**

- `+getId/getFirstName/getLastName/getEmail/getVerificationStatus/getFaculty/getCreatedAt`
- `+toString() : String`

**Service methods** (`OrganiserService`)

- `+registerOrganiser(organiser, facultyId) : Organiser` — validates the faculty exists and is `ACTIVE` before saving.
- `+createEvent(organiserId, event) : Event` — checks `verificationStatus` and faculty status first; this is where "can this organiser actually act" gets enforced, not on `Organiser` or `Event` directly.
- `+updateEvent(...) / closeEvent(...) : void` — same gate.

---

## Faculty — `domain.Faculty`

**Business rules**

- Created directly by `Admin` — no self-registration, no approval-of-itself.
- `status` is `ACTIVE`/`INACTIVE`, toggleable later by Admin (e.g. to freeze a faculty without deleting its history).
- `name` unique, mandatory.
- `createdByAdminId` — audit trail of which admin set it up.

**Domain methods**

- `+getId/getName/getStatus/getContactEmail/getCreatedByAdmin/getCreatedAt`
- `+toString() : String`

**Service methods** (`FacultyService`)

- `+deactivate(facultyId) : void` — also needs to decide what happens to that faculty's pending organisers/open events; that decision logic belongs here, not on `Faculty`.

---

## Admin — `domain.Admin`

**Business rules**

- No self-approval loop — Admin doesn't need approving to create a Faculty.
- Approval actions should be logged with a timestamp at the point of the call (in the service, not stored redundantly on `Admin`).

**Domain methods**

- `+getId/getFirstName/getLastName/getEmail/getCreatedAt`
- `+toString() : String`

**Service methods** (`AdminService`)

- `+createFaculty(faculty) : Faculty`
- `+approveOrganiser(organiserId) : void`
- `+approveStudent(studentId) : void`

Note: `createFaculty`/`approveOrganiser`/`approveStudent` were listed as domain methods on `Admin` in earlier drafts of this diagram — properly, they belong in `AdminService` since they touch `Faculty`/`Organiser`/`Student` rows the `Admin` object has no direct reference to.

---

## Event — `domain.Event`

**Business rules**

- `capacity` mandatory, > 0; cannot exceed the linked `Venue.capacity`.
- `open` defaults `true`; flips to `false` automatically once issued tickets hit `capacity`.
- `eventDate` cannot be in the past at creation.
- Belongs to one `Venue`, one `Organiser`, one `Faculty`.
- Cancelling notifies every student holding a non-cancelled `Ticket`.

**Domain methods**

- `+getId/getTitle/getDescription/getEventDate/getCapacity/isOpen/getVenue/getOrganiser/getFaculty/getCreatedAt`
- `+isFull() : boolean` — self-contained (compares own capacity to a passed-in ticket count, or a cached counter field).
- `+closeRegistration()/reopenRegistration() : void`
- `+toString() : String`

**Service methods** (`EventService`)

- `+registerStudent(eventId, studentId) : Ticket` — the real orchestration: checks `isFull()`, checks student verification, applies promo if present, creates the `Ticket`.
- `+cancelEvent(eventId) : void` — cascades to notifying all ticket holders.

---

## Venue — `domain.Venue`

**Business rules**

- `capacity` mandatory, > 0.
- `address` mandatory and validated as a unit — a `Venue` cannot save with a partially-filled `Address`.

**Domain methods**

- `+getId/getName/getCapacity/getAddress`
- `+toString() : String`

No service-layer rules beyond standard CRUD — nothing here needs cross-entity checks.

---

## Address — `domain.Address` _(`@Embeddable`)_

No `id`, no own table — flattened into `venue`'s columns. Not one of the 9 persisted entities.

**Business rules**

- All fields mandatory; a blank field anywhere makes the whole `Venue` invalid.
- `postalCode` digits-only, correct length.
- Value-object equality (`equals`/`hashCode` by field values, not reference).

**Domain methods**

- `+getStreet/getSuburb/getCity/getPostalCode/getProvince`
- `+toString() : String`

---

## Ticket — `domain.Ticket`

**Business rules**

- `price` defaults `0` for free events; never negative.
- `status`: `ISSUED → CHECKED_IN` or `ISSUED → CANCELLED`, never back from `CANCELLED`.
- Unique on (`studentId`, `eventId`) unless the event explicitly allows repeats.
- A linked `PromoCode` must be `active`, within its date window, and under `maxRedemptions` **at the moment of issue** — this check can't live on `Ticket` alone.

**Domain methods**

- `+getId/getPrice/getStatus/getStudent/getEvent/getPromoCode/getCreatedAt`
- `+cancel()/checkIn() : void`
- `+isActive() : boolean`
- `+toString() : String`

**Service methods** (`TicketService`)

- `+issue(studentId, eventId, promoCode?) : Ticket` — validates promo eligibility (including the "use once per student" check, which needs a repository query across existing tickets), increments `PromoCode.timesUsed` atomically, then creates the ticket.

---

## PromoCode — `domain.PromoCode`

**Business rules**

- `code` unique, mandatory.
- `discountType`: `FLAT` or `PERCENTAGE` — exactly one applies to `value`.
- `scopeType`: `PLATFORM` (no event/faculty set) / `EVENT` (`eventId` set) / `FACULTY` (`facultyId` set — auto-discounts any student in that faculty, any event).
- `maxRedemptions` / `timesUsed` — must increment atomically to avoid a race on the last slot.
- Per-student "use once" cannot be checked from fields on `PromoCode` alone — needs a repository query for existing tickets referencing this code + student, done in `service`.
- Valid only within `[startDate, expiryDate]` and while `active = true`.
- Only one `PromoCode` per `Ticket`.

**Domain methods**

- `+getId/getCode/getDiscountType/getValue/getScopeType/getEvent/getFaculty/getMaxRedemptions/getTimesUsed/getStartDate/getExpiryDate/isActive`
- `+isValidNow() : boolean` — self-contained date/active check.
- `+applyTo(ticket) : void` — self-contained calculation once eligibility is already confirmed.
- `+toString() : String`

**Service methods** (`PromoCodeService`)

- `+validateForStudent(promoCodeId, studentId, eventId) : boolean` — the cross-entity eligibility check (scope match, redemption count, prior usage).

---

## Notification — `domain.Notification`

**Business rules**

- System-generated only (ticket issued, event updated/cancelled) — not user-authored.
- `read` defaults `false`, only flips forward via `markAsRead()`.
- Belongs to exactly one `Student`.

**Domain methods**

- `+getId/getTitle/getMessage/isRead/getStudent/getCreatedAt`
- `+markAsRead() : void`
- `+toString() : String`

**Service methods** (`NotificationService`)

- `+notifyTicketHolders(eventId, message) : void` — fans out to every student with an active ticket for that event; this is orchestration, not something a single `Notification` instance can do.
