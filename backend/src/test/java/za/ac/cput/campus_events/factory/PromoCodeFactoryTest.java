package za.ac.cput.campus_events.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.campus_events.domain.PromoCode;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromoCodeFactoryTest {

    @Test
    void createPromoCodeSuccessfully() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNotNull(promoCode);
        assertEquals("P001", promoCode.getId());
        assertEquals("WELCOME10", promoCode.getCode());
        assertEquals("PERCENTAGE", promoCode.getDiscountType());
        assertEquals(10.0, promoCode.getValue());
        assertEquals("PLATFORM", promoCode.getScopeType());
        assertEquals(100, promoCode.getMaxRedemptions());
        assertEquals(0, promoCode.getTimesUsed());
        assertTrue(promoCode.isActive());
        assertNotNull(promoCode.getCreatedAt());
    }

    @Test
    void shouldReturnNullWhenIdIsNull() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                null,
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenCodeIsBlank() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenDiscountTypeIsInvalid() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "INVALID",
                10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenScopeTypeIsInvalid() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "INVALID",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenValueIsNegative() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                -10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenMaxRedemptionsIsZero() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                0,
                LocalDate.now(),
                LocalDate.now().plusDays(30)
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenDatesAreNull() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                100,
                null,
                null
        );

        assertNull(promoCode);
    }

    @Test
    void shouldReturnNullWhenExpiryDateIsBeforeStartDate() {

        PromoCode promoCode = PromoCodeFactory.createPromoCode(
                "P001",
                "WELCOME10",
                "PERCENTAGE",
                10.0,
                "PLATFORM",
                100,
                LocalDate.now(),
                LocalDate.now().minusDays(1)
        );

        assertNull(promoCode);
    }
}