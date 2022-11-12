package cpp.lab8.pizzeria.simulation.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;

@Component
public class CustomerSystem {
    // data transer
    private final DataTransferManager dataTransferManager;
    // facade object access
    private final PizzeriaManager pizzaManager;

    @Autowired
    public CustomerSystem(@Lazy PizzeriaManager pizzaManager, 
                        DataTransferManager dataTransferManager) {
        this.pizzaManager = pizzaManager;
        this.dataTransferManager = dataTransferManager;
    }

    // list of customers in the system
    private List<Customer> customers = new ArrayList<>();
    private int lastCustomer = 0;

    // timeout generation
    private ScheduledExecutorService genExecutor;
    private ScheduledFuture<?> genFuture;

    public synchronized void startGeneration(int timeoutSec) {
        genExecutor = Executors.newScheduledThreadPool(2);
        genFuture = genExecutor.scheduleAtFixedRate(this::createCustomer, 0L, timeoutSec, TimeUnit.SECONDS);
    }

    public synchronized void stopGeneration() {
        genFuture.cancel(true);
    }

    /**
     * Factory method for customer generation
     * TODO: full customer generation
     */
    public synchronized Customer createCustomer() {
        Customer customer = null;
        customer = new Customer(++lastCustomer);
        customers.add(customer);
        // notify of the new customer
        dataTransferManager.sendEntity(customer);
        return customer;
    }

    public synchronized Customer getCustomerById(Integer id) {
        return customers.stream()
            .filter(c -> {
                try { return c.getId().equals(id); }
                catch (NullPointerException npe) { return false; }
            })
            .findAny().orElse(null);
    }

    // TODO: filter without errors if cusomer has null as orderId
    public synchronized Customer getCustomerByOrderId(Integer orderId) {
        return customers.stream()
            .filter(c -> {
                try { return c.getOrderId().equals(orderId); }
                catch (NullPointerException npe) { return false; }
            })
            .findAny().orElse(null);
    }
}
