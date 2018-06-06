package Datamodel;

public class AccessToken {

    private int id;
    private String accessString;

    public AccessToken(int id, String accessString) {
        this.id = id;
        this.accessString = accessString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessString() {
        return accessString;
    }

    public void setAccessString(String accessString) {
        this.accessString = accessString;
    }
}
