package com.example.lab1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class KeyBoardFragment extends Fragment {

    MainViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_key_board, container, false);
        layout.findViewById(R.id.zero_button).setOnClickListener(item -> mainViewModel.addValue("0"));
        layout.findViewById(R.id.one_button).setOnClickListener(item -> mainViewModel.addValue("1"));
        layout.findViewById(R.id.two_button).setOnClickListener(item -> mainViewModel.addValue("2"));
        layout.findViewById(R.id.three_button).setOnClickListener(item -> mainViewModel.addValue("3"));
        layout.findViewById(R.id.four_button).setOnClickListener(item -> mainViewModel.addValue("4"));
        layout.findViewById(R.id.five_button).setOnClickListener(item -> mainViewModel.addValue("5"));
        layout.findViewById(R.id.six_button).setOnClickListener(item -> mainViewModel.addValue("6"));
        layout.findViewById(R.id.seven_button).setOnClickListener(item -> mainViewModel.addValue("7"));
        layout.findViewById(R.id.eight_button).setOnClickListener(item -> mainViewModel.addValue("8"));
        layout.findViewById(R.id.nine_button).setOnClickListener(item -> mainViewModel.addValue("9"));
        layout.findViewById(R.id.point_button).setOnClickListener(item -> mainViewModel.addPoint());
        layout.findViewById(R.id.clear_button).setOnClickListener(item -> mainViewModel.ClearData());
        return layout;
    }
}