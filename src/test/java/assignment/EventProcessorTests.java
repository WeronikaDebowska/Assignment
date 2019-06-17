package assignment;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class EventProcessorTests {

    private final String EVENT_1 = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}";
    private final String EVENT_2 = "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}";
    private final String EVENT_3 = "{\"id\":\"scsmbstgrc\", \"state\":\"FINISHED\", \"timestamp\":1491377495218}";
    private final String EVENT_4 = "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}";
    private final String EVENT_5 = "{\"id\":\"scsmbstgrc\", \"state\":\"STARTED\", \"timestamp\":1491377495210}";
    private final String EVENT_6 = "{\"id\":\"scsmbstgrb\", \"state\":\"FINISHED\", \"timestamp\":1491377495216}";
    private final String MISSING_ID = "\\{\"id\":\"\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217\\}";
    private EventMapper eventMapper;
    private EventProcessor eventProcessor;

    @Before
    public void createEventMapper() {
        eventMapper = new EventMapper();
    }


    @Before
    public void createEventProcessor() {
        eventProcessor = new EventProcessor(eventMapper);
    }


    @Test
    public void shouldProcessListOfValidEvents() {
        //given
        List<String> testList = new LinkedList<>();
        testList.add(EVENT_1);
        testList.add(EVENT_2);
        testList.add(EVENT_3);
        testList.add(EVENT_4);
        testList.add(EVENT_5);
        testList.add(EVENT_6);

        //when
        Map<String, List<Event>> result = eventProcessor.createEventMap(testList);

        //then
        Map<String, List<Event>> expectedList = new HashMap<>();

        expectedList.put("scsmbstgra", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgra", "STARTED", 1491377495212L, "APPLICATION_LOG", "12345"),
                new Event("scsmbstgra", "FINISHED", 1491377495217L, "APPLICATION_LOG", "12345"))));


        expectedList.put("scsmbstgrb", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgrb", "STARTED", 1491377495213L, null, null),
                new Event("scsmbstgrb", "FINISHED", 1491377495216L, null, null))));

        expectedList.put("scsmbstgrc", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgrc", "FINISHED", 1491377495218L, null, null),
                new Event("scsmbstgrc", "STARTED", 1491377495210L, null, null))));


        assertEquals(3, result.size());
        assertEquals(expectedList, result);
    }


    @Test
    public void shouldFilerMissingIdEvents() {
        //given
        List<String> testList = new LinkedList<>();
        testList.add(EVENT_1);
        testList.add(MISSING_ID);


        //when
        Map<String, List<Event>> result = eventProcessor.createEventMap(testList);

        //then
        Map<String, List<Event>> expectedMap = new HashMap<>();

        expectedMap.put("scsmbstgra", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgra", "STARTED", 1491377495212L, "APPLICATION_LOG", "12345"))));

        assertEquals(1, result.size());
        assertEquals(expectedMap, result);
    }

    @Test
    public void shouldFilerInvalidString() {
        //given
        List<String> testList = new LinkedList<>();
        testList.add(EVENT_1);
        testList.add("INVALID");


        //when
        Map<String, List<Event>> result = eventProcessor.createEventMap(testList);

        //then
        Map<String, List<Event>> expectedMap = new HashMap<>();

        expectedMap.put("scsmbstgra", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgra", "STARTED", 1491377495212L, "APPLICATION_LOG", "12345"))));

        assertEquals(1, result.size());
        assertEquals(expectedMap, result);
    }

    @Test
    public void shouldFilerEmptySting() {
        //given
        List<String> testList = new LinkedList<>();
        testList.add(EVENT_1);
        testList.add("");

        //when
        Map<String, List<Event>> result = eventProcessor.createEventMap(testList);

        //then
        Map<String, List<Event>> expectedMap = new HashMap<>();

        expectedMap.put("scsmbstgra", new LinkedList<Event>(Arrays.asList(
                new Event("scsmbstgra", "STARTED", 1491377495212L, "APPLICATION_LOG", "12345"))));

        assertEquals(1, result.size());
        assertEquals(expectedMap, result);
    }

}
