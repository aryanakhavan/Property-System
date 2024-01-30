import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final String phone;

    public User(String username, String password, String phone) {
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

}
