package assignment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class EventEntity {

    private final int ALERT_LIMIT = 4;

    private String id;
    private int eventDuration;
    private String host;
    private String type;
    private boolean alert;

    EventEntity(Event e1, Event e2) {
        this.id = e1.getId();
        this.host = e1.getHost();
        this.type = e1.getType();
        this.eventDuration = (int) Math.abs(e1.getTimestamp() - e2.getTimestamp());
        this.alert = eventDuration >= ALERT_LIMIT;
    }
}
