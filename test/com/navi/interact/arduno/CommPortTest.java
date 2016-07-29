package com.navi.interact.arduno;

import gnu.io.*;
import java.util.Enumeration;

/**
 * Created by mevan on 14/07/2016.
 */
public class CommPortTest {

    public static void main(String[] args) {
        System.out.println("Program Started!!!");

        CommPortIdentifier serialPortId;

        Enumeration enumComm;

        enumComm = CommPortIdentifier.getPortIdentifiers();

        while(enumComm.hasMoreElements())
        {
            serialPortId = (CommPortIdentifier)enumComm.nextElement();
            if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                System.out.println(serialPortId.getName());
            }
        }

        System.out.println("Program Finished Sucessfully");
    }

}
