package fr.alexandremarcq.familycalendar.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class UtilsFunctions {
    public static int[] dateConverter(long milliSeconds) {
        String dateString = new SimpleDateFormat("ddMM", Locale.getDefault())
                .format(milliSeconds);
        int day = Integer.parseInt(dateString.substring(0, 2));
        int month = Integer.parseInt(dateString.substring(2)) - 1;
        return new int[]{day, month};
    }
}
