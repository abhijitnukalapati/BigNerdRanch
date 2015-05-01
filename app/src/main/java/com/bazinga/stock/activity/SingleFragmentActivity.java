package com.bazinga.stock.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.bazinga.stock.R;

/**
 * Created by abhijitnukalapati on 4/30/15.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // try to get the fragment associated with the activity container, if it exists
        // this can happen if the activity is being re-created due to an orientation change
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);

        // otherwise add the fragment to the container
        if (fragment == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
        }
    }
}
