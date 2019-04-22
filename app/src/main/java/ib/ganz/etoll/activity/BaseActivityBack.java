package ib.ganz.etoll.activity;

/**
 * Created by limakali on 1/15/2018.
 */

public class BaseActivityBack extends BaseActivity
{
    @Override
    protected void onResume()
    {
        super.onResume();

        if (getSupportActionBar() == null) return;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finishActivity();
        return super.onSupportNavigateUp();
    }

    protected void finishActivity()
    {
        finish();
    }
}
