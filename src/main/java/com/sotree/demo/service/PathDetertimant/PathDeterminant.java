package com.sotree.demo.service.PathDetertimant;

import lombok.Data;
import org.apache.tomcat.jni.OS;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class PathDeterminant {
    /*
    private static final String LOCAL_PATH = "";
    private static final String LINUX_PATH = "";
    */
    private String OS_TYPE;

    public PathDeterminant(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dailyDirectory = dtf.format(now);
        if(isLINUX()){
            this.OS_TYPE = "/home/ec2-user/app/QR_IMG/"+dailyDirectory+"/";
        }else if(isWindow()){
            this.OS_TYPE = "C:\\TestQR\\qrImg\\"+dailyDirectory+"\\";
        }else{
            this.OS_TYPE = "C:\\TestQR\\qrImg\\"+dailyDirectory+"\\";
        }
    }

    public static boolean isLINUX(){
        return System.getProperty("os.name").toLowerCase().indexOf("linux")>=0;
    }
    public static boolean isWindow(){
        return System.getProperty("os.name").toLowerCase().indexOf("win")>=0;
    }

}
