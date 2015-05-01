package com.bazinga.stock.fragment;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bazinga.stock.R;
import com.bazinga.stock.model.Crime;
import com.bazinga.stock.model.CrimeLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijitnukalapati on 4/29/15.
 */
public class CrimeListFragment extends ListFragment {

    private static final String TAG = "CrimeListFragment";
    private ArrayList<Crime> mCrimes;

    public static CrimeListFragment newInstance() {
        CrimeListFragment crimeListFragment = new CrimeListFragment();
        return crimeListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);

        // create the list adapter with the view being
        // a simple text view;
        // the array adapter invokes each crime object's toString()
        // to display text
        ArrayAdapter<Crime> crimeArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, CrimeLab.getInstance(getActivity()).getCrimes());

        setListAdapter(crimeArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get item from the adapter
        Crime crime = (Crime) getListAdapter().getItem(position);
        Log.d(TAG, crime.getTitle());
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(List<Crime> crimeList) {
            super(getActivity(), 0, crimeList); // pass 0 since we are using a custom layout for each row
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }


            Crime crime = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.item_crime_title);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.item_crime_date);
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.item_crime_checkbox);

            titleTextView.setText(crime.getTitle());
            dateTextView.setText(crime.getDate().toString());
            solvedCheckBox.setChecked(crime.isSolved());

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView crimeTitle;
        TextView crimeDate;
        CheckBox crimeSolvedCheckBox;
    }
}
