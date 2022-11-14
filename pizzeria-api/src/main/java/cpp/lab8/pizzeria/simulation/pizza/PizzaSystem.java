package cpp.lab8.pizzeria.simulation.pizza;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.order.Order;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class PizzaSystem {
    @Autowired
    private DataTransferManager dtm;

    // list of pizzas in the system
    private List<Pizza> pizzas = new ArrayList<>();
    private int lastPizza = 0;

    public synchronized Pizza createPizza(int menuItem, int orderId) {
        Pizza pizza = new Pizza(++lastPizza, orderId, menuItem);
        pizza.setState(PizzaState.Idle);
        pizza.setIsTaken(false);
        pizzas.add(pizza);
        return pizza;
    }

    public synchronized Pizza getPizzaById(Integer id) throws NullPointerException {
        return this.pizzas.stream()
                .filter(order -> {
                    try { return order.getPizzaId().equals(id); }
                    catch (NullPointerException npe) { return false; }
                })
                .findAny().get();
    }

    public synchronized List<Pizza> getPizzasByOrderId(Integer id) throws NullPointerException {
        return this.pizzas.stream()
                .filter(pizza -> {
                    try { return pizza.getOrderId().equals(id); }
                    catch (NullPointerException npe) { return false; }
                }).collect(Collectors.toList());
    }
}
