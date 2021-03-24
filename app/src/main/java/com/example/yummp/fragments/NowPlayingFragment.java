package com.example.yummp.fragments;

import android.content.Intent;
import android.media.session.MediaController;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummp.MediaPlayerActivity;
import com.example.yummp.R;
import com.example.yummp.databinding.FragmentNowPlayingBinding;
import com.example.yummp.viewmodels.NowPlayingFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {
    private static final String TAG = "NowPlayingFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNowPlayingBinding binding;
    private NowPlayingFragmentViewModel nowPlayingViewModel;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nowPlayingViewModel = new ViewModelProvider(requireActivity())
                .get(NowPlayingFragmentViewModel.class);

        binding.mediaButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick() called with: v = [" + v + "]");
            MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(requireActivity());
            int pbState = mediaController.getPlaybackState().getState();
            MediaControllerCompat.TransportControls transportControls = mediaController.getTransportControls();
            if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                transportControls.pause();
            } else {
                transportControls.play();
            }
        });

        nowPlayingViewModel.getMediaButtonRes().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.mediaButton.setImageResource(integer.intValue());
            }
        });
    }

    public void updateState() {
        nowPlayingViewModel.getMediaButtonRes().postValue(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}