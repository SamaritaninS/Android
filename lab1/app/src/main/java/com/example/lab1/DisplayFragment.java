package com.example.lab1;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;


public class DisplayFragment extends Fragment {

    MainViewModel mainViewModel;
    ClipboardManager clipboardManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_display, container, false);
        EditText editInput = layout.findViewById(R.id.input_edit);
        EditText editOutput = layout.findViewById(R.id.output_edit);
        Spinner spinnerInput = layout.findViewById(R.id.input_spinner);
        Spinner spinnerOutput = layout.findViewById(R.id.output_spinner);

        spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainViewModel.setSpinnerInputLiveData(spinnerInput.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerOutput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainViewModel.setSpinnerOutputLiveData(spinnerOutput.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mainViewModel.getInputEditLiveData().observe(requireActivity(), value -> editInput.setText(value));
        mainViewModel.getOutputEditLiveData().observe(requireActivity(), value -> editOutput.setText(value));
        clipboardManager = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        layout.findViewById(R.id.exchange_button).setOnClickListener(item -> mainViewModel.ReplaceValues());
        layout.findViewById(R.id.convert_button).setOnClickListener(item -> mainViewModel.convert());
        return layout;
    }
}