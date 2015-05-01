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

        setListAdapter(new CrimeAdapter(CrimeLab.getInstance(getActivity()).getCrimes()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get item from the adapter
        Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.d(TAG, crime.getTitle());
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(List<Crime> crimeList) {
            super(getActivity(), 0, crimeList); // pass 0 since we are using a custom layout for each row
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            // If we weren't given a view, inflate one
            if (convertView == null) {
                // inflate the layout
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);

                // build the view holder
                viewHolder = new ViewHolder();
                viewHolder.crimeTitle = (TextView) convertView.findViewById(R.id.item_crime_title);
                viewHolder.crimeDate = (TextView) convertView.findViewById(R.id.item_crime_date);
                viewHolder.crimeSolvedCheckBox = (CheckBox) convertView.findViewById(R.id.item_crime_checkbox);
                convertView.setTag(viewHolder);
            } else {
                // avoid using findViewById as the holder is saved
                // as a tag for the view
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Crime crime = getItem(position);

            viewHolder.crimeTitle.setText(crime.getTitle());
            viewHolder.crimeDate.setText(crime.getDate().toString());
            viewHolder.crimeSolvedCheckBox.setChecked(crime.isSolved());

            return convertView;
        }
    }

    // Holds views for each crime list item row
    private static class ViewHolder {
        TextView crimeTitle;
        TextView crimeDate;
        CheckBox crimeSolvedCheckBox;
    }
}
