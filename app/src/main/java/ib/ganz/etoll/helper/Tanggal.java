package ib.ganz.etoll.helper;

import java.util.Calendar;
import java.util.TimeZone;

public class Tanggal
{
    // ZERO = Calendar.getInstance();
    // UNO = int d, int m, int y
    // DUO = "1 - januari - 1990"
    // TRIO = "1990-1-1"

    // NOTE: TRIO is used to read or write date in sql. The M >= 1 && M <= 12
    // The others are java date format. The M >= 0 && M <= 11

    public static String zeroToDuo(Calendar c)
    {
        return c.get(Calendar.DAY_OF_MONTH) + "-" + MONTH[c.get(Calendar.MONTH)] + "-" + c.get(Calendar.YEAR);
    }

    public static String zeroToTrio(Calendar c)
    {
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String unoToDuo(int d, int m, int y)
    {
        return d + "-" + MONTH[m] + "-" + y;
    }

    public static String unoToTrio(int d, int m, int y)
    {
        return y + "-" + (m + 1) + "-" + d;
    }

    public static Calendar duoToZero(String s)
    {
        String[] a = s.split("-");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[0]));
        c.set(Calendar.MONTH, indexOf(a[1]));
        c.set(Calendar.YEAR, Integer.parseInt(a[2]));

        return c;
    }

    public static int[] duoToUno(String s)
    {
        String trio = duoToTrio(s);
        String[] a = trio.split("-");

        return new int[] { Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]) };
    }

    public static String duoToTrio(String s)
    {
        String[] a = s.split("-");

        int m = 0;
        for (int i = 0; i < MONTH.length; i++)
        {
            if (MONTH[i].equals(a[1]))
            {
                m = i;
                break;
            }
        }

        return a[2] + "-" + (m + 1) + "-" + Integer.parseInt(a[0]);
    }

    public static Calendar trioToZero(String s)
    {
        String[] a = s.split("-");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(a[0]));
        c.set(Calendar.MONTH, Integer.parseInt(a[1]) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(a[2]));

        return c;
    }

    public static int[] trioToUno(String s)
    {
        String[] a = s.split("-");

        return new int[] { Integer.parseInt(a[2]), (Integer.parseInt(a[1]) - 1), Integer.parseInt(a[0]) };
    }

    public static String trioToDuo(String s)
    {
        String[] a = s.split("-");

        return a[2] + "-" + MONTH[Integer.parseInt(a[1]) - 1] + "-" + a[0];
    }

    public static int indexOf(String s) // The M >= 0 && M <= 11
    {
        for (int i = 0; i < MONTH.length; i++)
        {
            if (s.equals(MONTH[i]))
            {
                return i;
            }
        }

        return 0;
    }

    public static String hariIni()
    {
        Calendar c = Calendar.getInstance();
        return Tanggal.zeroToDuo(c);
    }

    private static final String[] MONTH = {
              "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    };
}
