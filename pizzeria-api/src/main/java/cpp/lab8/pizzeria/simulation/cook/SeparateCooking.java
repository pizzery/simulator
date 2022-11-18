package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

import java.util.Random;

public class SeparateCooking implements CookingProcess {
    private PizzaState pizzaState;
    private boolean active;

    public SeparateCooking(PizzaState ps){
        pizzaState = ps;
        active = true;
    }

    @Override
    public void cookingStart(PizzeriaManager pizzeriaManager, int time, DataTransferManager dataTransferManager, int Id) {
        while(true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!active) continue;

            Pizza pizzaToDo = pizzeriaManager.findPizzaToCook(pizzaState);
            if (pizzaToDo != null) {
                cook(pizzaToDo, time, dataTransferManager, Id, pizzeriaManager);
            }
        }
    }

    @Override
    public void cook(Pizza pizza, int time, DataTransferManager dataTransferManager, int Id, PizzeriaManager pizzaManager) {
        pizza.setState(PizzaState.next(pizzaState));
        System.out.println(pizza.getState().toString() + " " + pizza.getPizzaId());
        if(pizza.getState() == PizzaState.DoughMaking){
            pizzaManager.getLoggerSystem().addLog(pizza.getPizzaId());
        }
        pizza.setCookId(Id);
        dataTransferManager.sendEntity(pizza);

        double cookingTime =
                pizzaState == PizzaState.DoughMaking ? time * 0.3 + (double)new Random().nextInt(5000) :
                pizzaState == PizzaState.Filling ? time * 0.10 + (double)new Random().nextInt(5000) : time * 0.6 + (double)new Random().nextInt(5000);
        try {
            Thread.sleep((int)cookingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (pizza.getState() == PizzaState.Baking) {
            System.out.println("Done " + pizza.getPizzaId());
            pizza.setState(PizzaState.Done);
            pizzaManager.getLoggerSystem().finishPizzaLog(pizza.getPizzaId());
            dataTransferManager.sendEntity(pizza);
        }

        pizza.setIsTaken(false);
        pizza.setCookId(null);
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
