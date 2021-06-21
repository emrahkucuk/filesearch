package com.emrah.mafaylz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emrah.mafaylz.R;
import com.emrah.mafaylz.adapters.ResultsAdapter;
import com.emrah.mafaylz.databinding.ActivityMainBinding;
import com.emrah.mafaylz.helpers.FileSearchHelper;
import com.emrah.mafaylz.helpers.NotificationHelper;
import com.emrah.mafaylz.helpers.SortType;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityMainBinding binding;
    ResultsAdapter adapter;
    FileSearchHelper fileSearchHelper;
    SortType lastSelectedSortType = SortType.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getWindow().getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        fileSearchHelper = new FileSearchHelper(fileSearchResult -> {
            FileSearchHelper.setResult(fileSearchResult);
            Objects.requireNonNull(adapter).setResult(FileSearchHelper.getResult(SortType.NONE));
            NotificationHelper.getInstance().showFileSearchResultNotification(this, fileSearchResult);
        });

        binding.etSearch.setOnEditorActionListener(new OnSearchEditorAction(
                query -> fileSearchHelper.startSearching(query)
        ));

        binding.rvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ResultsAdapter();
        binding.rvResults.setAdapter(adapter);

        ArrayAdapter<CharSequence> sortBySpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item);
        sortBySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnSortType.setAdapter(sortBySpinnerAdapter);
        binding.spnSortType.setOnItemSelectedListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (adapter != null) {
            FileSearchHelper.getResult(SortType.NONE);
            adapter.setResult(FileSearchHelper.getResult(SortType.NONE));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ResultsAdapter resultsAdapter = Objects.requireNonNull(adapter);
        lastSelectedSortType = SortType.getSortType(position);
        resultsAdapter.setResult(FileSearchHelper.getResult(lastSelectedSortType));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ResultsAdapter resultsAdapter = Objects.requireNonNull(adapter);
        lastSelectedSortType = SortType.NONE;
        resultsAdapter.setResult(FileSearchHelper.getResult(lastSelectedSortType));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileSearchHelper != null) {
            fileSearchHelper.clearDisposables();
        }
    }
}