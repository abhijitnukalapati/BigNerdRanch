package com.bazinga.stock.activity;

import android.app.Fragment;

import com.bazinga.stock.fragment.CrimeFragment;

import java.util.UUID;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(uuid);
    }
}
