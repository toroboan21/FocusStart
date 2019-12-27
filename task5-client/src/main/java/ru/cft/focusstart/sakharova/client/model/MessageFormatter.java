package ru.cft.focusstart.sakharova.client.model;

import org.apache.commons.lang3.ObjectUtils;
import ru.cft.focusstart.sakharova.common.Message;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class MessageFormatter {

    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy - HH:mm";
    private static final String DATE_OPENING_SYMBOL = "[";
    private static final String DATE_CLOSING_SYMBOL = "]";
    private static final String USER_NAME_OPENING_SYMBOL = "<";
    private static final String USER_NAME_CLOSING_SYMBOL = "> ";

    String formatChatMessage(Message response) {
        ZonedDateTime responseDateTime = response.getDateTime();
        ZonedDateTime zonedDateTime = ObjectUtils.defaultIfNull(responseDateTime, ZonedDateTime.now()).
                withZoneSameInstant(ZoneId.systemDefault());
        String formattedDateTime = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(zonedDateTime);
        String author = response.getUserName();
        return DATE_OPENING_SYMBOL + formattedDateTime + DATE_CLOSING_SYMBOL +
                USER_NAME_OPENING_SYMBOL + author + USER_NAME_CLOSING_SYMBOL + response.getData();
    }
}
