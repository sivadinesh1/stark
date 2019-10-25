package com.squapl.stark;


import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Test1 {

    public static void main(String args[]) {
        String sdate = "16-09-2019";
        try {
            String sDate1 = "16-09-2019";
//            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
//            System.out.println(sDate1 + "\t" + date1);


            Instant timestamp = Instant.parse(sDate1);
            ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));

            System.out.println("test..." + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime));


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error " + e.getMessage());
        }


    }

}

