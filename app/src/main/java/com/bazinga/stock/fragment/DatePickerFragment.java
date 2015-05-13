package com.bazinga.stock.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.bazinga.stock.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple Fragment which contains a DatePicker
 *
 * According to some sources, the {@link android.app.DatePickerDialog}
 * is error-prone. So having a custom date picker fragment is
 * a sensible workaround
 *
 * Created by abhijitnukalapati on 5/5/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePicker.OnDateChangedListener {

    public static final String EXTRA_DATE = "com.bazinga.stock.fragment.EXTRA_DATE";
    private Date mDate;

    public static DatePickerFragment newInstance(Date date){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATE, date);

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);

        return datePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        View dialogLayout =  getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) dialogLayout.findViewById(R.id.dialog_date_picker);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);


        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(dialogLayout) // setting custom view
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // build a Date object using the user choices
        mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();

        // store the new date in the arguments, so it used
        // when recreating the fragment (due to rotation, for example)
        getArguments().putSerializable(EXTRA_DATE, mDate);
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null) {
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
