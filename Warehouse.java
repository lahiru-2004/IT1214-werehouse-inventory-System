import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Warehouse {

    private Inventory inventory;
    private Scanner scanner;

    public Warehouse() {
        this.inventory = new Inventory();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();
            handleUserChoice(choice);
        } while (choice != 0);
        scanner.close();
        System.out.println("Exiting Warehouse Inventory Management System. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\n--- Warehouse Inventory Management System ---");
        System.out.println("1. Add New Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Update Item Quantity");
        System.out.println("4. Search Item by ID");
        System.out.println("5. Search Item by Name");
        System.out.println("6. Display All Items");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Consume the invalid input
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        return choice;
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addNewItem();
                break;
            case 2:
                removeItem();
                break;
            case 3:
                updateItemQuantity();
                break;
            case 4:
                searchItemById();
                break;
            case 5:
                searchItemByName();
                break;
            case 6:
                inventory.displayAllItems();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addNewItem() {
        System.out.print("Enter Item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();
        int quantity = -1;
        double price = -1.0;

        while (quantity < 0) {
            try {
                System.out.print("Enter Quantity: ");
                quantity = scanner.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Please enter a non-negative number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for quantity.");
                scanner.next(); // Consume the invalid input
            }
            scanner.nextLine(); // Consume newline
        }

        while (price < 0) {
            try {
                System.out.print("Enter Price: ");
                price = scanner.nextDouble();
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a non-negative number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for price.");
                scanner.next(); // Consume the invalid input
            }
            scanner.nextLine(); // Consume newline
        }

        Item newItem = new Item(itemId, itemName, quantity, price);
        inventory.addItem(newItem);
    }

    private void removeItem() {
        System.out.print("Enter Item ID to remove: ");
        String itemId = scanner.nextLine();
        inventory.removeItem(itemId);
    }

    private void updateItemQuantity() {
        System.out.print("Enter Item ID to update quantity: ");
        String itemId = scanner.nextLine();
        int quantityChange = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter quantity change (e.g., 5 for add, -3 for remove): ");
                quantityChange = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for quantity change.");
                scanner.next(); // Consume the invalid input
            }
            scanner.nextLine(); // Consume newline
        }
        inventory.updateItemQuantity(itemId, quantityChange);
    }

    private void searchItemById() {
        System.out.print("Enter Item ID to search: ");
        String itemId = scanner.nextLine();
        Item foundItem = inventory.searchItemById(itemId);
        if (foundItem != null) {
            System.out.println("Item Found: " + foundItem);
        } else {
            System.out.println("Item with ID " + itemId + " not found.");
        }
    }

    private void searchItemByName() {
        System.out.print("Enter Item Name to search: ");
        String itemName = scanner.nextLine();
        Item foundItem = inventory.searchItemByName(itemName);
        if (foundItem != null) {
            System.out.println("Item Found: " + foundItem);
        } else {
            System.out.println("Item with Name " + itemName + " not found.");
        }
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.start();
    }

    // Item Class (Nested)
    static class Item {
        private String itemId;
        private String itemName;
        private int quantity;
        private double price;

        public Item(String itemId, String itemName, int quantity, double price) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Item ID: " + itemId + ", Name: " + itemName + ", Quantity: " + quantity + ", Price: $" + String.format("%.2f", price);
        }
    }

    // Inventory Class (Nested)
    static class Inventory {
        private ArrayList<Item> items;

        public Inventory() {
            this.items = new ArrayList<>();
        }

        public void addItem(Item item) {
            boolean exists = false;
            for (Item existingItem : items) {
                if (existingItem.getItemId().equals(item.getItemId())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                System.out.println("Item with ID " + item.getItemId() + " already exists. Use updateItemQuantity to modify.");
            } else {
                items.add(item);
                System.out.println("Item " + item.getItemName() + " added successfully.");
            }
        }

        public void removeItem(String itemId) {
            Iterator<Item> iterator = items.iterator();
            boolean removed = false;
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (item.getItemId().equals(itemId)) {
                    iterator.remove();
                    System.out.println("Item " + item.getItemName() + " removed successfully.");
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                System.out.println("Item with ID " + itemId + " not found.");
            }
        }

        public void updateItemQuantity(String itemId, int quantityChange) {
            Item foundItem = null;
            for (Item item : items) {
                if (item.getItemId().equals(itemId)) {
                    foundItem = item;
                    break;
                }
            }

            if (foundItem != null) {
                int newQuantity = foundItem.getQuantity() + quantityChange;
                if (newQuantity >= 0) {
                    foundItem.setQuantity(newQuantity);
                    System.out.println("Quantity for item " + foundItem.getItemName() + " updated to " + newQuantity + ".");
                } else {
                    System.out.println("Error: Quantity cannot be negative. Current quantity: " + foundItem.getQuantity());
                }
            } else {
                System.out.println("Item with ID " + itemId + " not found.");
            }
        }

        public Item searchItemById(String itemId) {
            for (Item item : items) {
                if (item.getItemId().equals(itemId)) {
                    return item;
                }
            }
            return null;
        }

        public Item searchItemByName(String itemName) {
            for (Item item : items) {
                if (item.getItemName().equalsIgnoreCase(itemName)) {
                    return item;
                }
            }
            return null;
        }

        public void displayAllItems() {
            if (items.isEmpty()) {
                System.out.println("Inventory is empty.");
            } else {
                System.out.println("\n--- Current Inventory ---");
                for (Item item : items) {
                    System.out.println(item);
                }
                System.out.println("-------------------------");
            }
        }
    }
}
