package ib.ganz.etoll.Transaksi.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ib.ganz.etoll.R;
import ib.ganz.etoll.Transaksi.adapter.ProjectsListAdapter;
import ib.ganz.etoll.Transaksi.model.IndoorProject;
import ib.ganz.etoll.Transaksi.utils.RecyclerItemClickListener;

import ib.ganz.etoll.activity.RiwayatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemClickListener.OnItemClickListener {

    public static void go(Context c) {
        c.startActivity(new Intent(c, HomeActivity.class));
    }

    private Realm realm;
    private RealmResults<IndoorProject> projects;
    private RecyclerView mRecyclerView;
    private ProjectsListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        // Create a new empty instance of Realm

        // Clear the realm from last time
//        Realm.deleteRealm(realmConfiguration);

        realm = Realm.getInstance(realmConfiguration);

        projects = realm.where(IndoorProject.class).findAll();
        if (projects.isEmpty()) {
            Snackbar.make(fab, "Empty List, Try creating project", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        // specify an adapter
        mAdapter = new ProjectsListAdapter(projects);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, UnifiedNavigationActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.projects_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, this));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == fab.getId()) {
            Intent intent = new Intent(this, NewProjectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        IndoorProject project = projects.get(position);
        intent.putExtra("id", project.getId());
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
