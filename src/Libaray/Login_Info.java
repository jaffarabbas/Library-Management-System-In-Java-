package Libaray;

import javafx.beans.property.SimpleStringProperty;

public class Login_Info {
    private final SimpleStringProperty id,user,pass;

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getUser() {
        return user.get();
    }


    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public Login_Info(String id, String user, String pass) {
        this.id = new SimpleStringProperty(id);
        this.user = new SimpleStringProperty(user);
        this.pass = new SimpleStringProperty(pass);
    }
}
