package com.zxhl.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/29.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT=4;
    private FragmentUtils fmu1=null;
    private FragmentUtils fmu2=null;
    private FragmentUtils fmu3=null;
    private FragmentUtils fmu4=null;

    public MyFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
        fmu1=new FragmentUtils("ONE",FragmentUtils.PAG_ONE);
        fmu2=new FragmentUtils("TWO",FragmentUtils.PAG_TWO);
        fmu3=new FragmentUtils("THREE",FragmentUtils.PAG_THREE);
        fmu4=new FragmentUtils("FOUR",FragmentUtils.PAG_FOUR);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position)
        {
            case FragmentUtils.PAG_ONE:
                fragment=fmu1;
                break;
            case FragmentUtils.PAG_TWO:
                fragment=fmu2;
                break;
            case FragmentUtils.PAG_THREE:
                fragment=fmu3;
                break;
            case FragmentUtils.PAG_FOUR:
                fragment=fmu4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
