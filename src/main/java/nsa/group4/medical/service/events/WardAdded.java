package nsa.group4.medical.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WardAdded {

    private Long id;
    private String name;
}
