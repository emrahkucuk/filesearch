package com.emrah.mafaylz.helpers;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.emrah.mafaylz.model.FileSearchResult;
import com.emrah.mafaylz.model.FileSearchResultItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    private static final String FILE_SAVE_DIRECTORY = "/fileSearches";
    private static final String SAVED_FILE_PREFIX = "file_search_";
    private static final String SAVED_FILE_EXTENSION = ".txt";

    private static FileSearchResult result;
    private final FileSearchListener fileSearchListener;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FileSearchHelper(FileSearchListener fileSearchListener) {
        this.fileSearchListener = fileSearchListener;
    }

    public static FileSearchResult getResult(SortType sortType) {
        if (result == null) {
            result = new FileSearchResult(new ArrayList<>(), "");
        }
        Comparator<FileSearchResultItem> comparator = sortType.getSortingComparator();
        List<FileSearchResultItem> resultBeforeSort = new ArrayList<>(result.getFileSearchResultList());
        resultBeforeSort.sort(Objects.requireNonNull(comparator));
        return new FileSearchResult(resultBeforeSort, result.getFileSearchQuery());
    }

    @VisibleForTesting
    protected void setResult(FileSearchResult result) {
        FileSearchHelper.result = result;
        fileSearchListener.onSuccess(result);
    }

    /*
     * I got help from this blog for Rx
     * @link https://blog.danlew.net/2015/07/23/deferring-observable-code-until-subscription-in-rxjava/
     * @link https://github.com/ReactiveX/RxAndroid
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
                                setResult(fileSearchResult);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Search Completed");
                            }
                        })
        );
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
                    subFiles.add(
                            new FileSearchResultItem(
                                    file.getName(),
                                    file.getAbsolutePath(),
                                    file.lastModified(),
                                    file.getPath().substring(file.getPath().lastIndexOf("."))
                            )
                    );
                }
            } else {
                subFiles.addAll(traverseFiles(Objects.requireNonNull(file.listFiles()), query));
            }
        }
        return subFiles;
    }

    public void clearDisposables() {
        disposables.clear();
    }

    /*
     * @link https://stackoverflow.com/questions/7887078/android-saving-file-to-external-storage
     * */
    public void saveSearchResult() {
        disposables.add(
                Observable
                        .defer(() -> Observable.just(doSaveSearchResult()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Boolean>() {

                            @Override
                            public void onNext(@NonNull Boolean isSaveSuccessful) {
                                if (isSaveSuccessful) {
                                    fileSearchListener.onSaveSuccess();
                                } else {
                                    Log.e(TAG, "Save Unsuccessful");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Save Completed");
                            }
                        })
        );
    }

    private boolean doSaveSearchResult() {
        File rootDir = Environment.getExternalStorageDirectory();
        File fileSearchesDirectory = new File(rootDir, FILE_SAVE_DIRECTORY);
        boolean isDirectoryExists = fileSearchesDirectory.exists();
        if (!isDirectoryExists) {
            isDirectoryExists = fileSearchesDirectory.mkdirs();
        }
        if (!isDirectoryExists) {
            Log.e(TAG, "File Search Save Directory does not exists");
            return false;
        }
        long timeStamp = System.currentTimeMillis();
        String newFileName = SAVED_FILE_PREFIX + timeStamp + SAVED_FILE_EXTENSION;
        File newFile = new File(fileSearchesDirectory, newFileName);

        try (FileOutputStream fout = new FileOutputStream(newFile); OutputStreamWriter osw = new OutputStreamWriter(fout)) {
            osw.write(result.getFileSearchQuery() + ":" + result.getFileCount());
            for (FileSearchResultItem item : result.getFileSearchResultList()) {
                osw.write("\n" + item.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
