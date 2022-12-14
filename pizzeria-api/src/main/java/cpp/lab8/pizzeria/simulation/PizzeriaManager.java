package cpp.lab8.pizzeria.simulation;

import cpp.lab8.pizzeria.simulation.cook.CookSystem;
import cpp.lab8.pizzeria.simulation.logger.LogRecord;
import cpp.lab8.pizzeria.simulation.logger.LoggerSystem;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;
import cpp.lab8.pizzeria.simulation.pizza.PizzaSystem;
import cpp.lab8.pizzeria.simulation.queue.QueueSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cpp.lab8.pizzeria.simulation.configuration.PizzeriaConfiguration;
import cpp.lab8.pizzeria.simulation.customer.Customer;
import cpp.lab8.pizzeria.simulation.customer.CustomerSystem;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.ClientGenerator;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.EveningStrategy;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.MorningStrategy;
import cpp.lab8.pizzeria.simulation.customer.generateStrategy.RushHourStrategy;
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

    // client generation strategy
    private ClientGenerator clientGenerator;

    // all subsystems
    private final CustomerSystem customerSystem;
    private final OrderSystem orderSystem;
    private final PizzaSystem pizzaSystem;
    private final CookSystem cookSystem;
    private final QueueSystem queueSystem;
    private final LoggerSystem loggerSystem;
    
    @Autowired
    public PizzeriaManager(CustomerSystem cs, OrderSystem os,  PizzaSystem ps, CookSystem cks, QueueSystem qs, LoggerSystem ls) {
        customerSystem = cs;
        orderSystem = os;
        pizzaSystem = ps;
        cookSystem = cks;
        queueSystem = qs;
        loggerSystem = ls;
    }

    // start workflow with current configuration
    public void start() {
        // configure client generation strategy
        switch (configuration.getStrategy()) {
            case 1:this.clientGenerator = new RushHourStrategy(); break;
            case 2: this.clientGenerator = new EveningStrategy(); break;
            default: this.clientGenerator = new MorningStrategy(); break;
        }

        // start systems
        queueSystem.createQueues(configuration.getCashRegisters());
        customerSystem.startGeneration(configuration.getVisitorsTimeout());
        cookSystem.createCooks(configuration.getCooks(), configuration.getCookStrategy());
    }

    // stop current simulation
    public void stop() {
        cookSystem.clear();
        customerSystem.stopGeneration();
        clientGenerator = null;
        customerSystem.clear();
        orderSystem.clear();
        pizzaSystem.clear();
        queueSystem.clear();
        loggerSystem.clear();
    }

    // customer related
    public synchronized void generateCustomer() {
        this.clientGenerator.generateCustomer(this);
    }
    public synchronized void chooseQueue(Customer customer) {
        queueSystem.assignCustomerToShortestQueue(customer);
    }

    // cook related
    public synchronized Pizza findPizzaToCook(PizzaState pizzaState){
        Pizza pizza = null;
        
        List<Pizza> allPizzas = pizzaSystem.getPizzas();
        Pizza currentPizza;
        for(int i = 0; i < allPizzas.size(); i++) {
            currentPizza = allPizzas.get(i);
            if(currentPizza.getState() == pizzaState && !currentPizza.getIsTaken()){
                pizza = currentPizza;
                pizza.setIsTaken(true);
                break;
            }
        }

        return pizza;
    }

    public List<LogRecord> getAllRecords(){
        return this.loggerSystem.getAllLogs();
    }

    public void resumeCookingFor(int id) {
        cookSystem.resumeCook(id);
    }

    public void suspendCookingFor(int id) {
        cookSystem.suspendCook(id);
    }
}
