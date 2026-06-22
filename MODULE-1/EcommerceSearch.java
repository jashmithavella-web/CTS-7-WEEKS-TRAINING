import java.util.ArrayList;
import java.util.Scanner;
class Product {
    int id;
    String name;
    String category;

    Product(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}
public class EcommerceSearch {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(101, "iPhone 17", "Mobile"));
        products.add(new Product(102, "Samsung Galaxy S25", "Mobile"));
        products.add(new Product(103, "HP Laptop", "Electronics"));
        products.add(new Product(104, "Dell Laptop", "Electronics"));
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter product to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        System.out.println("\nSearch Results:");
        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword)) {
                System.out.println("ID: " + p.id +
                                   ", Name: " + p.name +
                                   ", Category: " + p.category);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products found.");
        }

        sc.close();
    }
}