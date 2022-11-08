package cpp.lab8.pizzeria.simulation.cook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import cpp.lab8.pizzeria.simulation.order.Order;
import cpp.lab8.pizzeria.simulation.order.OrderSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cpp.lab8.pizzeria.socket.BroadcastSocket;

@Component
public class CookSystem {
    // socket to broadcast modifications
    @Autowired
    private BroadcastSocket socket;

    // list of cooks in the system
    private List<Cook> cooks = new ArrayList<>();
    private int lastCook = 0;

    public void setCookNumber(int n) {
        for(int i=0; i<n; i++) {
            cooks.add(new Cook(++lastCook));
        }
    }

    public void orderHandling(int sec, int cookId, Order order) {
        Cook cook = cooks.get(cookId);
        cook.setOrderToCook(order);
        int regime = (Math.random() <= 0.5) ? 1 : 2;
        if(regime == 1) {
            Random random = new Random();
            double offSet = (double)random.nextInt(4)/10 + sec;
            double time = offSet * 1000;
            cook.getOrderToCook().setOrderStatus("Cooking");
            try {
                Thread.sleep((int)time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cook.getOrderToCook().setOrderStatus("Done");
        }
        else {
            Random random = new Random();
            double offSet = (double)random.nextInt(4)/10 + sec;
            double time = offSet * 1000;
            //doughMaking - 0
            //toppingMaking - 1
            //baking - 2
            List<CookThread> cookThreads = new ArrayList<>();
            for(int i=0; i<3; i++) {
                cookThreads.add(new CookThread((int)time, i));
            }
            cook.getOrderToCook().setOrderStatus("Cooking");
            for(int i=0; i<3; i++) {
                cookThreads.get(i).start();
            }
            cook.getOrderToCook().setOrderStatus("Done");
        }
    }
}
