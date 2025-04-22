package com.polije.sosrobahufactoryapp.ui.agen.pesanan.detailPesanan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polije.sosrobahufactoryapp.R;

public class DetailPesananAgenFragment extends Fragment {

    private DetailPesananAgenViewModel mViewModel;

    public static DetailPesananAgenFragment newInstance() {
        return new DetailPesananAgenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_pesanan_agen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailPesananAgenViewModel.class);
        // TODO: Use the ViewModel
    }

}