package cpp.lab8.pizzeria.simulation.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderSystem {

    // list of orders in the system
    private List<Order> orders = new ArrayList<>();
    private int lastOrder = 0;

    public synchronized void clear() {
        orders.clear();
        lastOrder = 0;
    }

    public synchronized Order createOrder() {
        Order order = new Order(++lastOrder);
        orders.add(order);
        return order;
    }
    
    public synchronized Order getOrderById(Integer id) throws NullPointerException {
        return this.orders.stream()
            .filter(order -> {
                try { return order.getId().equals(id); }
                catch (NullPointerException npe) { return false; }
            })
            .findAny().get();
    }
}
