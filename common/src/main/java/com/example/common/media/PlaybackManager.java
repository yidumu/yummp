package com.example.common.media;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.media.session.MediaSessionCompat;

import java.io.IOException;

public class PlaybackManager {

    private MediaSessionCallback mMediaSessionCallback;

    public PlaybackManager() {
        mMediaSessionCallback = new MediaSessionCallback();
    }

    public MediaSessionCompat.Callback getMediaSessionCallback() {
        return mMediaSessionCallback;
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            //TODO: 抽象出一层播放器，适配不同的播放器的不同操作需求

        }
    }

}
