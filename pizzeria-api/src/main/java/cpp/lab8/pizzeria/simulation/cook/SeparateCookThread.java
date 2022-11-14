package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

public class SeparateCookThread extends Thread {
    private int time;

    private PizzeriaManager pizzeriaManager;

    private DataTransferManager dataTransferManager;

    private PizzaState pizzaState;

    public SeparateCookThread(int t, PizzeriaManager pm, DataTransferManager dtm, PizzaState ps) {
        time = t * 1000;
        pizzeriaManager = pm;
        dataTransferManager = dtm;
        pizzaState = ps;
    }

    @Override
    public void run() {
        System.out.println("thread started " + Thread.currentThread());
        new SeparateCooking(pizzaState).cookingStart(pizzeriaManager, time, dataTransferManager);
    }
}
