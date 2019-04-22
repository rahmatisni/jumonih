package ib.ganz.etoll.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FraxmendAdapter extends FragmentPagerAdapter
{
    List<Fragment> lf;
    List<String> ls;

    public FraxmendAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void setItem(List<Fragment> lf)
    {
        this.lf = lf;
    }

    public void setTitle(List<String> ls)
    {
        this.ls = ls;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return ls.get(position);
    }

    @Override
    public Fragment getItem(int position)
    {
        return lf.get(position);
    }

    @Override
    public int getCount()
    {
        return lf.size();
    }
}
