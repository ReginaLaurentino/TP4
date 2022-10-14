package com.example.tp4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return AgregarFragment.newInstance();
            case 1:
                return ModificarFragment.newInstance();
            case 2:
                return ListarFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return AgregarFragment.TITLE;

            case 1:
                return ModificarFragment.TITLE;

            case 2:
                return ListarFragment.TITLE;
        }
        return super.getPageTitle(position);
    }
}
