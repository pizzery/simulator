package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

import java.util.Random;

public class AllCooking {
    private boolean active;

    public AllCooking() { this.active = true; }

    public void cookingStart(PizzeriaManager pizzeriaManager, int time, DataTransferManager dataTransferManager, int Id){
        while(true){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!active) continue;

            Pizza pizzaToDo = null;

            while(pizzaToDo == null){
                pizzaToDo = pizzeriaManager.findPizzaToCook(PizzaState.Idle);
            }

            cook(pizzaToDo, time, dataTransferManager, Id, pizzeriaManager);

            pizzaToDo = null;
        }
    }

    public void cook(Pizza pizza, int time, DataTransferManager dataTransferManager, int Id, PizzeriaManager pizzeriaManager) {
        try {
            System.out.println("Dough making " + pizza.getPizzaId());
            pizza.setState(PizzaState.DoughMaking);
            pizzeriaManager.getLoggerSystem().addLog(pizza.getPizzaId());
            pizza.setCookId(Id);
            dataTransferManager.sendEntity(pizza);

            double doughMakingTime = time * 0.3 + (double)new Random().nextInt(5000);
            Thread.sleep((int)doughMakingTime);

            System.out.println("Filling" + pizza.getPizzaId());
            pizza.setState(PizzaState.Filling);
            dataTransferManager.sendEntity(pizza);

            double fillingTime = time * 0.10 + (double)new Random().nextInt(5000);
            Thread.sleep((int)fillingTime);

            System.out.println("Baking" + pizza.getPizzaId());
            pizza.setState(PizzaState.Baking);
            dataTransferManager.sendEntity(pizza);

            double bakingTime = time * 0.6 + (double)new Random().nextInt(5000);
            Thread.sleep((int)bakingTime);

            System.out.println("Pizza Done" + pizza.getPizzaId());
            pizza.setState(PizzaState.Done);
            pizzeriaManager.getLoggerSystem().finishPizzaLog(pizza.getPizzaId());
            dataTransferManager.sendEntity(pizza);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
