package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.model.Ticket;

public class TicketFactory {
    public static Ticket createTicket(Long studentId, Long eventId, Long promoCodeId, Double price) {
        if(price < 0) {
            return null;
        }
        if(eventId == null) {
            return null;
        }
        if(studentId == null) {
            return null;
        }

       Ticket ouTicket = new Ticket.Builder()
            .setEventId(eventId)
            .setPromoCodeId(promoCodeId)
            .setPrice(price)
            .build();

        return ouTicket;
    }
}
