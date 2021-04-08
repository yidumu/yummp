package com.example.common.common;

import android.content.ComponentName;
import android.content.Context;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

public class MusicServiceConnection {
    private static final String TAG = "MusicServiceConnection";

    private Context context;
    private ComponentName componentName;

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mediaBrowser.getSessionToken();

                    // Create a MediaControllerCompat
                    MediaControllerCompat mediaController = new MediaControllerCompat(context, token);

                    // Register a Callback to stay in sync
                    mediaController.registerCallback(controllerCallback);
                }

                @Override
                public void onConnectionSuspended() {
                    // The Service has crashed. Disable transport controls until it automatically reconnects
                }

                @Override
                public void onConnectionFailed() {
                    // The Service has refused our connection
                }
            };

    private final MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.d(TAG, "onMetadataChanged() called with: metadata = [" + metadata + "]");
                    //TODO:Update UI
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.d(TAG, "onPlaybackStateChanged() called with: state = [" + state + "]");
                    //TODO:Update UI
                }
            };

    private MediaBrowserCompat mediaBrowser =
            new MediaBrowserCompat(context, componentName, connectionCallbacks, null); // optional Bundle ;

    public MusicServiceConnection(Context context, ComponentName componentName) {
        this.context = context;
        this.componentName = componentName;
    }
}
