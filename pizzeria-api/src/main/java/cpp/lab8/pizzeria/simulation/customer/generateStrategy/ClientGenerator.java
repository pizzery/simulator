package cpp.lab8.pizzeria.simulation.customer.generateStrategy;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.customer.Customer;

public interface ClientGenerator {
    void generateCustomer(PizzeriaManager pizzaManager);
}
