package ib.ganz.etoll.manager;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

public class ExitManager
{
    private static boolean doubleBackToExitPressedOnce = false;

    public static void go(Activity a)
    {
        if (doubleBackToExitPressedOnce)
        {
            a.finish();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Toast.makeText(a, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}
