package com.example.common.media;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import com.example.common.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPlaybackService extends MediaBrowserServiceCompat {
    private static final String LOG_TAG = "MediaPlaybackService";
    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaPlayer mediaPlayer;

    private final MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            Log.d(LOG_TAG, "onPlay: ");
            //TODO:交由客户端处理播放内容
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.jam);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mediaPlayer.setDataSource(fd);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a MediaSessionCompat
        mediaSession = new MediaSessionCompat(this, "MediaPlaybackService");

        // Enable callbacks from MediaButtons and TransportControls
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        // MySessionCallback() has methods that handle callbacks from a media controller
        mediaSession.setCallback(mediaSessionCallback);

        // Set the session's token so that client activities can communicate with it.
        setSessionToken(mediaSession.getSessionToken());
    }

    //控制访问
    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        //if (allowBrowsing(clientPackageName, clientUid)) {
        // Returns a root ID that clients can use with onLoadChildren() to retrieve
        // the content hierarchy.
        return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        //} else {
        // Clients can connect, but this BrowserRoot is an empty hierachy
        // so onLoadChildren returns nothing. This disables the ability to browse for content.
        //return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
        //}
        //return null;
    }

    //


    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaItem>> result) {

        //  Browsing not allowed
        /*if (TextUtils.equals(MY_EMPTY_MEDIA_ROOT_ID, parentMediaId)) {
            result.sendResult(null);
            return;
        }*/

        // Assume for example that the music catalog is already loaded/cached.

        List<MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu:
        //if (MY_MEDIA_ROOT_ID.equals(parentMediaId)) {
        // Build the MediaItem objects for the top level,
        // and put them in the mediaItems list...
        //} else {
        // Examine the passed parentMediaId to see which submenu we're at,
        // and put the children of that menu in the mediaItems list...
        //}
        result.sendResult(mediaItems);
    }
}

