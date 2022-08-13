package com.farminikagroup.farminika.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.SectorViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sector_layout.*

class SectorBottomSheet : BottomSheetDialogFragment() {

    private lateinit var sectorViewModel: SectorViewModel
    private lateinit var sector: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sectorViewModel = ViewModelProvider(requireActivity())[SectorViewModel::class.java]
        fishFarming.setOnClickListener {
            sector = fishFarming.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }
        horticulture.setOnClickListener {
            sector = horticulture.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }
        beeKeeping.setOnClickListener {
            sector = beeKeeping.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }
        teaCoffee.setOnClickListener {
            sector = teaCoffee.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }
        grainFarming.setOnClickListener {
            sector = grainFarming.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }
        fertilizers.setOnClickListener {
            sector = fertilizers.text.toString()
            sectorViewModel.sector.value = sector
            dismiss()
            findNavController().navigate(R.id.action_sectorBottomSheet_to_expertFragment)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sector_layout, container, false)
    }
}