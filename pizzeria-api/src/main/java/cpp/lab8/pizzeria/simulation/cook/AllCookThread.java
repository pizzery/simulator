package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;

public class AllCookThread extends CookingThread {
    public AllCookThread(int cookId, int t, PizzeriaManager pm, DataTransferManager dtm) {
        super(cookId, t, pm, dtm);
    }

    @Override
    public void run() {
        System.out.println("thread started " + Thread.currentThread());
        this.cookingProcess = new AllCooking();
        this.cookingProcess.cookingStart(pizzeriaManager, time, dataTransferManager, Id);
    }
}
