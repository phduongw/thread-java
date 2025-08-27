package org.example.comsumerProceducer.chanllenge;

import java.util.ArrayList;
import java.util.List;

public class ShoeWarehouse {

    private final List<Order> shippingItems;
    public static final String[] PRODUCT_LIST = {
            "Running Shoes", "Sandals", "Boots", "Slippers", "High Tops"
    };

    public ShoeWarehouse() {
        this.shippingItems = new ArrayList<>();
    }

    public synchronized void receiveOrder(Order item) {
        while (shippingItems.size() > 20) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        shippingItems.add(item);
        System.out.println("Incoming order: " + item);
        notifyAll();
    }

    public synchronized Order fulfillOrder() {
        while (shippingItems.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        var item = shippingItems.remove(0);
        System.out.println("Fulfilling order: " + item);
        notifyAll();

        return item;
    }
}
