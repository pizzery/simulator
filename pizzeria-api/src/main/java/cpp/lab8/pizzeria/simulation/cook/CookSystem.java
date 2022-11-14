package cpp.lab8.pizzeria.simulation.cook;

import java.util.ArrayList;
import java.util.List;


import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.pizza.PizzaState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CookSystem {
    // socket to broadcast modifications
    private DataTransferManager dataTransferManager;

    private final PizzeriaManager pizzaManager;

    @Autowired
    public CookSystem(@Lazy PizzeriaManager pizzaManager,
                          DataTransferManager dataTransferManager) {
        this.pizzaManager = pizzaManager;
        this.dataTransferManager = dataTransferManager;
    }

    // list of cooks in the system
    private List<Cook> cooks = new ArrayList<>();

    public void createCooks(int n, int strategyType) {
        for(int i = 0; i < n; i++) {
            cooks.add(new Cook(i + 1));
            if(strategyType == 0){
                cooks.get(i).setCookType(CookType.All);
                AllCookThread cookThread = new AllCookThread(pizzaManager.getConfiguration().getMinCookingTime(), pizzaManager, dataTransferManager);
                cookThread.start();
            }
            else{
                switch (i%3){
                    case 0:
                        cooks.get(i).setCookType(CookType.DoughMaking);
                        SeparateCookThread doughCookThread = new SeparateCookThread(pizzaManager.getConfiguration().getMinCookingTime(), pizzaManager, dataTransferManager, PizzaState.DoughMaking);
                        doughCookThread.start();
                        break;
                    case 1:
                        cooks.get(i).setCookType(CookType.Filling);
                        SeparateCookThread fillingCookThread = new SeparateCookThread(pizzaManager.getConfiguration().getMinCookingTime(), pizzaManager, dataTransferManager, PizzaState.Filling);
                        fillingCookThread.start();
                        break;
                    case 2:
                        cooks.get(i).setCookType(CookType.Baking);
                        SeparateCookThread bakingCookThread = new SeparateCookThread(pizzaManager.getConfiguration().getMinCookingTime(), pizzaManager, dataTransferManager, PizzaState.Baking);
                        bakingCookThread.start();
                        break;
                }
            }
        }
    }
}