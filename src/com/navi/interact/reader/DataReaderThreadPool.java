package com.navi.interact.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mevan on 27/07/2016.
 */
public class DataReaderThreadPool {

    private static final Logger LOGGER = LogManager.getLogger(WeatherStationDataReader.class);

    private ExecutorService executor;
    private static String SEED = "seed";

    private static int NUMBER_OF_THREADS = 10;
    private static DataReaderThreadPool threadPool = null;

    private ArrayList<DataReaderSlave> dormentWorkers;

    // Default Constructor
    private DataReaderThreadPool() throws Exception {
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        dormentWorkers = new ArrayList<DataReaderSlave>();
        for (int i = 0; i < NUMBER_OF_THREADS*2; i++) {
            DataReaderSlave worker = new DataReaderSlave();
            LOGGER.info("Adding worker "+worker.hashCode());
            dormentWorkers.add(worker);
        }
    }

    public static DataReaderThreadPool getInstance() throws Exception {
        synchronized(SEED) {
            if (threadPool == null) {
                threadPool = new DataReaderThreadPool();
            }
        }
        return threadPool;
    }

    public void executeDataReaderThread(String readLine, SimpleDateFormat dateFormatter) {
        if (dormentWorkers.size() > 0 ) {
            DataReaderSlave worker = dormentWorkers.remove(0);
            worker.setData(readLine,dateFormatter, dormentWorkers);
            executor.execute(worker);
        }
    }

}
