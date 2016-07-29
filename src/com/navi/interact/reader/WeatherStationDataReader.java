package com.navi.interact.reader;

import com.navi.interact.build.wrapper.SensorDataWrapper;
import com.navi.interact.tools.factory.InstanceFactory;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

// Data format - Weather Statio number[:Sensor number, sensor reading,Measurement] x n and line seperator (\n)
// example: 1#1,22,C#2,42,%#3,1.2,Pa\n

// sensor number list: 1-Temperature, 2-Humidity, 3-Barometric Presure,
// 4-Iluminicity, 5-Wind Speed, 6-Wind Direction, 7-Rain guage, 8-Battery Voltage

/**
 * Created by mevan on 26/07/2016.
 */
public class WeatherStationDataReader implements SerialPortEventListener {

    private static final Logger LOGGER = LogManager.getLogger(WeatherStationDataReader.class);

    SerialPort serialPort;
    /** The port we're normally going to use. */
    private static final String PORT_NAMES[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyUSB0", // Linux
            "COM1", // Windows
            "COM2", // Windows
            "COM3", // Windows
            "COM7", // Windows
    };

    private InputStream is;
    private BufferedReader input;
    private OutputStream output;
    private SimpleDateFormat dateFormatter;
    private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private DataReaderThreadPool threadPool;

    private SensorDataWrapper wrapper;

    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    public void initialize() throws Exception {

        threadPool = DataReaderThreadPool.getInstance();
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            LOGGER.error("Could not find COM port.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            is = serialPort.getInputStream();
            input = new BufferedReader(new InputStreamReader(is));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            dateFormatter = new SimpleDateFormat(dateFormat);

        } catch (Exception e) {
            LOGGER.error(e.toString());
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                if (input.ready()) {
                    String inputLine = input.readLine()+","+dateFormatter.format(new Date());
                    LOGGER.debug(inputLine);
                    threadPool.executeDataReaderThread(inputLine, dateFormatter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        WeatherStationDataReader main = new WeatherStationDataReader();
        main.initialize();
        Thread t=new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000    seconds,
                //waiting for events to occur and responding to them    (printing incoming messages to console).
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException ie) {}
            }
        };
        t.start();
        LOGGER.debug("Started");
    }

}
