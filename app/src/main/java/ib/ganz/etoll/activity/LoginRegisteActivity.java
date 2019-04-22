package ib.ganz.etoll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.adapter.FraxmendAdapter;
import ib.ganz.etoll.customclass.NonScrollViewPager;
import ib.ganz.etoll.fragment.LoginFragment;
import ib.ganz.etoll.fragment.RegisterFragment;
import ib.ganz.etoll.manager.ExitManager;
import ib.ganz.etoll.manager.SessionManager;

public class LoginRegisteActivity extends BaseActivity
{
    public static void go(Activity c)
    {
        Intent i = new Intent(c, LoginRegisteActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);
        c.finish();
    }

    @BindView(R.id.viewPager) NonScrollViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (SessionManager.isLogin())
        {
            MainActivity.go(this);
        }

        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);

        FraxmendAdapter adapter = new FraxmendAdapter(fm);
        adapter.setItem(Arrays.asList(new LoginFragment(), new RegisterFragment()));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed()
    {
        if (viewPager.getCurrentItem() == 1) openLogin();
        else ExitManager.go(a);
    }

    public void openLogin()
    {
        viewPager.setCurrentItem(0);
    }

    public void openRegister()
    {
        viewPager.setCurrentItem(1);
    }
}
