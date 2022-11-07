package cpp.lab8.pizzeria.simulation.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class OrderSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of orders in the system
    private List<Order> orders = new ArrayList<>();
    private int lastOrder = 0;

    public Order createOrder() {
        Order order = new Order(++lastOrder);
        orders.add(order);
        socket.broadcastNew("order", order);
        return order;
    }
    
}
