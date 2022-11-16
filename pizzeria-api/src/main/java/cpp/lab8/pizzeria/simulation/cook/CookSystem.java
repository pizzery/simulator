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

    // threads responsible for every cook's work
    private Thread threadPool[];

    public synchronized void clear() {
        this.cooks.clear();
        for (int t = 0; t < threadPool.length; t++) {
            threadPool[t].interrupt();
        }
        threadPool = null;
    }

    public void createCooks(int n, int strategyType) {
        threadPool = new Thread[n];
        for(int i = 0; i < n; i++) {
            cooks.add(new Cook(i + 1));
            if(strategyType == 0){
                cooks.get(i).setCookType(CookType.All);
                threadPool[i] = new AllCookThread(i + 1, pizzaManager.getConfiguration().getMinCookingTime(), pizzaManager, dataTransferManager);
            } else {
                switch (i%3){
                    case 0:
                        cooks.get(i).setCookType(CookType.DoughMaking);
                        threadPool[i] = new SeparateCookThread(i + 1, 
                            pizzaManager.getConfiguration().getMinCookingTime(), 
                            pizzaManager, 
                            dataTransferManager, 
                            PizzaState.Idle);
                        break;
                    case 1:
                        cooks.get(i).setCookType(CookType.Filling);
                        threadPool[i] = new SeparateCookThread(i + 1, 
                            pizzaManager.getConfiguration().getMinCookingTime(), 
                            pizzaManager, 
                            dataTransferManager, 
                            PizzaState.DoughMaking);
                        break;
                    case 2:
                        cooks.get(i).setCookType(CookType.Baking);
                        threadPool[i] = new SeparateCookThread(i + 1, 
                            pizzaManager.getConfiguration().getMinCookingTime(), 
                            pizzaManager, 
                            dataTransferManager, 
                            PizzaState.Filling);
                        break;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            threadPool[i].start();
        }
    }
}