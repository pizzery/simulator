package cpp.lab8.pizzeria.simulation.cook;

import cpp.lab8.pizzeria.simulation.PizzeriaManager;
import cpp.lab8.pizzeria.simulation.DTO.DataTransferManager;

public abstract class CookingThread extends Thread {
    protected int Id;
    protected int time;
    protected PizzeriaManager pizzeriaManager;
    protected DataTransferManager dataTransferManager;
    protected CookingProcess cookingProcess;

    public CookingThread(int cookId, int t, PizzeriaManager pm, DataTransferManager dtm) {
        time = t * 1000;
        pizzeriaManager = pm;
        dataTransferManager = dtm;
        Id = cookId;
    }

    public void setActive(boolean active) {
        this.cookingProcess.setActive(active);
    }
}
