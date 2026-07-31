package za.ac.cput.campus_events.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.campus_events.domain.Notification;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void createNotificationSuccessfully() {

        Notification notification = NotificationFactory.createNotification(
                "N001",
                "Registration Successful",
                "You have successfully registered."
        );

        assertNotNull(notification);
        assertEquals("N001", notification.getId());
        assertEquals("Registration Successful", notification.getTitle());
        assertEquals("You have successfully registered.", notification.getMessage());
        assertFalse(notification.getRead());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    void shouldReturnNullWhenIdIsNull() {

        Notification notification = NotificationFactory.createNotification(
                null,
                "Registration Successful",
                "You have successfully registered."
        );

        assertNull(notification);
    }

    @Test
    void shouldReturnNullWhenIdIsBlank() {

        Notification notification = NotificationFactory.createNotification(
                "",
                "Registration Successful",
                "You have successfully registered."
        );

        assertNull(notification);
    }

    @Test
    void shouldReturnNullWhenTitleIsNull() {

        Notification notification = NotificationFactory.createNotification(
                "N001",
                null,
                "You have successfully registered."
        );

        assertNull(notification);
    }

    @Test
    void shouldReturnNullWhenTitleIsBlank() {

        Notification notification = NotificationFactory.createNotification(
                "N001",
                "",
                "You have successfully registered."
        );

        assertNull(notification);
    }

    @Test
    void shouldReturnNullWhenMessageIsNull() {

        Notification notification = NotificationFactory.createNotification(
                "N001",
                "Registration Successful",
                null
        );

        assertNull(notification);
    }

    @Test
    void shouldReturnNullWhenMessageIsBlank() {

        Notification notification = NotificationFactory.createNotification(
                "N001",
                "Registration Successful",
                ""
        );

        assertNull(notification);
    }
}