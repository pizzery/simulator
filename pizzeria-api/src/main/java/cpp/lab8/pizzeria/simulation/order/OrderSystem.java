package cpp.lab8.pizzeria.simulation.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;

@Component
public class OrderSystem {
    @Autowired
    private DataTransferManager dtm;

    // list of orders in the system
    private List<Order> orders = new ArrayList<>();
    private int lastOrder = 0;

    public synchronized Order createOrder() {
        Order order = new Order(++lastOrder);
        orders.add(order);
        dtm.sendEntity(order);
        return order;
    }
    
    public synchronized Order getOrderById(Integer id) throws NullPointerException {
        return this.orders.stream()
            .filter(order -> id == order.getId())
            .findAny().get();
    }
}
