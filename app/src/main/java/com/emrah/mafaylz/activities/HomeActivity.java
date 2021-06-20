package com.emrah.mafaylz.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emrah.mafaylz.adapters.ResultsAdapter;
import com.emrah.mafaylz.databinding.ActivityMainBinding;
import com.emrah.mafaylz.helpers.FileSearchHelper;
import com.emrah.mafaylz.helpers.NotificationHelper;
import com.emrah.mafaylz.helpers.SortType;

public class HomeActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ResultsAdapter adapter;
    FileSearchHelper fileSearchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getWindow().getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        fileSearchHelper = new FileSearchHelper(fileSearchResult -> {
            FileSearchHelper.setResult(FileSearchHelper.getResult(SortType.NONE));
            adapter.setResult(FileSearchHelper.getResult(SortType.NONE));
            NotificationHelper.getInstance().showFileSearchResultNotification(this, fileSearchResult);
        });

        binding.etSearch.setOnEditorActionListener(new OnSearchEditorAction(
                query -> fileSearchHelper.startSearching(query)
        ));

        binding.rvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ResultsAdapter();
        binding.rvResults.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (adapter != null && FileSearchHelper.getResult(SortType.NONE) != null) {
            adapter.setResult(FileSearchHelper.getResult(SortType.NONE));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileSearchHelper != null) {
            fileSearchHelper.clearDisposables();
        }
    }
}