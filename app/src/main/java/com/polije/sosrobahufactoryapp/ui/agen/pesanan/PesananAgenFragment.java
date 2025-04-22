package com.polije.sosrobahufactoryapp.ui.agen.pesanan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polije.sosrobahufactoryapp.R;

public class PesananAgenFragment extends Fragment {

    private PesananAgenViewModel mViewModel;

    public static PesananAgenFragment newInstance() {
        return new PesananAgenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pesanan_agen2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PesananAgenViewModel.class);
        // TODO: Use the ViewModel
    }

}