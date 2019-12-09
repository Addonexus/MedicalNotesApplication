package nsa.group4.medical.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationAdded {

    private Long id;
    private String content;
}
