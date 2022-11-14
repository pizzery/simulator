package cpp.lab8.pizzeria.simulation.cook;

import lombok.Data;
import lombok.NonNull;

@Data
public class Cook {
    // queue id
    @NonNull
    private Integer id;

    private Integer pizzaId;

    private CookType cookType;
}