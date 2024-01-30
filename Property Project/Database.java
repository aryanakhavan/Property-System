import java.io.*;
import java.util.ArrayList;

public class Database implements Serializable {
    private static Database instance = null;
    private ArrayList<User> users;
    private ArrayList<Property> properties;

    public static Database getInstance() {
        if (instance == null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File("db"));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                instance = (Database) objectInputStream.readObject();
                for (Property property : instance.properties) {
                    Property.updateID(property.getId());
                }
            } catch (IOException | ClassNotFoundException e) {
                instance = new Database();
            }
        }
        return instance;
    }

    private Database() {
        users = new ArrayList<>();
        properties = new ArrayList<>();

    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("db");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }
}
