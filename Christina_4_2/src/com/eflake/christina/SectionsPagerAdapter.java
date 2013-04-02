package com.eflake.christina;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
 * sections of the app.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	 public SectionsPagerAdapter(FragmentManager fm) {
         super(fm);
     }

     @Override
     public Fragment getItem(int i) {
    	 
         Fragment fragment = FragmentFactory.product(i);
//         Bundle args = new Bundle();
//         args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
//         fragment.setArguments(args);
         return fragment;
     }

     @Override
     public int getCount() {
         return 3;
     }

     @Override
     public CharSequence getPageTitle(int position) {
         switch (position) {
             case 0: return "One";
             case 1: return "Two";
             case 2: return "Three";
         }
         return null;
     }

}
