package com.example.common.library;

import android.net.Uri;
import android.support.v4.media.MediaMetadataCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class JsonSource {
    private static final String TAG = "JsonSource";
    private Uri source;

    public JsonSource(Uri source) {
        this.source = source;
    }

    public List<MediaMetadataCompat> updateCatalog(Uri uri) throws IOException {
        return null;
    }

    public List<JsonMusic> downloadJson(Uri uri) throws IOException {
        URL url = new URL(uri.toString());
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        Gson gson = new Gson();
        Type listType = new TypeToken<List<JsonMusic>>() {
        }.getType();
        return gson.fromJson(bufferedReader, listType);
    }

}
