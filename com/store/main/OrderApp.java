package com.store.main;

import com.store.order.*;
import java.util.ArrayList;
import java.util.Iterator;

public class OrderApp {

    public static void main(String[] args) {

        ArrayList<Order> orders = new ArrayList<>();

        // Adding three orders
        OnlineOrder o1 = new OnlineOrder(101, 490.00);
        OnlineOrder o2 = new OnlineOrder(102, 357.00);
        OnlineOrder o3 = new OnlineOrder(103, 895.00);

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        // processing order
        o1.processOrder();
        o1.pay();

        // showcase how cancellation works via subclass behavior
        o3.processOrder();
        
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

    // Helper method to cancel order without issues
    private static void cancelOrder(Order order) {
        if (order instanceof OnlineOrder) {
            ((OnlineOrder) order).processOrder();
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
// https://chatgpt.com/share/6961c39b-0e84-8005-a7ce-7e31cca4c92e
