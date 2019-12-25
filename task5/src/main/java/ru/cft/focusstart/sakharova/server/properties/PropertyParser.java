package ru.cft.focusstart.sakharova.server.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
@Getter
public class PropertyParser {

    private static final String PORT_NUMBER_PROPERTY_NAME = "port";
    private static final String NUMBER_OF_THREADS_PROPERTY_NAME = "numberOfThreads";

    private final int portNumber;
    private final int numberOfThreads;

    public PropertyParser() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                (getClass().getResourceAsStream("/config.properties")))) {
            Properties properties = new Properties();
            properties.load(reader);
            portNumber = Integer.parseInt(properties.getProperty(PORT_NUMBER_PROPERTY_NAME));
            numberOfThreads = Integer.parseInt(properties.getProperty(NUMBER_OF_THREADS_PROPERTY_NAME));
        }
    }
}