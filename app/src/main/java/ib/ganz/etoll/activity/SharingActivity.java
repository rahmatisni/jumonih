package ib.ganz.etoll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.dataclass.MemberData;
import ib.ganz.etoll.helper.ListDialog;
import ib.ganz.etoll.helper.Utilz;
import ib.ganz.etoll.manager.SessionManager;
import ib.ganz.etoll.network.Servize;

public class SharingActivity extends BaseActivityBack
{
    public static void go(Context c) { c.startActivity(new Intent(c, SharingActivity.class)); }

    @BindView(R.id.eMember)     EditText eMember;
    @BindView(R.id.eNominal)    EditText eNominal;
    @BindView(R.id.bSharing)    TextView bSharing;

    private List<MemberData> lMember;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        ButterKnife.bind(a);

        lMember = new ArrayList<>();
        getMember(null, null);

        Utilz.edtRupiah(eNominal);
        eMember.setOnClickListener(x -> ListDialog.go(lMember, MemberData.class, fm, this::getMember, m ->
        {
            eMember.setTag(m);
            eMember.setText(m.getIdMember());
        }));
        bSharing.setOnClickListener(x -> { if (check(eMember, eNominal)) sharing(); });
    }

    private void getMember(ListDialog.OnDataLoaded<MemberData> ol, ListDialog.OnDataError oe)
    {
        cd().add(Servize.getMember().subscribe(r ->
        {
            lMember.clear();
            lMember.addAll(r);
            if (ol != null) ol.onLoaded(lMember);

        }, e -> { if (oe != null) oe.onError(); }));
    }

    private void sharing()
    {
        if (Integer.parseInt(SessionManager.getMemberData().getSaldo()) < Integer.parseInt(Utilz.getNum(eNominal)))
        {
            eNominal.requestFocus();
            eNominal.setError("Saldo anda tidak mencukupi");
            return;
        }

        lcd().add(Servize.share(((MemberData) eMember.getTag()).getIdMember(), Utilz.getNum(eNominal)).subscribe(r ->
        {
            hideLoading();
            SessionManager.setSaldo(String.valueOf(Integer.parseInt(SessionManager.getMemberData().getSaldo()) - Integer.parseInt(Utilz.getNum(eNominal))));
            toast("Sharing saldo berhasil");
            finish();
        }));
    }
}
