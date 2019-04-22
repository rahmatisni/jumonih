package ib.ganz.etoll.helper;

import java.util.Calendar;

public class Waktu
{
    // SEKARANG => YYYY-MM-DD HH:MM:SS

    public static String sekarang()
    {
        Calendar c = Calendar.getInstance();
        return Tanggal.zeroToTrio(c) + " " + addZero(c.get(Calendar.HOUR_OF_DAY)) + ":" + addZero(c.get(Calendar.MINUTE)) + ":" + addZero(c.get(Calendar.SECOND));
    }

    public static String tampil(String s)
    {
        return Tanggal.trioToDuo(s.split(" ")[0]) + " " + dateTimeToHour(s);
    }

    public static String tampilTanggal(String s)
    {
        return Tanggal.trioToDuo(s.split(" ")[0]);
    }

    public static String tampilJam(String s)
    {
        return dateTimeToHour(s);
    }

    public static String dateTimeToDay(String s)
    {
        return s.split(" ")[0].split("-")[0];
    }

    public static String dateTimeToMonth(String s)
    {
        return s.split(" ")[0].split("-")[1];
    }

    public static String dateTimeToYear(String s)
    {
        return s.split(" ")[0].split("-")[2];
    }

    public static String dateTimeToHour(String s)
    {
        String jam = s.split(" ")[1];
        String[] ar = jam.split(":");

        return ar[0] + ":" + ar[1];
    }

    public static Calendar tanggalJamToZero(String s)
    {
        Calendar c = Tanggal.duoToZero(tampilTanggal(s));
        String[] h = tampilJam(s).split(":");
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(h[1]));

        return c;
    }

    private static String addZero(int i)
    {
        return (i + "").length() == 1 ? "0" + i : "" + i;
    }
}
