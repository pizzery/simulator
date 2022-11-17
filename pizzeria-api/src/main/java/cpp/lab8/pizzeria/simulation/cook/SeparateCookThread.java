package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

public class SeparateCookThread extends CookingThread {
    private PizzaState pizzaState;

    public SeparateCookThread(int cookId, int t, PizzeriaManager pm, DataTransferManager dtm, PizzaState ps) {
        super(cookId, t, pm, dtm);
        pizzaState = ps;
    }

    @Override
    public void run() {
        System.out.println("thread started " + Thread.currentThread());
        this.cookingProcess = new SeparateCooking(pizzaState);
        this.cookingProcess.cookingStart(pizzeriaManager, time, dataTransferManager, Id);
    }
}
