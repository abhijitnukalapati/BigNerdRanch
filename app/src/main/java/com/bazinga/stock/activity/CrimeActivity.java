package com.bazinga.stock.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.bazinga.stock.CrimeFragment;
import com.bazinga.stock.R;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // try to get the fragment associated with the activity container, if it exists
        // this can happen if the activity is being re-created due to an orientation change
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);

        // otherwise add the fragment to the container
        if (fragment == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, CrimeFragment.newInstance())
                    .commit();
        }

    }
}
