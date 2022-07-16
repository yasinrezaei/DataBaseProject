package model;

public class Chat {
    private int chatId;
    private int chatTicketId;
    private String chatText;
    private String chatUserName;

    public Chat(int chatId, int chatTicketId, String chatText, String chatUserName) {
        this.chatId = chatId;
        this.chatTicketId = chatTicketId;
        this.chatText = chatText;
        this.chatUserName = chatUserName;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getChatTicketId() {
        return chatTicketId;
    }

    public void setChatTicketId(int chatTicketId) {
        this.chatTicketId = chatTicketId;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String  chatText) {
        this.chatText = chatText;
    }

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName;
    }
}
