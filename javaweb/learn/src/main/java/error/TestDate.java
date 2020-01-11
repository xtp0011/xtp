package error;


import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(Clock.systemDefaultZone().millis());

    }
}
