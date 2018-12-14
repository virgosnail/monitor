package com.skd;

import com.skd.manager.Monitor;
import org.apache.log4j.PropertyConfigurator;

/**
 * Hello world!
 *
 */
public class ClientEntrance
{
    public static void main ( String[] args ) throws Exception
    {
        PropertyConfigurator.configure(ClientEntrance.class.getClassLoader().getResourceAsStream("config/log4j.properties"));
        Monitor.getInstance().monitor();
    }
}
