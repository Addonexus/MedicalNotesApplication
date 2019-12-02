package nsa.group4.medical.service;

import nsa.group4.medical.service.events.NotificationAdded;

public interface NotificationRepositoryInterface {
    public void saveNotification(NotificationAdded notificationAdded);
}
