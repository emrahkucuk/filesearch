package com.emrah.mafaylz.activities;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class OnSearchEditorAction implements TextView.OnEditorActionListener {

    final OnSearchEditorActionListener listener;

    public OnSearchEditorAction(OnSearchEditorActionListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            listener.search(v.getText().toString());
            return true;
        } else {
            return false;
        }
    }
}
