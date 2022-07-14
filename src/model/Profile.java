package model;

public class Profile {
    private int profileId;
    private int userId;
    private String name;
    private String lastName;
    private String PhoneNumber;

    public Profile(int profileId, int userId, String name, String lastName, String phoneNumber) {
        this.profileId = profileId;
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        PhoneNumber = phoneNumber;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
