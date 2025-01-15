package com.example.homework15

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.homework15.databinding.BottomsheetDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(private val position:Int) : BottomSheetDialogFragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private var listener: OnItemClickListener? = null
    private var _binding :BottomsheetDialogFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? OnItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.no.setOnClickListener {
             dismiss()
        }
        binding.yes.setOnClickListener {

            viewModel.removeCard(position)

            dismiss()


        }
    }

}