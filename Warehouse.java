import java.util.ArrayList;
import java.util.Scanner;

// --- Class 1: Item (Data Model) ---
class Item {
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

    // Getters and Setters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "ID: " + itemId + " | Name: " + itemName + " | Qty: " + quantity + " | Price: $" + price;
    }
}

// --- Class 2: Inventory (Logic using ArrayList) ---
class Inventory {
    // List to store item objects
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Item added to inventory.");
    }

    public boolean removeItem(String id) {
        // We must iterate to find the item with the matching ID
        for (Item item : items) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                items.remove(item);
                return true;
            }
        }
        return false;
    }

    public boolean updateStock(String id, int qty) {
        for (Item item : items) {
            if (item.getItemId().equalsIgnoreCase(id)) {
                item.setQuantity(qty);
                return true;
            }
        }
        return false;
    }

    public Item search(String query) {
        // Search by ID or Name
        for (Item item : items) {
            if (item.getItemId().equalsIgnoreCase(query) || item.getItemName().equalsIgnoreCase(query)) {
                return item;
            }
        }
        return null;
    }

    public void display() {
        if (items.isEmpty()) {
            System.out.println("Inventory is currently empty.");
        } else {
            System.out.println("\n--- Warehouse Inventory List ---");
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }
}

// --- Class 3: Warehouse (User Interface) ---
public class Warehouse {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Warehouse Menu ---");
            System.out.println("1. Add Item\n2. Remove Item\n3. Update Stock\n4. View All\n5. Search\n6. Exit");
            System.out.print("Select: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter ID: "); String id = sc.nextLine();
                    System.out.print("Enter Name: "); String name = sc.nextLine();
                    System.out.print("Enter Qty: "); int q = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Price: "); double p = Double.parseDouble(sc.nextLine());
                    inventory.addItem(new Item(id, name, q, p));
                    break;
                case "2":
                    System.out.print("Enter ID to remove: ");
                    if (inventory.removeItem(sc.nextLine())) System.out.println("Item removed.");
                    else System.out.println("Item not found.");
                    break;
                case "3":
                    System.out.print("Enter ID: "); String updateId = sc.nextLine();
                    System.out.print("Enter New Quantity: "); int newQty = Integer.parseInt(sc.nextLine());
                    if (inventory.updateStock(updateId, newQty)) System.out.println("Stock updated.");
                    else System.out.println("Item not found.");
                    break;
                case "4":
                    inventory.display();
                    break;
                case "5":
                    System.out.print("Enter ID or Name to search: ");
                    Item found = inventory.search(sc.nextLine());
                    if (found != null) System.out.println("Found: " + found);
                    else System.out.println("No matching item found.");
                    break;
                case "6":
                    System.out.println("Exiting System...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
