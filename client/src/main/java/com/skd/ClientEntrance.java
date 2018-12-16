package com.skd;

import com.skd.manager.Monitor;
import org.apache.log4j.PropertyConfigurator;

/**
 * @Description
 * @Author virgosnail
 * @Date 2018/12/15 17:06
 */
public class ClientEntrance
{
    public static void main ( String[] args )
    {
        try {
            PropertyConfigurator.configure(ClientEntrance.class.getClassLoader().getResourceAsStream("config/log4j.properties"));
            Monitor.getInstance().monitor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
