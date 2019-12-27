package ru.cft.focusstart.sakharova.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class Message implements Serializable {

    private MessageType type;
    private String data;
    private String userName;
    private ZonedDateTime dateTime;

    public Message(MessageType type) {
        this.type = type;
    }

    public Message(MessageType type, String userName) {
        this.type = type;
        this.userName = userName;
    }

    public Message(MessageType type, String data, ZonedDateTime dateTime) {
        this.type = type;
        this.data = data;
        this.dateTime = dateTime;
    }

    public enum MessageType {
        AUTHORIZATION,
        NAME_APPROVED,
        NAME_DECLINED,
        CHAT_MESSAGE,
        USER_ADDED,
        USER_REMOVED,
        DISCONNECT
    }
}