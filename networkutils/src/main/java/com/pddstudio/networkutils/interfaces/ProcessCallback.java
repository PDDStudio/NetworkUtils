package com.pddstudio.networkutils.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * This Class was created by Patrick J
 * on 12.02.16. For more Details and Licensing
 * have a look at the README.md
 */
public interface ProcessCallback {
    void onProcessStarted(@NonNull final String serviceName);
    void onProcessFailed(@NonNull final String serviceName, @Nullable String errorMessage, int errorCode);
    void onProcessFinished(@NonNull final String serviceName, @Nullable String endMessage);
    void onProcessUpdate(@NonNull Object processUpdate);
}
