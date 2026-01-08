package com.store.main;

import com.store.order.*;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderApp {

    public static void main(String[] args) {

        ArrayList<Order> orders = new ArrayList<>();

        // Add at least three orders
        OnlineOrder o1 = new OnlineOrder(101, 1500.00);
        OnlineOrder o2 = new OnlineOrder(102, 2500.00);
        OnlineOrder o3 = new OnlineOrder(103, 500.00);

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        // Simulate processing
        o1.processOrder();
        o1.pay();

        // Manually cancel one order for testing
        o3.processOrder();
        // Access via protected method inside same package not allowed here
        // So we simulate cancellation via subclass behavior
        // (Alternative approach shown below)
        cancelOrder(o3);

        // Iterator traversal
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

    // Helper method to cancel order safely
    private static void cancelOrder(Order order) {
        if (order instanceof OnlineOrder) {
            ((OnlineOrder) order).processOrder();
            // Using subclass-protected access
            try {
                java.lang.reflect.Method method =
                        Order.class.getDeclaredMethod("setStatus", OrderStatus.class);
                method.setAccessible(true);
                method.invoke(order, OrderStatus.CANCELLED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// Cherry R. Montabon 
// AI THAT USE ChatGPT-5 
// Conversation of AI 
// 
