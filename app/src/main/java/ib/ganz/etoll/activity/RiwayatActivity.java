package ib.ganz.etoll.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.adapter.RiwayatAdapter;
import ib.ganz.etoll.dataclass.RiwayatData;
import ib.ganz.etoll.network.Servize;

public class RiwayatActivity extends BaseActivityBack
{
    public static void go(Context c) { c.startActivity(new Intent(c, RiwayatActivity.class)); }

    @BindView(R.id.lvRiwayat)   ListView lvRiwayat;

    List<RiwayatData> lRiwayat;
    RiwayatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        ButterKnife.bind(a);

        lRiwayat = new ArrayList<>();
        adapter = new RiwayatAdapter(a, lRiwayat);

        lvRiwayat.setAdapter(adapter);
        getData();
    }

    private void getData()
    {
        cd().add(Servize.getRiwayat().subscribe(r ->
        {
            hide(ctrLoading);
            lRiwayat.clear();
            lRiwayat.addAll(r);
            adapter.notifyDataSetChanged();

        }, this::handleError));
    }
}
