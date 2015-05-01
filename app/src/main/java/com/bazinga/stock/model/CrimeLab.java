package com.bazinga.stock.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by abhijitnukalapati on 4/29/15.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private ArrayList<Crime> mCrimes;
    private Context mContext;

    private CrimeLab(Context context){
        // get global context as we cannot be sure that the current context will
        // be available
        mContext = context.getApplicationContext();
        mCrimes = new ArrayList<>();

        // populate list with dummy data
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        }
    }

    public static CrimeLab getInstance(Context c){
        if(sCrimeLab == null){
           sCrimeLab = new CrimeLab(c);
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
}
