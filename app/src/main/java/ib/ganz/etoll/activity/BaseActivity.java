package ib.ganz.etoll.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import ib.ganz.etoll.R;
import ib.ganz.etoll.helper.Gxon;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by limakali on 3/17/2018.
 */

public class BaseActivity extends AppCompatActivity
{
    protected static final String TAG = "app:BaseActivity";

    ProgressDialog progressDialog;
    CompositeDisposable compositeDisposable;
    FragmentManager fm;
    BaseActivity a;

    @Nullable @BindView(R.id.toolbar)       Toolbar toolbar;
    @Nullable @BindView(R.id.tTitle)        TextView tTitle;
    @Nullable @BindView(R.id.ctrLoading)    View ctrLoading;
    @Nullable @BindView(R.id.ctrNodata)     View ctrNodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        compositeDisposable = new CompositeDisposable();
        fm = getSupportFragmentManager();
        a = this;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }

    public CompositeDisposable cd()
    {
        return compositeDisposable;
    }

    public CompositeDisposable lcd()
    {
        showLoading();
        return compositeDisposable;
    }

    public void toast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void toastKoneksi()
    {
        toast("Periksa koneksi Anda!");
    }

    public void handleError(Throwable e)
    {
        hideLoading();
        toastKoneksi();

        if (ctrLoading != null) hide(ctrLoading);
        if (ctrNodata != null) show(ctrNodata);
    }

    public void toggleVisibility(boolean b, View... vs)
    {
        if (b) show(vs);
        else hide(vs);
    }

    public void show(boolean b, View... vs)
    {
        if (b) show(vs);
    }

    public void show(View... vs)
    {
        for (View v : vs)
        {
            if (v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
        }
    }

    public void hide(boolean b, View... vs)
    {
        if (b) hide(vs);
    }

    public void hide(View... vs)
    {
        for (View v : vs)
        {
            if (v.getVisibility() != View.GONE) v.setVisibility(View.GONE);
        }
    }

    public void showLoading()
    {
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    public void hideLoading()
    {
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    public boolean isEmpty(TextView t)
    {
        return txt(t).isEmpty();
    }

    public String txt(TextView e)
    {
        return e.getText().toString().trim();
    }

    public boolean check(EditText... edts)
    {
        for (EditText e : edts)
        {
            e.setError(null);
        }

        for (EditText e : edts)
        {
            if (e.getText().toString().trim().isEmpty())
            {
                e.requestFocus();
                e.setError("Isi semua field!");
                return false;
            }
        }

        return true;
    }

    public void clear(EditText... edts)
    {
        for (EditText e : edts)
        {
            e.setText("");
        }
    }

    protected static <T> void open(Context c, Class<?> activityToOpen, T t)
    {
        Intent i = new Intent(c, activityToOpen);
        i.putExtra("a", Gxon.toJsonObject(t));
        c.startActivity(i);
    }

    protected <T> T getArg(Class<T> t)
    {
        return Gxon.fromJsonObject(getIntent().getStringExtra("a"), t);
    }
}
