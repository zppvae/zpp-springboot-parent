package org.zpp.springboot.socketio.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zpp
 * @date 2018/8/15 10:49
 */
public class DateUtils {

    private static final String YYYY_MM_DD_HH_MM_SS = "YYYY-MM-dd HH:mm:ss";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    public static String getNow(){
        LocalDateTime rightNow = LocalDateTime.now();
        return formatter.format(rightNow);
    }

    public static class TestDateThreadSafe extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    System.out.println(this.getName()+":"+getNow());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 3; i++){
            new TestDateThreadSafe().start();
        }

    }
}
