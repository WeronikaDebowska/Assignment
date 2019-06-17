package assignment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@ToString
@EqualsAndHashCode
public class Event {

    public String id;
    private String state;
    private Long timestamp;

    private String host;
    private String type;

    @JsonCreator
    Event(
            @JsonProperty(value = "id", required = true) String id,
            @JsonProperty(value = "state", required = true) String state,
            @JsonProperty(value = "timestamp", required = true) long timestamp,
            @JsonProperty(value = "type") String type,
            @JsonProperty(value = "host") String host
    ) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
    }

    String getId() {
        return id;
    }
}
