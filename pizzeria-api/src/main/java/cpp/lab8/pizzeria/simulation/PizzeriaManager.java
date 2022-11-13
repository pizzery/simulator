package cpp.lab8.pizzeria.simulation;

import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.pizza.PizzaSystem;
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

    @Autowired
    public PizzeriaManager(CustomerSystem cs, OrderSystem os,  PizzaSystem ps) {
        customerSystem = cs;
        orderSystem = os;
        pizzaSystem = ps;
    }

    // TODO: start workflow with current configuration
    public void start() {
        customerSystem.startGeneration(configuration.getVisitorsTimeout());
    }
}
