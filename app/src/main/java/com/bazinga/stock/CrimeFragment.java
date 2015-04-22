package com.bazinga.stock;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bazinga.stock.model.Crime;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeFragment extends Fragment {

    @InjectView(R.id.crime_title) EditText mCrimeTitleField;

    private Crime mCrime;


    public static CrimeFragment newInstance(){
        CrimeFragment crimeFragment = new CrimeFragment();
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.inject(this, view);

        mCrimeTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });

        return view;
    }
}
