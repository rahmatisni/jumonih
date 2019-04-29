package ib.ganz.etoll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ib.ganz.etoll.R;
import ib.ganz.etoll.Transaksi.ui.HomeActivity;
import ib.ganz.etoll.dataclass.MemberData;
import ib.ganz.etoll.helper.Utilz;
import ib.ganz.etoll.manager.DialogManager;
import ib.ganz.etoll.manager.ExitManager;
import ib.ganz.etoll.manager.SessionManager;
import ib.ganz.etoll.network.Servize;

public class MainActivity extends BaseActivity
{
    public static void go(Activity c)
    {
        Intent i = new Intent(c, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(i);
        c.finish();
    }

    @BindView(R.id.toolbar)         Toolbar toolbar;
    @BindView(R.id.drawer_layout)   DrawerLayout drawer;
    @BindView(R.id.nav_view)        NavigationView navigationView;
    @BindView(R.id.bTopUp)          TextView bTopUp;
    @BindView(R.id.bRiwayat)        TextView bRiwayat;
    @BindView(R.id.bSharing)        TextView bSharing;
    @BindView(R.id.bInfo)           TextView bInfo;
    @BindView(R.id.bTransaksi)      TextView bTransaksi;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(a);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(x -> IpActivity.go(a));

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(l);
        bTopUp.setOnClickListener(x -> TopUpActivity.go(a));
        bSharing.setOnClickListener(x -> SharingActivity.go(a));
        bRiwayat.setOnClickListener(x -> RiwayatActivity.go(a));
        bTransaksi.setOnClickListener(x -> HomeActivity.go(a));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setHeaderNavView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void setHeaderNavView()
    {
        View v = navigationView.getHeaderView(0);
        TextView tNama = v.findViewById(R.id.tNama);
        TextView tId = v.findViewById(R.id.tId);
        TextView tSaldo = v.findViewById(R.id.tSaldo);
        TextView bRefresh = v.findViewById(R.id.bRefresh);

        MemberData m = SessionManager.getMemberData();
        tNama.setText(m.getNamaMember());
        tId.setText(m.getIdMember());
        tSaldo.setText(Utilz.harga(m.getSaldo()));
        bRefresh.setOnClickListener(x ->
        {
            tSaldo.setText("Loading...");
            cd().add(Servize.refreshSaldo().subscribe(r ->
            {
                hideLoading();
                tSaldo.setText(r);
                SessionManager.setSaldo(r);
            }, e ->
            {
                hideLoading();
                toast("Gagal mendapatkan data");
                tSaldo.setText(m.getSaldo());
            }));
        });
    }

    NavigationView.OnNavigationItemSelectedListener l = item ->
    {
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {

        }
        else if (id == R.id.nav_transaksi)
        {

        }
        else if (id == R.id.nav_sharing)
        {
            SharingActivity.go(a);
        }
        else if (id == R.id.nav_top_up)
        {
            TopUpActivity.go(a);
        }
        else if (id == R.id.nav_riwayat)
        {

        }
        else if (id == R.id.nav_info)
        {

        }
        else if (id == R.id.nav_edit)
        {

        }
        else if (id == R.id.nav_logout)
        {
            DialogManager.confirm(a, "Yakin mau logout?", () ->
            {
                SessionManager.logout();
                LoginRegisteActivity.go(a);
            });
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            ExitManager.go(a);
        }
    }
}
