package assignment;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class App {


    public static void main(String[] args) {

        if (args.length != 1) {
            log.info("Enter valid file path");
        }

        System.out.println(args[0]);

        String filePath = args[0];

        try {
            List<String> content = Files.readAllLines(Paths.get(filePath));


            EventMapper eventMapper = new EventMapper();
            EventRepository eventRepository = new EventRepository();
            EventProcessor eventProcessor = new EventProcessor(eventMapper);
            eventProcessor.setEventRepository(eventRepository);

            eventProcessor.process(content);
            eventRepository.closeConnection();

        } catch (IOException ioe) {
            log.error("Failed to read input from {}", filePath, ioe);
        }
    }
}

