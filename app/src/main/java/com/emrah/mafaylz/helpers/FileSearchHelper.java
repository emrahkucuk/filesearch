package com.emrah.mafaylz.helpers;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.emrah.mafaylz.model.FileSearchResult;
import com.emrah.mafaylz.model.FileSearchResultItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FileSearchHelper {

    private static final String TAG = "FileSearchHelper";

    private static FileSearchResult result;
    private final FileSearchListener fileSearchListener;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FileSearchHelper(FileSearchListener fileSearchListener) {
        this.fileSearchListener = fileSearchListener;
    }

    public static FileSearchResult getResult(SortType sortType) {
        Comparator<FileSearchResultItem> comparator = null;
        if (sortType == SortType.ALPHABETICAL){
            comparator = new AlphabeticalComparator();
        }
        result.getFileSearchResultList().sort(comparator);
        return result;
    }

    public static void setResult(FileSearchResult result) {
        FileSearchHelper.result = result;
    }

    /*
     * I got help from this blog for Rx
     * @link https://blog.danlew.net/2015/07/23/deferring-observable-code-until-subscription-in-rxjava/
     * */
    public void startSearching(String fileSearchQuery) {
        disposables.add(
                Observable
                        .defer(() -> Observable.just(searchFiles(fileSearchQuery)))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<FileSearchResult>() {

                            @Override
                            public void onNext(@NonNull FileSearchResult fileSearchResult) {
                                fileSearchListener.onSuccess(fileSearchResult);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                            }

                            @Override
                            public void onComplete() {
                                Log.e(TAG, "Search Completed");
                            }
                        })
        );
    }

    public void clearDisposables() {
        disposables.clear();
    }

    private FileSearchResult searchFiles(String query) {
        File[] filesInRoot = Environment.getExternalStorageDirectory().listFiles();
        if (filesInRoot != null) {
            return new FileSearchResult(traverseFiles(filesInRoot, query), query);
        } else {
            return new FileSearchResult(new ArrayList<>(), query);
        }
    }

    private List<FileSearchResultItem> traverseFiles(File[] listFiles, String query) {
        ArrayList<FileSearchResultItem> subFiles = new ArrayList<>();
        for (File file : listFiles) {
            if (file.isFile()) {
                if (file.getName().toLowerCase().contains(query.toLowerCase())) {
                    subFiles.add(new FileSearchResultItem(file.getName(), file.getAbsolutePath()));
                }
            } else {
                subFiles.addAll(traverseFiles(Objects.requireNonNull(file.listFiles()), query));
            }
        }
        SystemClock.sleep(10);
        return subFiles;
    }
}
