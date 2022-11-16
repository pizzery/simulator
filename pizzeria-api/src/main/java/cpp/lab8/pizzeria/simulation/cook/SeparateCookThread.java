package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

public class SeparateCookThread extends Thread {
    private int Id;
    private int time;

    private PizzeriaManager pizzeriaManager;

    private DataTransferManager dataTransferManager;

    private PizzaState pizzaState;

    private SeparateCooking cookingProcess;

    public SeparateCookThread(int cookId, int t, PizzeriaManager pm, DataTransferManager dtm, PizzaState ps) {
        time = t * 1000;
        pizzeriaManager = pm;
        dataTransferManager = dtm;
        pizzaState = ps;
        Id = cookId;
    }

    @Override
    public void run() {
        System.out.println("thread started " + Thread.currentThread());
        this.cookingProcess = new SeparateCooking(pizzaState);
        this.cookingProcess.cookingStart(pizzeriaManager, time, dataTransferManager, Id);
    }
}
