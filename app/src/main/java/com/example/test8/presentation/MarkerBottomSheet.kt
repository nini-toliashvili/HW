package com.example.test8.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.test8.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MarkerBottomSheet : BottomSheetDialogFragment() {
    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_LAT = "arg_lat"
        private const val ARG_LNG = "arg_lng"
        private const val ARG_ADDRESS = "arg_address"

        fun newInstance(title: String, lat: Double, lng: Double, address: String): MarkerBottomSheet {
            val fragment = MarkerBottomSheet()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putDouble(ARG_LAT, lat)
            args.putDouble(ARG_LNG, lng)
            args.putString(ARG_ADDRESS, address)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
        val titleTextView = view.findViewById<TextView>(R.id.title)
        val locationTextView = view.findViewById<TextView>(R.id.marker_location)
        val addressTextView = view.findViewById<TextView>(R.id.marker_address)

        val title = arguments?.getString(ARG_TITLE) ?: "Unknown"
        val lat = arguments?.getDouble(ARG_LAT) ?: 0.0
        val lng = arguments?.getDouble(ARG_LNG) ?: 0.0
        val address = arguments?.getString(ARG_ADDRESS) ?: "No Address"

        titleTextView.text = title
        locationTextView.text = "Lat: $lat, Lng: $lng"
        addressTextView.text = address

        return view
    }
}