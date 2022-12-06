package com.kisssusha.ksplugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CurrentWeatherAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        WeatherApi weatherApi = new WeatherApi();
        WeatherReport weatherReport1 = new WeatherReport();
        try {
            weatherReport1 = weatherApi.timelineRequestHttpClient();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        NotificationGroup ng = NotificationGroup.findRegisteredGroup("PerformancePlugin");
        Notifications.Bus.notify(new Notification(String.valueOf(ng),
                        String.format("Message was sent to %s\n%s", weatherReport1.getLocation(), weatherReport1.getCurrentWeather()),
                        NotificationType.INFORMATION),
                e.getProject());
    }
}