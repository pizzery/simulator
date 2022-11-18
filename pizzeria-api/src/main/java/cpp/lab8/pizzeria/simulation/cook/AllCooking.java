package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.Pizza;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;

import java.util.Random;

public class AllCooking implements CookingProcess {
    private boolean active;

    public AllCooking() { this.active = true; }

    @Override
    public void cookingStart(PizzeriaManager pizzeriaManager, int time, DataTransferManager dataTransferManager, int Id){
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!active) continue;

            Pizza pizzaToDo = pizzeriaManager.findPizzaToCook(PizzaState.Idle);
            if (pizzaToDo != null) {
                cook(pizzaToDo, time, dataTransferManager, Id, pizzeriaManager);
            }
        }
    }

    @Override
    public void cook(Pizza pizza, int time, DataTransferManager dataTransferManager, int Id, PizzeriaManager pizzeriaManager) {
        Random random = new Random();
        try {
            System.out.println("DoughMaking " + pizza.getPizzaId());
            pizza.setState(PizzaState.DoughMaking);
            pizzeriaManager.getLoggerSystem().addLog(pizza.getPizzaId());
            pizza.setCookId(Id);
            dataTransferManager.sendEntity(pizza);

            double doughMakingTime = time * 0.3 + (double)random.nextInt(5000);
            Thread.sleep((int)doughMakingTime);

            System.out.println("Filling " + pizza.getPizzaId());
            pizza.setState(PizzaState.Filling);
            dataTransferManager.sendEntity(pizza);

            double fillingTime = time * 0.1 + (double)random.nextInt(5000);
            Thread.sleep((int)fillingTime);

            System.out.println("Baking " + pizza.getPizzaId());
            pizza.setState(PizzaState.Baking);
            dataTransferManager.sendEntity(pizza);

            double bakingTime = time * 0.6 + (double)random.nextInt(5000);
            Thread.sleep((int)bakingTime);

            System.out.println("Done " + pizza.getPizzaId());
            pizza.setState(PizzaState.Done);
            pizzeriaManager.getLoggerSystem().finishPizzaLog(pizza.getPizzaId());
            dataTransferManager.sendEntity(pizza);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
