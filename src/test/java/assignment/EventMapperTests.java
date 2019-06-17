package assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class EventMapperTests {


    private final String VALID = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", " +
            "\"timestamp\":1491377495212}";
    private final String MISSING_ID = "\\{\"id\":\"\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217\\}";
    private final String MISSING_TIMESTAMP = "\\{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":\\}";

    private EventMapper eventMapper;


    @Before
    public void createEventMapper() {
        eventMapper = new EventMapper();
    }


    @Test
    public void shouldReturnEventForValidString() {

        //when
        Event event = eventMapper.mapStringToEvenObject(VALID);

        //then
        assertNotNull(event);
        assertEquals(event.getId(), "scsmbstgra");
        assertEquals(event.getState(), "STARTED");
        assertEquals(event.getType(), "APPLICATION_LOG");
        assertEquals(event.getHost(), "12345");

        Long expectedTimestamp = 1491377495212L;
        assertEquals(event.getTimestamp(), expectedTimestamp);
    }


    @Test
    public void shouldReturnNullWhenMissingId() {
        //when
        Event event = eventMapper.mapStringToEvenObject(MISSING_ID);

        //then
        assertNull(event);
    }

    @Test
    public void shouldReturnNullWhenMissingTimestamp() {
        //when
        Event event = eventMapper.mapStringToEvenObject(MISSING_TIMESTAMP);

        //then
        assertNull(event);
    }

    @Test
    public void shouldReturnNullForStringNull() {
        //when
        Event event = eventMapper.mapStringToEvenObject(null);

        //then
        assertNull(event);
    }

    @Test
    public void shouldReturnNullForStringEmpty() {
        //when
        Event event = eventMapper.mapStringToEvenObject("");

        //then
        assertNull(event);
    }

    @Test
    public void shouldReturnNullForStringInvalid() {
        //when
        Event event = eventMapper.mapStringToEvenObject("INVALID");

        //then
        assertNull(event);
    }

}

