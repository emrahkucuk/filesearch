package com.emrah.mafaylz.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.emrah.mafaylz.helpers.SortType;

public class SortBySpinnerAdapter extends ArrayAdapter<SortType> {

    public SortBySpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_item, SortType.values());
    }

}
