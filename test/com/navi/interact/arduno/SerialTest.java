package com.navi.interact.arduno;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Calendar;
import java.util.Enumeration;

/**
 * Created by mevan on 15/07/2016.
 */
public class SerialTest implements SerialPortEventListener {

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

    private BufferedReader input;
    private InputStream is;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    private int readCount = 0;
    private long intervalTime = 0;

    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        intervalTime = System.currentTimeMillis();
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

        } catch (Exception e) {
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
        System.out.println("**************"+event.getEventType());
        readCount++;
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = null;

//                while (is.available() != 0) {
//                }

                System.err.println("Something .............");
                if (input.ready()) {
                    inputLine = input.readLine();
                    String [] chunks = inputLine.split(",");

                    System.out.println(inputLine);
                    System.out.println(chunks[0] + "\t" + chunks[1] + "\t" + chunks[2] + "\t");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.err.println(readCount+" in milliSec: "+(System.currentTimeMillis()-intervalTime));
        intervalTime = System.currentTimeMillis();

    }

    public static void main(String[] args) throws Exception {
        SerialTest main = new SerialTest();
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
        System.out.println("Started");
    }
}
