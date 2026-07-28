package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.domain.PromoCode;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromoCodeFactory {

    public static PromoCode createPromoCode(
            String id,
            String code,
            String discountType,
            double value,
            String scopeType,
            int maxRedemptions,
            LocalDate startDate,
            LocalDate expiryDate) {

        if (id == null || id.isBlank())
            return null;

        if (code == null || code.isBlank())
            return null;

        if (discountType == null || discountType.isBlank())
            return null;

        if (!discountType.equalsIgnoreCase("FLAT")
                && !discountType.equalsIgnoreCase("PERCENTAGE"))
            return null;

        if (scopeType == null || scopeType.isBlank())
            return null;

        if (!scopeType.equalsIgnoreCase("PLATFORM")
                && !scopeType.equalsIgnoreCase("EVENT")
                && !scopeType.equalsIgnoreCase("FACULTY"))
            return null;

        if (value < 0)
            return null;

        if (maxRedemptions <= 0)
            return null;

        if (startDate == null || expiryDate == null)
            return null;

        if (expiryDate.isBefore(startDate))
            return null;

        return new PromoCode.Builder()
                .setId(id)
                .setCode(code)
                .setDiscountType(discountType)
                .setValue(value)
                .setScopeType(scopeType)
                .setMaxRedemptions(maxRedemptions)
                .setTimesUsed(0)
                .setStartDate(startDate)
                .setExpiryDate(expiryDate)
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .build();
    }
}
