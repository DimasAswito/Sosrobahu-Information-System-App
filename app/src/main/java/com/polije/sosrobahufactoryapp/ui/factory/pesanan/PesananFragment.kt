package com.polije.sosrobahufactoryapp.ui.factory.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.polije.sosrobahufactoryapp.databinding.FragmentPesananBinding

class PesananFragment : Fragment() {

    private var _binding: FragmentPesananBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pesananViewModel =
            ViewModelProvider(this).get(PesananViewModel::class.java)

        _binding = FragmentPesananBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPesanan
        pesananViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}