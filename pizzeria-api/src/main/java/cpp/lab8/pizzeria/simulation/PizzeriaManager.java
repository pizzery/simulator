package cpp.lab8.pizzeria.simulation;

import cpp.lab8.pizzeria.simulation.cook.CookSystem;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;
import cpp.lab8.pizzeria.simulation.pizza.PizzaSystem;
import cpp.lab8.pizzeria.simulation.queue.QueueSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cpp.lab8.pizzeria.simulation.configuration.PizzeriaConfiguration;
import cpp.lab8.pizzeria.simulation.customer.CustomerSystem;
import cpp.lab8.pizzeria.simulation.order.OrderSystem;
import lombok.Getter;
import lombok.Setter;

/**
 * Pizzeria management class
 * @implNote Facade and Singleton patterns were used 
 */
@Service
@Scope("singleton")
@Getter
@Setter
public class PizzeriaManager {
    // current configuration
    private PizzeriaConfiguration configuration;

    // all subsystems
    private final CustomerSystem customerSystem;
    private final OrderSystem orderSystem;
    private final PizzaSystem pizzaSystem;
    private final CookSystem cookSystem;
    private final QueueSystem queueSystem;
    @Autowired
    public PizzeriaManager(CustomerSystem cs, OrderSystem os,  PizzaSystem ps, CookSystem cks, QueueSystem qs) {
        customerSystem = cs;
        orderSystem = os;
        pizzaSystem = ps;
        cookSystem = cks;
        queueSystem = qs;
    }

    // start workflow with current configuration
    public void start() {
        customerSystem.startGeneration(configuration.getVisitorsTimeout());
        queueSystem.createQueues(configuration.getCashRegisters());

        // sleep to wait for pizza creation
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Pizza> pizzas = pizzaSystem.getPizzas();
        for (Pizza pizza : pizzas) {
            System.out.println(pizza);
        }

        cookSystem.createCooks(configuration.getCooks(), configuration.getCookStrategy());
    }

    public synchronized Pizza findPizzaToCook(PizzaState pizzaState){
        Pizza pizza = null;
        List<Pizza> allPizzas = pizzaSystem.getPizzas();

        for (int i = 0; i < allPizzas.size(); i++){
            pizza = allPizzas.get(i);
            if (pizza.getState() == pizzaState && !pizza.getIsTaken()){
                pizza.setIsTaken(true);
                break;
            }
        }

        return pizza;
    }
}
