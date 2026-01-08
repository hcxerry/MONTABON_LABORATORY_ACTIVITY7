import java.util.ArrayList;
import java.util.Iterator;

// Enum for Order Status
enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    CANCELLED
}

// Abstract Order class
abstract class Order {
    private int orderId;
    private double amount;
    private OrderStatus status;

    public Order(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = OrderStatus.PENDING;
    }

    // Abstract method
    public abstract void processOrder();

    // Concrete method
    public String getOrderSummary() {
        return "Order ID: " + orderId +
               ", Amount: " + amount +
               ", Status: " + status;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }

    // Setter with validation
    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            System.out.println("Invalid amount. Cannot be negative.");
        }
    }

    // Protected setter for subclasses
    protected void setStatus(OrderStatus status) {
        this.status = status;
    }
}

// Payment interface
interface Payable {
    void pay();
}

// Concrete OnlineOrder class
class OnlineOrder extends Order implements Payable {

    public OnlineOrder(int orderId, double amount) {
        super(orderId, amount);
    }

    @Override
    public void processOrder() {
        System.out.println("Processing online order ID: " + getOrderId());
    }

    @Override
    public void pay() {
        setStatus(OrderStatus.PAID);
        System.out.println("Order ID " + getOrderId() + " has been paid.");
    }
}

// Main Application
public class OrderApp {

    public static void main(String[] args) {

        ArrayList<Order> orders = new ArrayList<>();

        // Add three online orders
        orders.add(new OnlineOrder(101, 1500.00));
        orders.add(new OnlineOrder(102, 2500.00));
        orders.add(new OnlineOrder(103, 500.00));

        // Process orders
        for (Order order : orders) {
            order.processOrder();
        }

        // Cancel one order for testing
        ((OnlineOrder) orders.get(1)).setStatus(OrderStatus.CANCELLED);

        System.out.println("\nOrder List:");

        // Iterate and remove CANCELLED orders
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();

            System.out.println(order.getOrderSummary());

            if (order.getStatus() == OrderStatus.CANCELLED) {
                iterator.remove();
                System.out.println("Cancelled order removed.");
            }
        }
    }
}
