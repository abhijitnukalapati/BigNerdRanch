package com.bazinga.stock;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bazinga.stock.model.Crime;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeFragment extends Fragment {

    @InjectView(R.id.crime_title) EditText vCrimeTitleField;
    @InjectView(R.id.crime_date) Button vDateButton;
    @InjectView(R.id.crime_solved) CheckBox vCrimeSolvedCheckBox;

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

        vCrimeTitleField.addTextChangedListener(new TextWatcher() {
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

        // display the date in the format "Tuesday, Oct 12, 2012"
        vDateButton.setText(new SimpleDateFormat("EEEE, MMM dd, yyyy").format(mCrime.getDate()));
        vDateButton.setEnabled(false); // we don't want the user to interact with the button

        vCrimeSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return view;
    }
}
