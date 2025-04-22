package com.polije.sosrobahufactoryapp.ui.agen.order.pilihProdukAgen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polije.sosrobahufactoryapp.R;

public class PilihProdukAgenFragment extends Fragment {

    private PilihProdukAgenViewModel mViewModel;

    public static PilihProdukAgenFragment newInstance() {
        return new PilihProdukAgenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pilih_produk_agen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PilihProdukAgenViewModel.class);
        // TODO: Use the ViewModel
    }

}