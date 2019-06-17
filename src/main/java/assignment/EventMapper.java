package assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class EventMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    private Event parse(String jsonString) {
        try {
            return mapper.readValue(jsonString, Event.class);
        } catch (IOException ioe) {
            log.warn("Line skipped - Line {} could not be parsed into Event Object", jsonString, ioe);
            return null;
        }
    }

    Event mapStringToEvenObject(String str) {
        return str == null ?
                null :
                parse(str);
    }
}
