package model;

public class Ticket {
    private int ticketId;
    private int ticketOrderId;
    private String ticketUserName;
    private String ticketOrderDate;

    public Ticket(int ticketId, int ticketOrderId, String ticketUserName, String ticketOrderDate) {
        this.ticketId = ticketId;
        this.ticketOrderId = ticketOrderId;
        this.ticketUserName = ticketUserName;
        this.ticketOrderDate = ticketOrderDate;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketOrderId() {
        return ticketOrderId;
    }

    public void setTicketOrderId(int ticketOrderId) {
        this.ticketOrderId = ticketOrderId;
    }

    public String getTicketUserName() {
        return ticketUserName;
    }

    public void setTicketUserName(String ticketUserName) {
        this.ticketUserName = ticketUserName;
    }

    public String getTicketOrderDate() {
        return ticketOrderDate;
    }

    public void setTicketOrderDate(String ticketOrderDate) {
        this.ticketOrderDate = ticketOrderDate;
    }
}
