package com.example.yummp;

import android.content.ComponentName;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.media.MediaPlaybackService;

public class MediaPlayerActivity extends AppCompatActivity {
    private static final String TAG = "MediaPlayerActivity";

    private MediaBrowserCompat mediaBrowser;

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mediaBrowser.getSessionToken();

                    // Create a MediaControllerCompat
                    MediaControllerCompat mediaController =
                            new MediaControllerCompat(MediaPlayerActivity.this, // Context
                                    token);

                    // Save the controller
                    MediaControllerCompat.setMediaController(MediaPlayerActivity.this, mediaController);

                    // Finish building the UI
                    buildTransportControls();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);

        // ...
        // Create MediaBrowserServiceCompat
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, MediaPlaybackService.class),
                connectionCallbacks,
                null); // optional Bundle
    }

    void buildTransportControls() {
        // Grab the view for the play/pause button
        Button playPause = findViewById(R.id.btn_music_play);

        // Attach a listener to the button
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
                // Since this is a play/pause button, you'll need to test the current state
                // and choose the action accordingly

                int pbState = MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getPlaybackState().getState();
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
                } else {
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().play();
                }
            }
        });

        //activity save
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(MediaPlayerActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
        }
        mediaBrowser.disconnect();
    }
}
