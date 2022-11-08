package cpp.lab8.pizzeria.simulation;

import cpp.lab8.pizzeria.simulation.queue.Queue;
import cpp.lab8.pizzeria.simulation.queue.QueueSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cpp.lab8.pizzeria.simulation.configuration.PizzeriaConfiguration;
import cpp.lab8.pizzeria.simulation.customer.CustomerSystem;
import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.order.OrderSystem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @Getter
    private final QueueSystem queueSystem;

    @Autowired
    public PizzeriaManager(CustomerSystem cs, OrderSystem os, QueueSystem qs) {
        customerSystem = cs;
        orderSystem = os;
        queueSystem = qs;
    }

    // TODO: start workflow with current configuration
    public void start() {
        customerSystem.startGeneration(configuration.getVisitorsTimeout());
        queueSystem.createQueues(configuration.getCashRegisters());
    }

    public void createOrderForCustomer(int customerId) {
        Order order = orderSystem.createOrder();
        customerSystem.assignOrder(customerId, order);
    }

    public List<Queue> getAllQueues(){
        return queueSystem.getQueues();
    }
}
