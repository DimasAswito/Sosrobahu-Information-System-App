package com.polije.sosrobahufactoryapp.ui.agen.order.detailOrder;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polije.sosrobahufactoryapp.R;

public class DetailOrderAgenFragment extends Fragment {

    private DetailOrderAgenViewModel mViewModel;

    public static DetailOrderAgenFragment newInstance() {
        return new DetailOrderAgenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_order_agen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailOrderAgenViewModel.class);
        // TODO: Use the ViewModel
    }

}