package com.bazinga.stock.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.bazinga.stock.R;
import com.bazinga.stock.fragment.CrimeFragment;
import com.bazinga.stock.model.Crime;
import com.bazinga.stock.model.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by abhijitnukalapati on 5/4/15.
 */
public class CrimePagerActivity extends ActionBarActivity {
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the view pager
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        // get the list of crimes
        mCrimes = CrimeLab.getInstance(this).getCrimes();

        // set up the adapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // do nothing
            }

            @Override
            public void onPageSelected(int position) {
                String title = mCrimes.get(position).getTitle();
                if(!TextUtils.isEmpty(title)){
                    setTitle(title);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing
            }
        });

        UUID id = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        // find the position to go to
        // by iterating through the list of crimes
        for(int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(id)) {
                mViewPager.setCurrentItem(i);
                break; // exit from the loop as we have found our item
            }
        }

        // use this method to set the number of pages that need to
        // be pre-loaded for the view pager on either side of the current page;
        // 1 is default
        mViewPager.setOffscreenPageLimit(1);

    }
}
