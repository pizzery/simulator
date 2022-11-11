package cpp.lab8.pizzeria.simulation.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;

@Component
public class CustomerSystem {
    // socket to broadcast modifications
    @Autowired
    private DataTransferManager dtm;

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
        dtm.sendEntity(customer);
        return customer;
    }

    public synchronized Customer getCustomerById(Integer id) {
        return customers.stream()
            .filter(c -> c.getId().equals(id))
            .findAny().orElse(null);
    }

    // TODO: filter without errors if cusomer has null as orderId
    public synchronized Customer getCustomerByOrderId(Integer orderId) {
        return customers.stream()
            .filter(c -> c.getOrderId().equals(orderId))
            .findAny().orElse(null);
    }
}
