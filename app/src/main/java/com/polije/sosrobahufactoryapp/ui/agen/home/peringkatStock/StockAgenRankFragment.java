package com.polije.sosrobahufactoryapp.ui.agen.home.peringkatStock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polije.sosrobahufactoryapp.R;

public class StockAgenRankFragment extends Fragment {

    private StockAgenRankViewModel mViewModel;

    public static StockAgenRankFragment newInstance() {
        return new StockAgenRankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock_agen_rank, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StockAgenRankViewModel.class);
        // TODO: Use the ViewModel
    }

}