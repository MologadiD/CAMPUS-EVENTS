package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.domain.Notification;
import java.time.LocalDateTime;

public class NotificationFactory {

 public static Notification createNotification(String id,
                                                  String title,
                                                  String message) {

        if (id == null || id.isBlank())
            return null;

        if (title == null || title.isBlank())
            return null;

        if (message == null || message.isBlank())
            return null;

     return new Notification.Builder()
             .setId(id)
             .setTitle(title)
             .setMessage(message)
             .setRead(false)
             .setCreatedAt(LocalDateTime.now())
             .build();
    }
}