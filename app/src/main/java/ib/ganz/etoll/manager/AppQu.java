package ib.ganz.etoll.manager;

import android.app.Application;
import android.os.Build;

/**
 * Created by limakali on 3/18/2018.
 */

public class AppQu extends Application
{
    private static AppQu appQu;

    public static AppQu get() { return appQu; }

    @Override
    public void onCreate()
    {
        super.onCreate();

        appQu = this;
        SessionManager.init(this);
    }
}
