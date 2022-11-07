package cpp.lab8.pizzeria.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cpp.lab8.pizzeria.simulation.configuration.PizzeriaConfiguration;
import cpp.lab8.pizzeria.simulation.customer.CustomerSystem;
import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.order.OrderSystem;
import lombok.Getter;
import lombok.Setter;

/**
 * Pizzeria management class
 * @implNote Facade and Singleton patterns were used 
 */
@Service
@Scope("singleton")
public class PizzeriaManager {
    // current configuration
    @Getter
    @Setter
    private PizzeriaConfiguration configuration;

    // all subsystems
    private final CustomerSystem customerSystem;
    private final OrderSystem orderSystem;

    @Autowired
    public PizzeriaManager(CustomerSystem cs, OrderSystem os) {
        customerSystem = cs;
        orderSystem = os;
    }

    // TODO: start workflow with current configuration
    public void start() {
        customerSystem.startGeneration(configuration.getVisitorsTimeout());
    }

    public void createOrderForCustomer(int customerId) {
        Order order = orderSystem.createOrder();
        customerSystem.assignOrder(customerId, order);
    }
}
