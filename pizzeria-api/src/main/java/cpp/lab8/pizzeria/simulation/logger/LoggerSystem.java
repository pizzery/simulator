package cpp.lab8.pizzeria.simulation.logger;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoggerSystem {

    private int logCount = 0;

    private List<LogRecord> logRecords = new ArrayList<>();

    public List<LogRecord> getAllLogs(){
        return this.logRecords;
    }

    public synchronized void addLog(Integer pizzaId){
        var log = new LogRecord(++logCount);
        log.setStartedAt(LocalDateTime.now());
        log.setPizzaId(pizzaId);
        logRecords.add(log);
    }

    public synchronized void finishPizzaLog(Integer pizzaId) {
        var log = logRecords.stream().filter(x -> x.getPizzaId() == pizzaId).findFirst();
        if(log.isPresent()){
            log.get().setFinishedAt(LocalDateTime.now());
        }
    }
}