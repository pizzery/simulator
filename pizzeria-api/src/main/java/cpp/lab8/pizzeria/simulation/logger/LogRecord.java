package cpp.lab8.pizzeria.simulation.logger;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class LogRecord {
    @NonNull
    private Integer id;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Integer pizzaId;
}
