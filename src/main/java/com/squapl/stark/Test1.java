package com.squapl.stark;


public class Test1 {

//    public static void main(String args[]) {
//        String sdate = "16-09-2019";
//        try {
//            String sDate1 = "16-09-2019";
//            Instant timestamp = Instant.parse(sDate1);
//            ZonedDateTime isttime = timestamp.atZone(ZoneId.of("Asia/Kolkata"));
//
//            System.out.println("test..." + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(isttime));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("error " + e.getMessage());
//        }
//
//
//    }


//    public static void main(String args[]) {
//        //declare variables
//        double weightInKilos = 66;
//        int heightInCms = 157;
//
//
//        //convert measurements
//        double heightInMeters = ((double) heightInCms / 100);
//        double bmi = Math.round((weightInKilos / Math.pow(heightInMeters, 2.0)) * 100) / 100.0;
//        //display output
//        System.out.println("Your BMI is: " + bmi);
//
//        //interpret BMI
//        if (bmi < 18.5) {
//            System.out.print("Underweight");
//        } else if (bmi >= 18.5 && bmi < 25) {
//            System.out.print("Normal");
//        } else if (bmi >= 25 && bmi < 30) {
//            System.out.print("Overweight");
//        } else if (bmi >= 30) {
//            System.out.print("Obese");
//        }
//
//
//    }


    public static void main(String args[]) {
        double weightInKilos = 66;
        int heightInCms = 157;
        int ageInYears = 43;

        int bmrMen = (int) Math.ceil(88.362 + (13.397 * weightInKilos) + (4.799 * heightInCms) - (5.677 * ageInYears));
        int bmrWomen = (int) Math.ceil(447.593 + (9.247 * weightInKilos) + (3.098 * heightInCms) - (4.330 * ageInYears));
        //display output
        System.out.println("Your BMR MEN is: " + bmrMen);
        System.out.println("Your BMR WOMEN is: " + bmrWomen);

    }

}

