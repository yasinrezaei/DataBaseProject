package model;

public class SendingMethod {
    private int sendingMethodId;
    private String sendingMethodName;

    public SendingMethod(int sendingMethodId, String sendingMethodName) {
        this.sendingMethodId = sendingMethodId;
        this.sendingMethodName = sendingMethodName;
    }

    public int getSendingMethodId() {
        return sendingMethodId;
    }

    public void setSendingMethodId(int sendingMethodId) {
        this.sendingMethodId = sendingMethodId;
    }

    public String getSendingMethodName() {
        return sendingMethodName;
    }

    public void setSendingMethodName(String sendingMethodName) {
        this.sendingMethodName = sendingMethodName;
    }
}
