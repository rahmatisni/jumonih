package ib.ganz.etoll.manager;

import android.content.Context;
import android.content.SharedPreferences;

import ib.ganz.etoll.dataclass.MemberData;
import ib.ganz.etoll.helper.Gxon;


/**
 * Created by limakali on 3/18/2018.
 */

public class SessionManager
{
    private static final String MEMBER_DATA = "MemberData",
                                IS_LOGIN = "islogin";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void init(Context c)
    {
        if (sp == null || editor == null)
        {
            sp = c.getSharedPreferences("smartUser", 0);
            editor = sp.edit();
        }
    }

    public static void login(MemberData s)
    {
        editor.putString(MEMBER_DATA, Gxon.toJsonObject(s)).commit();
        editor.putBoolean(IS_LOGIN, true).commit();
    }

    public static boolean isLogin()
    {
        return sp.getBoolean(IS_LOGIN, false);
    }

    public static void logout()
    {
        editor.putString(MEMBER_DATA, "").commit();
        editor.putBoolean(IS_LOGIN, false).commit();
    }

    // ------------  -_-_-_-  --------------

    public static String getIdMember()
    {
        return getMemberData().getIdMember();
    }

    public static MemberData getMemberData()
    {
        return Gxon.fromJsonObject(sp.getString(MEMBER_DATA, ""), MemberData.class);
    }

    public static void setSaldo(String s)
    {
        MemberData m = getMemberData();
        m.setSaldo(s);
        setMemberData(m);
    }

    public static void setMemberData(MemberData s)
    {
        editor.putString(MEMBER_DATA, Gxon.toJsonObject(s)).commit();
    }
}
