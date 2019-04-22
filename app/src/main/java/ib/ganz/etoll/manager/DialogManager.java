package ib.ganz.etoll.manager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Calendar;

import ib.ganz.etoll.helper.I;
import ib.ganz.etoll.helper.Tanggal;


/**
 * Created by limakali on 3/18/2018.
 */

public class DialogManager
{
    public static void info(Activity a, String s)
    {
        info(a, s, null);
    }

    public static void info(Activity a, String s, I.VoidEmpty o)
    {
        new AlertDialog.Builder(a)
                .setMessage(s)
                .setPositiveButton("Ok", (di, v) -> {
                    if (o != null) o.o();
                })
                .show();
    }

    public static void confirm(Activity a, String s, I.VoidEmpty o)
    {
        new AlertDialog.Builder(a)
                .setMessage(s)
                .setPositiveButton("Ok", (di, v) -> o.o())
                .setNegativeButton("Batal", null)
                .show();
    }

    public static void tanggal(Activity a, String tgl, String min, String max, I.VoidString o)
    {
        Calendar c = Calendar.getInstance();

        if (!tgl.trim().isEmpty())
        {
            String[] ars = tgl.split("-");
            c.set(Calendar.YEAR, Integer.parseInt(ars[2]));
            c.set(Calendar.MONTH, Tanggal.indexOf(ars[1]));
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ars[0]));
        }

        DatePickerDialog dpd = new DatePickerDialog(a, (dp, y, m, d) -> o.o(Tanggal.unoToDuo(d, m, y)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        if (min != null && !min.isEmpty())
            dpd.getDatePicker().setMinDate(Tanggal.duoToZero(min).getTimeInMillis());
        if (max != null && !max.isEmpty())
            dpd.getDatePicker().setMaxDate(Tanggal.duoToZero(max).getTimeInMillis());
        dpd.show();
    }

//    public static void jumlah(Context c, String stokTersedia, I.VoidInt o)
//    {
//        if (Integer.parseInt(stokTersedia) == 0)
//        {
//            toast(c, "Stok barang ini sudah habis");
//            return;
//        }
//
//        View v = LayoutInflater.from(c).inflate(R.layout.dialog_jumlah, null);
//        EditText eJumlah = v.findViewById(R.id.eJumlah);
//
//        AlertDialog ad = new AlertDialog.Builder(c)
//                .setTitle("Masukkan jumlah barang")
//                .setView(v)
//                .setPositiveButton("Ok", null)
//                .setNegativeButton("Cancel", null)
//                .create();
//
//        ad.setOnShowListener(di -> ((AlertDialog) di).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(x ->
//        {
//            if (eJumlah.getText().toString().trim().isEmpty())
//            {
//                eJumlah.requestFocus();
//                eJumlah.setError("Masukkan jumlah dahulu");
//            }
//            else
//            {
//                int jumlah = Integer.parseInt(eJumlah.getText().toString().trim());
//                if (jumlah > Integer.parseInt(stokTersedia))
//                {
//                    toast(c, "Jumlah yang anda masukkan melebihi stok tersedia");
//                }
//                else
//                {
//                    o.o(jumlah);
//                    ad.dismiss();
//                }
//            }
//        }));
//
//        ad.show();
//    }
//
//    /* ini untuk detail toko yg akan dikunjungi */
//    public static void detailToko(MainActivity a, TokoData t)
//    {
//        detailToko(a, t, -100, "", true);
//    }
//
//    /* ini untuk detail toko yg gk bisa dikunjungi */
//    public static void detailToko(MainActivity a, TokoData t, int action, String tgl)
//    {
//        detailToko(a, t, action, tgl, false);
//    }
//
//    private static void detailToko(MainActivity a, TokoData t, int action, String tgl, boolean bisaKunjungi)
//    {
//        View v                  = a.getLayoutInflater().inflate(R.layout.dialog_det_toko, null);
//        ImageViewQu iToko       = v.findViewById(R.id.iToko);
//        TextView tNamaToko      = v.findViewById(R.id.tNamaToko);
//        TextView tAlamat        = v.findViewById(R.id.tAlamat);
//        TextView tNamaPemilik   = v.findViewById(R.id.tNamaPemilik);
//        TextView tNoTelp        = v.findViewById(R.id.tNoTelp);
//        TextView tKota          = v.findViewById(R.id.tKota);
//        View bKunjungi          = v.findViewById(R.id.bKunjungi);
//        View ctrNonKunjungi     = v.findViewById(R.id.ctrNonKunjungi);
//        TextView tAction        = v.findViewById(R.id.tAction);
//        TextView tTgl           = v.findViewById(R.id.tTgl);
//
//        AlertDialog ad = new AlertDialog.Builder(a)
//                .setView(v)
//                .show();
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(ad.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = 850;
//        ad.getWindow().setAttributes(lp);
//
//        iToko.setOnlineImage(t.getGambar());
//        iToko.setOnClickListener(x -> ZoomImageActivity.goWithResult(a, t.getGambar()));
//        tNamaToko.setText(t.getNamaToko());
//        tAlamat.setText(t.getAlamat());
//        tNamaPemilik.setText(t.getNamaPemilik());
//        tNoTelp.setText(t.getNoTelp());
//        tKota.setText(t.getArea().getNamaArea());
//
//        if (bisaKunjungi)
//        {
//            a.hide(ctrNonKunjungi);
//
//            bKunjungi.setOnClickListener(x -> DialogManager.confirm(a, "Kunjungi toko ini?", () ->
//            {
//                a.showLoading();
//                MyLastLocation.getInstance().getLocation(l -> a.cd().add(P.addKunjunngan(t.getIdToko(), Waktu.sekarang(), l.getLatitude(), l.getLongitude()).subscribe(r ->
//                {
//                    a.hideLoading();
//
//                    if (r.getCode() == DATA_EXIST)
//                        a.toast("Anda sudah mengunjungi toko ini pada hari ini");
//                    else
//                    {
//                        ad.dismiss();
//                        a.refreshToko();
//                        a.toast("Berhasil menambahkan kunjungan!");
//                        SessionManager.setHaveToReloadDashboard(true);
//                    }
//
//                }, a::handleError)));
//            }));
//        }
//        else
//        {
//            a.hide(bKunjungi);
//
//            tAction.setText(action == HistoryData.KUNJUNGAN ? "Dikunjungi:" : "Ditambahkan:");
//            tTgl.setText(Waktu.tampil(tgl));
//        }
//    }

    private static void toast(Context c, String s)
    {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }
}
