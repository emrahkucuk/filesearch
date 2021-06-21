package com.emrah.mafaylz.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class PermissionHelper {
    private static PermissionHelper permissionHelperInstance;
    private static final String PERMISSION_RATIONALE = "You need to give storage access";

    private PermissionHelper() {
    }

    public static PermissionHelper getInstance() {
        if (permissionHelperInstance == null) {
            permissionHelperInstance = new PermissionHelper();
        }
        return permissionHelperInstance;
    }

    public void executeOrAskStoragePermission(
            Activity activity,
            PermissionGrantedListener listener,
            ActivityResultLauncher<String> requestPermissionLauncher) {
        if (isStoragePermissionGranted(activity)) {
            listener.onPermissionGranted();
        } else if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, PERMISSION_RATIONALE, Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private boolean isStoragePermissionGranted(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }
}
