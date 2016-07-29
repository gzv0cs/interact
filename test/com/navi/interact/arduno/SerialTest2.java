package com.navi.interact.arduno;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by mevan on 15/07/2016.
 */
public class SerialTest2 implements SerialPortEventListener {

    private static final Logger LOGGER = LogManager.getLogger(SerialTest2.class);

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

    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    public void initialize() {
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
                String inputLine = null;
                if (input.ready()) {
                    inputLine = input.readLine();
                    LOGGER.debug(inputLine+","+ dateFormatter.format(new Date()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SerialTest2 main = new SerialTest2();
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
