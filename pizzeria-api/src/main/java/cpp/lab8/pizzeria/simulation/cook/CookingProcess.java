package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;

public interface CookingProcess {
    public void cookingStart(PizzeriaManager pizzeriaManager, int time, DataTransferManager dataTransferManager, int Id);
    public void cook(Pizza pizza, int time, DataTransferManager dataTransferManager, int Id, PizzeriaManager pizzaManager);
    public void setActive(boolean active);
}
