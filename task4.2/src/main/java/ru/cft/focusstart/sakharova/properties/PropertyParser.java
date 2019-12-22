package ru.cft.focusstart.sakharova.properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
@Getter
public class PropertyParser {
    private static final int MILLIS_IN_SECOND = 1000;

    private static final String PRODUCER_THREADS_NUMBER = "producerThreadsNumber";
    private static final String CONSUMER_THREADS_NUMBER = "consumerThreadsNumber";
    private static final String PRODUCTION_TIME = "productionTime";
    private static final String CONSUMPTION_TIME = "consumptionTime";
    private static final String STORAGE_SIZE = "storageSize";

    private final int producerThreadsNumber;
    private final int consumerThreadsNumber;
    private final int productionTime;
    private final int consumptionTime;
    private final int storageSize;

    public PropertyParser() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                (getClass().getResourceAsStream("/config.properties")))) {
            Properties properties = new Properties();
            properties.load(reader);

            producerThreadsNumber = Integer.parseInt(properties.getProperty(PRODUCER_THREADS_NUMBER));
            consumerThreadsNumber = Integer.parseInt(properties.getProperty(CONSUMER_THREADS_NUMBER));
            productionTime = Integer.parseInt(properties.getProperty(PRODUCTION_TIME)) * MILLIS_IN_SECOND;
            consumptionTime = Integer.parseInt(properties.getProperty(CONSUMPTION_TIME)) * MILLIS_IN_SECOND;
            storageSize = Integer.parseInt(properties.getProperty(STORAGE_SIZE));
        }
    }
}
