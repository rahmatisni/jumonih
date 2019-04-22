package ib.ganz.etoll.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import ib.ganz.etoll.R;
import ib.ganz.etoll.activity.BaseActivity;
import ib.ganz.etoll.helper.I;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by limakali on 6/3/2018.
 */

public class BaseFragment extends Fragment
{
    protected Context c;
    protected BaseActivity a;
    ProgressDialog progressDialog;
    CompositeDisposable compositeDisposable;
    FragmentManager fm;

    @Nullable @BindView(R.id.ctrNodata)   public View ctrNoData;
    @Nullable @BindView(R.id.ctrLoading)  public View ctrLoading;

    @Override
    public void onAttach(Context c)
    {
        super.onAttach(c);

        this.c = c;
        this.a = (BaseActivity) c;

        progressDialog = new ProgressDialog(c);
        compositeDisposable = new CompositeDisposable();
        fm = getChildFragmentManager();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
    }

    public CompositeDisposable ocd()
    {
        return compositeDisposable;
    }

    public CompositeDisposable cd()
    {
        if (ctrLoading != null) show(ctrLoading);
        if (ctrNoData != null) hide(ctrNoData);

        return compositeDisposable;
    }

    public CompositeDisposable lcd()
    {
        showLoading();
        return compositeDisposable;
    }

    public void toast(String s)
    {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
        if (ctrNoData != null) show(ctrNoData);
    }

    public void show(View... vs)
    {
        for (View v : vs)
        {
            if (v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
        }
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

    public String txt(TextView e)
    {
        return e.getText().toString().trim();
    }

    public boolean charLessThan(TextView e, int i)
    {
        return txt(e).length() < i;
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

    public boolean checkAndRecheck(EditText... edts)
    {
        if (check(edts))
        {
            for (EditText e : edts)
            {
                if (txt(e).contains("'"))
                {
                    e.requestFocus();
                    e.setError("Tidak boleh berisi karakter petik satu");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean check(boolean b, I.VoidEmpty o)
    {
        if (b) o.o();
        return !b;
    }

    public boolean checkEmail(TextView e)
    {
        String email = txt(e);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches())
        {
            toast("Email tidak valid");
            return false;
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
}
