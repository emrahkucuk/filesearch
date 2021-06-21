package com.emrah.mafaylz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emrah.mafaylz.adapters.ResultsAdapter;
import com.emrah.mafaylz.adapters.SortBySpinnerAdapter;
import com.emrah.mafaylz.databinding.ActivityMainBinding;
import com.emrah.mafaylz.helpers.FileSearchHelper;
import com.emrah.mafaylz.helpers.FileSearchListener;
import com.emrah.mafaylz.helpers.NotificationHelper;
import com.emrah.mafaylz.helpers.PermissionHelper;
import com.emrah.mafaylz.helpers.SortType;
import com.emrah.mafaylz.model.FileSearchResult;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, FileSearchListener {

    private final ActivityResultLauncher<String> saveRequestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> onSavePermissionGranted());
    private final ActivityResultLauncher<String> searchRequestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> onSearchPermissionGranted());

    ActivityMainBinding binding;
    ResultsAdapter adapter;
    ArrayAdapter<SortType> sortBySpinnerAdapter;

    FileSearchHelper fileSearchHelper;
    SortType lastSelectedSortType = SortType.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getWindow().getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        fileSearchHelper = new FileSearchHelper(this);

        binding.etSearch.setOnEditorActionListener(new OnSearchEditorAction(
                query -> PermissionHelper.getInstance().executeOrAskStoragePermission(
                        this,
                        this::onSearchPermissionGranted,
                        searchRequestPermissionLauncher
                )
        ));

        binding.rvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ResultsAdapter();
        binding.rvResults.setAdapter(adapter);

        sortBySpinnerAdapter = new SortBySpinnerAdapter(this);
        sortBySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnSortType.setAdapter(sortBySpinnerAdapter);
        binding.spnSortType.setOnItemSelectedListener(this);

        binding.btnSaveResults.setOnClickListener(v -> PermissionHelper.getInstance().executeOrAskStoragePermission(
                this,
                this::onSavePermissionGranted,
                saveRequestPermissionLauncher));
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
        lastSelectedSortType = sortBySpinnerAdapter.getItem(position);
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

    @Override
    public void onSuccess(FileSearchResult fileSearchResult) {
        Objects.requireNonNull(adapter).setResult(FileSearchHelper.getResult(SortType.NONE));
        NotificationHelper.getInstance().showFileSearchResultNotification(this, fileSearchResult);
        int saveResultButtonVisibility = fileSearchResult.getFileSearchResultList().isEmpty() ? View.GONE : View.VISIBLE;
        Objects.requireNonNull(binding.btnSaveResults).setVisibility(saveResultButtonVisibility);
    }

    @Override
    public void onSaveSuccess() {
        Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
    }

    private void onSavePermissionGranted() {
        fileSearchHelper.saveSearchResult();
    }

    private void onSearchPermissionGranted() {
        String query = Objects.requireNonNull(binding.etSearch.getText().toString());
        fileSearchHelper.startSearching(query);
    }
}