package cpp.lab8.pizzeria.simulation.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cpp.lab8.pizzeria.simulation.customer.generateStrategy.ClientGenerator;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.EveningStrategy;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.MorningStrategy;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.RushHourStrategy;
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
    // strategy for generating clients
    private ClientGenerator clientGenerator;

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
    // private ScheduledExecutorService genExecutor;
    // private ScheduledFuture<?> genFuture;
    Timer timer;

    public synchronized void startGeneration(int timeoutSec) {
        // genExecutor = Executors.newScheduledThreadPool(2);
        // genFuture = genExecutor.scheduleAtFixedRate(this::generateClients, 0L, timeoutSec, TimeUnit.SECONDS);
        timer = new Timer();
        timer.schedule(new TimerTask() { 
            @Override
            public void run() {
                generateClients();
            }
        }, 0, 1000 * timeoutSec);
    }

    public synchronized void stopGeneration() {
        // genFuture.cancel(true);
    }

    public synchronized void clear() {
        this.customers.clear();
        lastCustomer = 0;
    }

    /**
     * Factory method for customer generation
     */
    public synchronized Customer createCustomer(Integer orderID) {
        Customer customer = null;
        customer = new Customer(++lastCustomer);
        customer.setOrderId(orderID);
        customers.add(customer);
        // place customer in queue
        pizzaManager.chooseQueue(customer);
        // notify of the new customer
        dataTransferManager.sendEntity(customer);
        return customer;
    }

    public synchronized void generateClients() {
        int strategy = this.pizzaManager.getConfiguration().getStrategy();
        if (strategy == 0) {
            this.clientGenerator = new MorningStrategy();
        } else if (strategy == 1) {
            this.clientGenerator = new RushHourStrategy();
        } else if (strategy == 2) {
            this.clientGenerator = new EveningStrategy();
        } else {
            this.clientGenerator = new MorningStrategy();
        }
        this.clientGenerator.generateCustomer(this.pizzaManager);
    }

    public synchronized Customer getCustomerById(Integer id) {
        return customers.stream()
            .filter(c -> {
                try { return c.getId().equals(id); }
                catch (NullPointerException npe) { return false; }
            })
            .findAny().orElse(null);
    }

    public synchronized Customer getCustomerByOrderId(Integer orderId) {
        return customers.stream()
            .filter(c -> {
                try { return c.getOrderId().equals(orderId); }
                catch (NullPointerException npe) { return false; }
            })
            .findAny().orElse(null);
    }
}
