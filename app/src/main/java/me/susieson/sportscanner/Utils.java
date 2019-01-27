package me.susieson.sportscanner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Utils {

    static String formatDateTime(Date createdAt) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CANADA);
        return dateFormat.format(createdAt);
    }

}
