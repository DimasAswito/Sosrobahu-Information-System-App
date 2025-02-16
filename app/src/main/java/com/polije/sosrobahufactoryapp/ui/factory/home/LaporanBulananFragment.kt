package com.polije.sosrobahufactoryapp.ui.factory.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.polije.sosrobahufactoryapp.MainActivity
import com.polije.sosrobahufactoryapp.R

class LaporanBulananFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk LaporanBulananFragment
        return inflater.inflate(R.layout.fragment_laporan_bulanan, container, false)
    }

    override fun onResume() {
        super.onResume()
        // Sembunyikan BottomNavigationView di Activity
        (activity as? MainActivity)?.hideBottomNav()
    }

    override fun onPause() {
        super.onPause()
        // Tampilkan kembali BottomNavigationView saat fragment ini tidak aktif
        (activity as? MainActivity)?.showBottomNav()
    }
}
