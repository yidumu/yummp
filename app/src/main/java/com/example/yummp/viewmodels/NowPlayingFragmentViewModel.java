package com.example.yummp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NowPlayingFragmentViewModel extends AndroidViewModel {

    public NowPlayingFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }
}
