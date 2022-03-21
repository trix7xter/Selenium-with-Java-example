package pages;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class StaticHelper {

    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static String getCurrentMonth() {
        ZoneId zoneId = ZoneId.of( "Europe/Moscow" );
        ZonedDateTime now = ZonedDateTime.now( zoneId );
        Month month = now.getMonth();
        return month.getDisplayName( TextStyle.FULL , new Locale("ru") );
    }
}