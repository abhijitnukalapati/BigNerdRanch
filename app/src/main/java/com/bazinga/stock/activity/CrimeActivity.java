package com.bazinga.stock.activity;

import android.app.Fragment;

import com.bazinga.stock.fragment.CrimeFragment;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CrimeFragment.newInstance();
    }
}
