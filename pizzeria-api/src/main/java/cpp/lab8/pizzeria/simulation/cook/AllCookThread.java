package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;
import cpp.lab8.pizzeria.simulation.PizzeriaManager;

public class AllCookThread extends Thread{
    private int Id;
    private int time;

    private PizzeriaManager pizzeriaManager;

    private DataTransferManager dataTransferManager;

    private AllCooking cookingProcess;

    public AllCookThread(int cookId, int t, PizzeriaManager pm, DataTransferManager dtm) {
        time = t * 1000;
        pizzeriaManager = pm;
        dataTransferManager = dtm;
        Id = cookId;
    }

    @Override
    public void run() {
        System.out.println("thread started " + Thread.currentThread());
        this.cookingProcess = new AllCooking();
        this.cookingProcess.cookingStart(pizzeriaManager, time, dataTransferManager, Id);
    }
}
