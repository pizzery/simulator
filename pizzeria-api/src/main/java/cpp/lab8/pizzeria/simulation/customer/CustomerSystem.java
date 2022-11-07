package cpp.lab8.pizzeria.simulation.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class CustomerSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of customers in the system
    private List<Customer> customers = new ArrayList<>();
    private int lastCustomer = 0;

    // timeout generation
    private ScheduledExecutorService genExecutor;
    private ScheduledFuture<?> genFuture;

    public void startGeneration(int timeoutSec) {
        genExecutor = Executors.newScheduledThreadPool(2);
        genFuture = genExecutor.scheduleAtFixedRate(this::createCustomer, 0L, timeoutSec, TimeUnit.SECONDS);
    }

    public void stopGeneration() {
        genFuture.cancel(true);
    }

    /**
     * Factory method for customer generation
     * TODO: full customer generation
     */
    public Customer createCustomer() {
        Customer customer = null;
        customer = new Customer(++lastCustomer);
        customers.add(customer);
        socket.broadcastNew("customer", customer);
        return customer;
    }

    public Customer getCustomer(int id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst().get();
    }

    public void assignOrder(int customerId, Order order) {
        Customer customer = getCustomer(customerId);
        customer.setOrderId(order.getId());
        socket.broadcastUpdate("customer", customer);
    }
}
