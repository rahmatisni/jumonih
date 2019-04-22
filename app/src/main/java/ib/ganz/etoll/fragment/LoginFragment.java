package ib.ganz.etoll.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.activity.LoginRegisteActivity;
import ib.ganz.etoll.activity.MainActivity;
import ib.ganz.etoll.helper.Utilz;
import ib.ganz.etoll.manager.SessionManager;
import ib.ganz.etoll.network.Servize;
import ib.ganz.etoll.response.Responses;

public class LoginFragment extends BaseFragment
{
    @BindView(R.id.eEmail)      EditText eEmail;
    @BindView(R.id.ePassword)   EditText ePassword;
    @BindView(R.id.bLogin)      View bLogin;
    @BindView(R.id.bRegister)   View bRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        Utilz.edtFocus(a, eEmail, R.color.colorPrimary);
        Utilz.edtFocus(a, ePassword, R.color.colorPrimary);

        bLogin.setOnClickListener(x -> { if (checkAndRecheck(eEmail, ePassword) && checkEmail(eEmail)) login(); });
        bRegister.setOnClickListener(x -> ((LoginRegisteActivity) a).openRegister());

        return v;
    }

    private void login()
    {
        lcd().add(Servize.login(txt(eEmail), txt(ePassword)).subscribe(r ->
        {
            hideLoading();

            if (r.getCode() == Responses.USERNAME_WRONG) toast("Email belum terdaftar");
            else if (r.getCode() == Responses.PASSWORD_WRONG) toast("Password tidak cocok");
            else if (r.getCode() == Responses.DATA_EXIST) toast("Akun sudah dipakai untuk login");
            else if (r.getCode() == Responses.SUCCESS)
            {
                toast("Login sukses");
                SessionManager.login(r.getMember());
                MainActivity.go(a);
            }

        }, this::handleError));
    }
}
