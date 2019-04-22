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

public class RegisterFragment extends BaseFragment
{
    @BindView(R.id.eNama)       EditText eNama;
    @BindView(R.id.ePassword)   EditText ePassword;
    @BindView(R.id.eKonPassword)EditText eKonPassword;
    @BindView(R.id.eEmail)      EditText eEmail;
    @BindView(R.id.eNoTelp)     EditText eNoTelp;
    @BindView(R.id.eAlamat)     EditText eAlamat;
    @BindView(R.id.bRegister)   View bRegister;
    @BindView(R.id.bBack)       View bBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, v);

        Utilz.edtFocus(a, eEmail, R.color.colorPrimary);
        Utilz.edtFocus(a, ePassword, R.color.colorPrimary);

        bRegister.setOnClickListener(x -> { if (checkAndRecheck(eNama, eEmail, ePassword, eKonPassword, eNoTelp, eAlamat) && checkEmail(eEmail)) register(); });
        bBack.setOnClickListener(x -> ((LoginRegisteActivity) a).openLogin());

        return v;
    }

    private void register()
    {
        if (!txt(ePassword).equals(txt(eKonPassword)))
        {
            eKonPassword.requestFocus();
            eKonPassword.setError("Password konfirmasi tidak cocok");
            return;
        }

        lcd().add(Servize.register(txt(eNama), txt(ePassword), txt(eEmail), txt(eNoTelp), txt(eAlamat)).subscribe(r ->
        {
            hideLoading();

            if (r.getCode() == Responses.DATA_EXIST) toast("Email sudah terpakai");
            else if (r.getCode() == Responses.SUCCESS)
            {
                SessionManager.login(r.getMember());
                toast("Registrasi berhasil");
                MainActivity.go(a);
            }

        }, this::handleError));
    }
}
