package com.example.yummp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class NowPlayingFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mediaButtonRes;

    public NowPlayingFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends Application> T getApplication() {
        return super.getApplication();
    }

    public MutableLiveData<Integer> getMediaButtonRes() {
        if(mediaButtonRes == null) {
            mediaButtonRes = new MutableLiveData<>();
        }
        return mediaButtonRes;
    }

    public void updateState() {
        getMediaButtonRes().postValue(0);
    }
}
