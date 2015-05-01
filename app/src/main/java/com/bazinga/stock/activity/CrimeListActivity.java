package com.bazinga.stock.activity;

import android.app.Fragment;

import com.bazinga.stock.fragment.CrimeListFragment;

/**
 * Created by abhijitnukalapati on 4/30/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CrimeListFragment.newInstance();
    }
}
