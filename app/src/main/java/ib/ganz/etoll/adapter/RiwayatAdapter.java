package ib.ganz.etoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.dataclass.RiwayatData;
import ib.ganz.etoll.helper.Utilz;
import ib.ganz.etoll.helper.Waktu;

public class RiwayatAdapter extends ArrayAdapter<RiwayatData>
{
    @BindView(R.id.iRiwayat)    ImageView iRiwayat;
    @BindView(R.id.tJenis)      TextView tJenis;
    @BindView(R.id.tNominal)    TextView tNominal;

    public RiwayatAdapter(@NonNull Context context, @NonNull List<RiwayatData> objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent)
    {
        if (v == null) v = LayoutInflater.from(getContext()).inflate(R.layout.item_riwayat, parent, false);
        ButterKnife.bind(this, v);

        RiwayatData r = getItem(position);

        if (r.getJenis() == RiwayatData.TOP_UP)
        {
            iRiwayat.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_attach_money_black_24dp));
            iRiwayat.setBackground(getContext().getResources().getDrawable(R.drawable.ctr_lined_round_accent));
            iRiwayat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tJenis.setText("Top up: " + r.getTopUpData().getToken() + "  ->  " + Waktu.tampil(r.getTopUpData().getTgl()));
            tNominal.setText(Utilz.harga(r.getTopUpData().getNominal()));
        }
        else if (r.getJenis() == RiwayatData.SHARING)
        {
            iRiwayat.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_share_black_24dp));
            iRiwayat.setBackground(getContext().getResources().getDrawable(R.drawable.ctr_lined_round_blue));
            iRiwayat.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark), android.graphics.PorterDuff.Mode.SRC_IN);
            tJenis.setText("Sharing: " + r.getSharingData().getTujuan().getNamaMember() + "(" + r.getSharingData().getTujuan().getIdMember() + ")  ->  " + Waktu.tampil(r.getSharingData().getTgl()));
            tNominal.setText(Utilz.harga(r.getSharingData().getNominal()));
        }

        return v;
    }
}
