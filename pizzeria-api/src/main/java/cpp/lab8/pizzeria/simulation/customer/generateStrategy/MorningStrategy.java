package cpp.lab8.pizzeria.simulation.customer.generateStrategy;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.customer.Customer;
import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MorningStrategy implements ClientGenerator {
    @Override
    public void generateCustomer(PizzeriaManager pizzaManager) {
        Order order = pizzaManager.getOrderSystem().createOrder();
        Random rnd = new Random();
        int pizzaCount = rnd.nextInt(1, 2);

        for(int i = 0; i < pizzaCount; i++) {
            pizzaManager
                    .getPizzaSystem()
                    .createPizza(
                            rnd.nextInt(pizzaManager.getConfiguration().getMenuItems()),
                            order.getId()
                    );
        }
        pizzaManager.getCustomerSystem().createCustomer(order.getId());
    }
}
