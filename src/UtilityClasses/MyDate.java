/*
 * Copyright (C) 2014 tezk
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package UtilityClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author tezk
 */
public class MyDate {

    public static String longToTime(long time) {
        /**
         * Returns time in the format HH:mm from a given time in long
         */
        Date date = new Date(time);

        SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
        return (ft.format(date));
    }

    public static String longToDate(long time) {
        /**
         * Returns date in format dd-MM-yy
         */
        Date date = new Date(time);

        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        return ft.format(date);
    }

    public static long stringToLongTime(String time) {
        /**
         * Given time in format HH:mm or HH.mm, converts to long value for that
         * time today
         */
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date t = null;
        // Cheating way to get text value of today... format yyyy-mm-dd
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

        time = sqlDate + " " + time;
        System.out.println(time);
        try {
            t = ft.parse(time);
            System.out.println(t);
        } catch (ParseException e) {
            try {
                ft = new SimpleDateFormat("yyyy-MM-dd HH.mm");
                t = ft.parse(time);
                System.out.println(t);
            } catch (ParseException ed) {
                System.out.println("Unparseable using " + ft);
                return (long) -1;
            }
        }
        return t.getTime();

    }

    public static Date stringToDate(String time) {
        /**
         * Given date on format dd-MM-yyyy converts to Date
         */
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        Date t = null;

        try {
            t = ft.parse(time);
        } catch (ParseException e) {
            return null;
        }
        return t;
    }
    
    public static Date stringToDateShort(String time) {
        /**
         * Given date on format dd-MM-yyyy converts to Date
         */
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date t = null;

        try {
            t = ft.parse(time);
        } catch (ParseException e) {
           // System.err.println("Date parse exception : "+e.getMessage());
            return null;
        }
        return t;
    }
    
        public static Date stringToDateShortNoM(String time) {
        /**
         * Given date on format dd-MM-yyyy converts to Date
         */
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy");
        Date t = null;

        try {
            t = ft.parse(time);
        } catch (ParseException e) {
            //System.err.println("Date parse exception : "+e.getMessage());
            return null;
        }
        return t;
    }

        public static Date stringToDateLong(String time) {
        /**
         * Given date on format dd-MM-yyyy converts to Date
         */
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        Date t = null;

        try {
            t = ft.parse(time);
        } catch (ParseException e) {
         //   System.err.println("Date parse exception : "+e.getMessage());
            return null;
        }
        return t;
    }
    
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int getDaysBetween(Date a, Date b) {
        return (int) TimeUnit.DAYS.convert(b.getTime() - a.getTime(), TimeUnit.MILLISECONDS);
    }

    public static int getYearsBetween(Date a, Date b) {
        Calendar aCal = getCalendar(a);
        Calendar bCal = getCalendar(b);
        int diff = bCal.get(Calendar.YEAR) - aCal.get(Calendar.YEAR);

        if (aCal.get(Calendar.MONTH) > bCal.get(Calendar.MONTH)
                || (aCal.get(Calendar.MONTH) == bCal.get(Calendar.MONTH) && aCal.get(Calendar.DATE) > bCal.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        return cal;
    }

    public static int getDaysBetween(LocalDate a, LocalDate b) {
        return (int) ChronoUnit.DAYS.between(a, b);
    }

    public static int getDaysBetween(XMLGregorianCalendar a, XMLGregorianCalendar b) {
        return getDaysBetween(toLocalDate(a), toLocalDate(b));
    }
    
    /*
     * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            System.err.println("Error converting dates : " + ex.getMessage());
        }
        return xmlCalendar;
    }

    /*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    public static String toString(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return longToDate(toDate(calendar).getTime());
    }
    
    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate fromDate) {
        if (fromDate == null)
            return null;
        GregorianCalendar gcal = GregorianCalendar.from(fromDate.atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xcal=null;
        try {
            xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (DatatypeConfigurationException e) {
            System.err.println("Error converting dates : "+e.getMessage());
        }
        return xcal;
    }

    public static LocalDate toLocalDate(XMLGregorianCalendar aCal) {
        if (aCal==null)
            return null;
        return aCal.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }
    
}
