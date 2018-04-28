package java8.core.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

// 日期相关
/**
 * LocalDate是一个不可变的类，它表示默认格式(yyyy-MM-dd)的日期，
 * 我们可以使用now()方法得到当前时间，
 * 也可以提供输入年份、月份和日期的输入参数来创建一个LocalDate实例。
 * 该类为now()方法提供了重载方法，我们可以传入ZoneId来获得指定时区的日期。
 *
 */
public class Test01LocalDateExample {
 
    public static void main(String[] args) {
 
        //Current Date
        LocalDate today = LocalDate.now();
        System.out.println("Current Date="+today);
 
        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date="+firstDay_2014);
 
        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException: 
        //Invalid date 'February 29' as '2014' is not a leap year
 
        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata);
 
        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));
 
        //Getting date from the base date i.e 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= "+dateFromBase);
 
        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014="+hundredDay2014);
    }
 
}