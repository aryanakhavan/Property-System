import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {
    private final ArrayList<User> users;
    private final ArrayList<Property> properties;

    private User currentUser;

    public Shop() {
        currentUser = null;
        users = Database.getInstance().getUsers();
        properties = Database.getInstance().getProperties();
    }

    public void save() {
        Database.getInstance().setProperties(properties);
        Database.getInstance().setUsers(users);
        Database.getInstance().save();
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    private Property findPropertyByID(int id) {
        for (Property property : properties) {
            if (property.getId() == id)
                return property;
        }
        return null;
    }

    private void checkLoggedIn() throws Exception {
        if (currentUser == null) {
            throw new Exception("error: login first");
        }
    }

    public void login(String username, String password) throws Exception {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new Exception("error: username not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new Exception("error: password is wrong");
        }
        currentUser = user;
    }

    public void register(String username, String password, String phone) throws Exception {
        if (findUserByUsername(username) != null) {
            throw new Exception("error: username in-use");
        }
        users.add(new User(username, password, phone));
    }

    public void logout() throws Exception {
        checkLoggedIn();
        currentUser = null;
    }

    public void addProperty(String name, String location, int price, int area, String details) throws Exception {
        checkLoggedIn();
        properties.add(new Property(name, location, price, area, currentUser, details));
    }

    public List<Property> getProperties() throws Exception {
        checkLoggedIn();
        return properties;
    }

    public List<Property> getUserProperties() throws Exception {
        checkLoggedIn();
        return properties.stream().filter(property -> property.getSeller().equals(currentUser)).collect(Collectors.toList());
    }

    public List<Property> search(List<String> locations, int min_price, int max_price, int min_area, int max_area) throws Exception {
        checkLoggedIn();
        return properties.stream().filter(
                property -> property.getPrice() >= min_price &&
                        property.getPrice() <= max_price &&
                        property.getArea() >= min_area &&
                        property.getArea() <= max_area &&
                        (locations.size() == 0 || locations.contains(property.getLocation()))
                        ).collect(Collectors.toList());
    }

    public void changeProperty(int id, String name, String location, int price, int area, String details) throws Exception {
        checkLoggedIn();
        Property property = findPropertyByID(id);
        if (property == null) {
            throw new Exception("error: property not found");
        }
        if (!property.getSeller().equals(currentUser)) {
            throw new Exception("error: this user cannot change this property");
        }
        property.setName(name);
        property.setArea(area);
        property.setDetails(details);
        property.setLocation(location);
        property.setPrice(price);
    }
}
