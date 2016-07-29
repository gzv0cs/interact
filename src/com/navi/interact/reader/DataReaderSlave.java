package com.navi.interact.reader;

import com.navi.interact.build.entity.SensorData;
import com.navi.interact.build.wrapper.SensorDataWrapper;
import com.navi.interact.tools.factory.InstanceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Data format - Weather Station number,Sensor number,sensor reading,DateTime(\n)
// example: 1,1,22,C\n
// example: 1,2,42.34,Pa,2016-07-26T22:16:44.939Z\n

// sensor number list: 1-Temperature, 2-Humidity, 3-Barometric Presure,
// 4-Iluminicity, 5-Wind Speed, 6-Wind Direction, 7-Rain guage, 8-Battery Voltage

/**
 * Created by mevan on 26/07/2016.
 */
public class DataReaderSlave implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(DataReaderSlave.class);

    private String readLine;
    private String delmSessor = ",";
    private SensorData sensorData;

    private SimpleDateFormat dateFormatter;
    private SensorDataWrapper wrapper;
    private ArrayList<DataReaderSlave> dormentWorkers;

    // Default Constructor
    public DataReaderSlave() throws Exception {
        wrapper = (SensorDataWrapper) InstanceFactory.getInstance().getEntity(SensorDataWrapper.BEAN_ID);
    }

    public void setData(String readLine, SimpleDateFormat dateFormatter,
                        ArrayList<DataReaderSlave> dormentWorkers) {
        this.readLine = readLine;
        this.dateFormatter = dateFormatter;
        this.dormentWorkers = dormentWorkers;
    }

    public void run() {
        LOGGER.info("DataReaderSlave "+this.hashCode()+" Processing data: "+readLine);
        try {
            if (readLine != null && readLine.contains(delmSessor)) {
                StringTokenizer st = new StringTokenizer(readLine, delmSessor);
                if (st.countTokens() == 5) {
                    int tokenNumber = 1;
                    sensorData = new SensorData();
                    while (st.hasMoreTokens()) {
                        String token = (String) st.nextToken();
                        if (tokenNumber == 1) {
                            sensorData.setWeatherStationNo(token);
                        }
                        if (tokenNumber == 2) {
                            sensorData.setSensorId(Integer.parseInt(token));
                        }
                        if (tokenNumber == 3) {
                            sensorData.setSessorData(Float.parseFloat(token));
                        }
                        if (tokenNumber == 4) {
                            sensorData.setDataUnit(token);
                        }
                        if (tokenNumber == 5) {
                            sensorData.setReadDateTime(dateFormatter.parse(token));
                        }
                        tokenNumber++;
                    }
                    // sensorData.disclose();
                    wrapper.insert(sensorData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dormentWorkers.add(this);
        }
    }

}
