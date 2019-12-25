package ru.cft.focusstart.sakharova.client.common;

public interface ChatView extends View {

    void requestUserName();

    void processNameDeclined();

    void processSuccessAuthentication(String authorizedUserName);

    void showChatMessage(String message);

    void showNewUserAdditionMessage(String userName);

    void showUserRemovingMessage(String userName);

    void showMessageAboutConnectionFailure(boolean isUserNameWasAuthorized);

    void offerReconnection();
}

