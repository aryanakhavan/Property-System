import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Shop shop;

    private static int readInt(String msg) {
        while (true) {
            String str = readString(msg);
            try {
                int n = Integer.parseInt(str);
                return n;
            } catch (Exception e) {
                System.out.println("input must be an integer");
            }
        }
    }

    private static int readInt(String msg, boolean canBeEmpty) {
        if (canBeEmpty) {
            while (true) {
                String str = readString(msg, canBeEmpty);
                if (str.equals(""))
                    return -1;
                try {
                    int n = Integer.parseInt(str);
                    return n;
                } catch (Exception e) {
                    System.out.println("input must be an integer");
                }
            }
        }
        return readInt(msg);
    }

    private static String readString(String msg, boolean canBeEmpty) {
        if (canBeEmpty) {
            Scanner scanner = new Scanner(System.in);
            System.out.printf("%s", msg);
            return scanner.nextLine();
        }
        return readString(msg);
    }

    private static String readString(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s", msg);
        String str = scanner.nextLine();
        while (str.equals("")) {
            System.out.println("input cannot be empty");
            System.out.printf("%s", msg);
            str = scanner.nextLine();
        }
        return str;
    }

    private static String readString(String msg, String[] values) {
        while (true) {
            String str = readString(msg);
            for (String value : values) {
                if (value.equals(str))
                    return str;
            }
            System.out.println("invalid value, try again!");
        }
    }

    interface Menu {
        Menu show();
    }

    static class LoginMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--Login--");
            String username = readString("username: ");
            String password = readString("password: ");
            try {
                shop.login(username, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new MainMenu();
            }
            System.out.println("logged in successfully");
            return new Dashboard();
        }
    }

    static class RegisterMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--Register--");
            String username = readString("username: ");
            String password = readString("password: ");
            String phone = readString("phone: ");
            try {
                shop.register(username, password, phone);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new MainMenu();
            }
            System.out.println("registered successfully");
            return new MainMenu();
        }
    }

    static class MainMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--MainMenu--");
            System.out.print("1.Register\n2.Login\n3.Exit\n");
            String option = readString("Select an option: ", new String[]{"1", "2", "3"});
            if (option.equals("1")) {
                return new RegisterMenu();
            }
            if (option.equals("2")) {
                return new LoginMenu();
            }
            return null;
        }
    }

    static class AddPropertyMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--AddProperty--");
            String name = readString("name: ");
            String location = readString("location: ");
            int price = readInt("price: ");
            int area = readInt("area: ");
            String details = readString("details: ", true);
            try {
                shop.addProperty(name, location, price, area, details);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new Dashboard();
            }
            System.out.println("property added");
            return new Dashboard();
        }
    }

    static class ChangePropertyMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--ChangeProperty--");
            System.out.println("enter empty string for the fields you dont want to change");
            int id = readInt("id: ");
            String name = readString("name: ", true);
            String location = readString("location: ", true);
            int price = readInt("price: ", true);
            int area = readInt("area: ", true);
            String details = readString("details: ", true);
            try {
                shop.changeProperty(id, name, location, price, area, details);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new Dashboard();
            }
            System.out.println("property changed");
            return new Dashboard();
        }
    }

    static class SearchMenu implements Menu {
        @Override
        public Menu show() {
            System.out.println("--SearchProperty--");
            System.out.println("enter empty string for the fields you dont want to consider");
            ArrayList<String> locations = new ArrayList<>();
            String location = readString("location: ", true);
            while (!location.isEmpty()) {
                locations.add(location);
                location = readString("location: ", true);
            }
            int minPrice = readInt("min-price: ", true);
            int maxPrice = readInt("max-price: ", true);
            if (maxPrice == -1) {
                maxPrice = Integer.MAX_VALUE;
            }
            int minArea = readInt("min-area: ", true);
            int maxArea = readInt("max-area: ", true);
            if (maxArea == -1) {
                maxArea = Integer.MAX_VALUE;
            }
            try {
                List<Property> properties = shop.search(locations, minPrice, maxPrice, minArea, maxArea);
                for (Property property : properties)
                    System.out.println(property);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new Dashboard();
        }
    }

    static class Dashboard implements Menu {
        @Override
        public Menu show() {
            System.out.println("--Dashboard--");
            System.out.print("1.Show All Properties\n2.Search Properties\n3.Show My Properties\n4.Add Property\n5.Change Property\n6.Logout\n");
            String option = readString("Select an option: ", new String[]{"1", "2", "3", "4", "5", "6"});
            if (option.equals("1")) {
                try {
                    List<Property> properties = shop.getProperties();
                    for (Property property : properties)
                        System.out.println(property);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new Dashboard();
                }
                return new Dashboard();
            }
            if (option.equals("2")) {
                return new SearchMenu();
            }
            if (option.equals("3")) {
                try {
                    List<Property> properties = shop.getUserProperties();
                    for (Property property : properties)
                        System.out.println(property);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new Dashboard();
                }
                return new Dashboard();
            }
            if (option.equals("4")) {
                return new AddPropertyMenu();
            }
            if (option.equals("5")) {
                return new ChangePropertyMenu();
            }
            if (option.equals("6")) {
                try {
                    shop.logout();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new Dashboard();
                }
                System.out.println("logged out successfully");
                return new MainMenu();
            }
            return null;
        }
    }


    public static void main(String[] args) {
        shop = new Shop();
        Menu menu = new MainMenu();
        while (menu != null) {
            menu = menu.show();
            shop.save();
        }
    }
}