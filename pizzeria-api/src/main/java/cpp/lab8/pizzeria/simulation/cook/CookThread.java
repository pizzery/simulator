package cpp.lab8.pizzeria.simulation.cook;

public class CookThread extends Thread{

    private int time;
    private int activityType;

    public CookThread(int t, int a) {
        time = t;
        activityType = a;
    }

    @Override
    public void run() {
        if(activityType == 0) {
            double doughMakingTime = time * 0.35;
            try {
                Thread.sleep((int)doughMakingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            if(activityType == 1) {
                double toppingMakingTime = time * 0.10;
                try {
                    Thread.sleep((int)toppingMakingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                    double bakingTime = time * 0.55;
                    try {
                        Thread.sleep((int)bakingTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }
            }
        }

    }
}
