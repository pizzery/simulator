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
        Thread threadPool[] = new Thread[n];
        CookType responsibility;
        PizzaState acceptsState;
        for(int i = 0; i < n; i++) {
            cooks.add(new Cook(i + 1));
            if(strategyType == 0){
                cooks.get(i).setCookType(CookType.All);
                threadPool[i] = new AllCookThread(i + 1, 
                    pizzaManager.getConfiguration().getMinCookingTime(), 
                    pizzaManager, 
                    dataTransferManager);
            } else {
                switch (i%3){
                    case 1:
                        responsibility = CookType.Filling;
                        acceptsState = PizzaState.DoughMaking;
                        break;
                    case 2:
                        responsibility = CookType.Baking;
                        acceptsState = PizzaState.Filling;
                        break;
                    default:
                        responsibility = CookType.DoughMaking;
                        acceptsState = PizzaState.Idle;
                        break;
                }
                cooks.get(i).setCookType(responsibility);
                threadPool[i] = new SeparateCookThread(i + 1, 
                    pizzaManager.getConfiguration().getMinCookingTime(), 
                    pizzaManager, 
                    dataTransferManager, 
                    acceptsState);
            }
        }
        for (int i = 0; i < n; i++) {
            threadPool[i].start();
        }
    }
}