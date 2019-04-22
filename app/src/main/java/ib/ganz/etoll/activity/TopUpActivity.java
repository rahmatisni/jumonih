package ib.ganz.etoll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.manager.SessionManager;
import ib.ganz.etoll.network.Servize;
import ib.ganz.etoll.response.Responses;

public class TopUpActivity extends BaseActivityBack
{
    public static void go(Context c) { c.startActivity(new Intent(c, TopUpActivity.class)); }

    @BindView(R.id.eVoucher)    EditText eVoucher;
    @BindView(R.id.bTopUp)      TextView bTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        ButterKnife.bind(this);

        bTopUp.setOnClickListener(x -> { if (check(eVoucher)) topUp(); });
    }

    private void topUp()
    {
        lcd().add(Servize.topUp(txt(eVoucher)).subscribe(r ->
        {
            hideLoading();

            if (r.getCode() == Responses.SUCCESS)
            {
                SessionManager.setSaldo(r.getSaldo());
                toast("Top up berhasil");
                finish();
            }
            else if (r.getCode() == Responses.DATA_EXIST)
            {
                toast("Voucher sudah digunakan");
            }
            else if (r.getCode() == Responses.DATA_NOT_EXIST)
            {
                toast("Voucher salah");
            }

        }, this::handleError));
    }
}
