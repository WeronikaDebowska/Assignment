package assignment;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@Slf4j
class EventProcessor {


    private EventMapper eventmapper;
    private EventRepository eventRepository;

    EventProcessor(EventMapper eventmapper) {
        this.eventmapper = eventmapper;
    }

    void process(List<String> content) {
        if (content != null) {
            Map<String, List<Event>> pairedEvents = createEventMap(content);
            for (Map.Entry<String, List<Event>> group : pairedEvents.entrySet()) {
                if (hasEventItsPair(group)) {
                    EventEntity entity = createEventEntity(group);
                    eventRepository.insert(entity);
                } else {
                    log.warn("Event skipped - Event {} has no pair", group.getKey());
                }
            }
        }
    }

    private EventEntity createEventEntity(Map.Entry<String, List<Event>> group) {
        return new EventEntity(group.getValue().get(0), group.getValue().get(1));
    }


    Map<String, List<Event>> createEventMap(List<String> eventStrings) {
        return eventStrings.stream()
                .parallel()
                .map(str -> eventmapper.mapStringToEvenObject(str))
                .filter(Objects::nonNull)
                .collect(groupingBy(Event::getId));
    }

    private boolean hasEventItsPair(Map.Entry<String, List<Event>> group) {
        return group.getValue().size() == 2;
    }


}
