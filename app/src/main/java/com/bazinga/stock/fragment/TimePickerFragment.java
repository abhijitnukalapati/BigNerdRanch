package com.bazinga.stock.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

/**
 * Created by abhijitnukalapati on 5/10/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_TIME_HOUR = "com.bazinga.stock.fragment.EXTRA_TIME_HOUR";
    public static final String EXTRA_TIME_MINUTE = "com.bazinga.stock.fragment.EXTRA_TIME_MINUTE";


    public static TimePickerFragment newInstance(int h, int m){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TIME_HOUR, h);
        bundle.putSerializable(EXTRA_TIME_MINUTE, m);

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);

        return timePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int h = getArguments().getInt(EXTRA_TIME_HOUR);
        int m = getArguments().getInt(EXTRA_TIME_MINUTE);

        return new TimePickerDialog(getActivity(), this, h, m,false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
