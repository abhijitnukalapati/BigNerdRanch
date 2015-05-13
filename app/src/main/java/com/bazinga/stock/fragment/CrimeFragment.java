package com.bazinga.stock.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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

import com.bazinga.stock.R;
import com.bazinga.stock.model.Crime;
import com.bazinga.stock.model.CrimeLab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by abhijitnukalapati on 4/21/15.
 */
public class CrimeFragment extends Fragment {

    @InjectView(R.id.crime_title) EditText vCrimeTitleField;
    @InjectView(R.id.crime_date) Button vDateButton;
    @InjectView(R.id.crime_solved) CheckBox vCrimeSolvedCheckBox;

    public static String EXTRA_CRIME_ID = "com.bazinga.stock.EXTRA_CRIME_ID";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEEE, MMM dd, yyyy");
    private static final String DIALOG_CRIME_DATE = "DIALOG_CRIME_DATE";
    private static final String DIALOG_CRIME_TIME = "DIALOG_CRIME_TIME";

    private static final int REQUEST_DATE = 0x001;

    private Crime mCrime;

    public static CrimeFragment newInstance(UUID id){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CRIME_ID, id);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from the arguments supplied to the
        // fragment
        if (getArguments() != null) {
            UUID id = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
            mCrime = CrimeLab.getInstance(getActivity()).getCrime(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.inject(this, view);

        vCrimeTitleField.setText(mCrime.getTitle());

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
        updateDate();
        vDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // show the date picker, so that the user can change the date
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());

                // set the target fragment, so that we can report back any changes
                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePickerFragment.show(getFragmentManager(), DIALOG_CRIME_DATE);

//                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(
//                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));
//
//                timePickerFragment.show(getFragmentManager(), DIALOG_CRIME_TIME);

            }
        });

        vCrimeSolvedCheckBox.setChecked(mCrime.isSolved());
        vCrimeSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_DATE:
                    Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                    mCrime.setDate(date);
                    updateDate();
            }
        }
    }

    private void updateDate(){
        vDateButton.setText(DATE_FORMAT.format(mCrime.getDate()));
    }
}
