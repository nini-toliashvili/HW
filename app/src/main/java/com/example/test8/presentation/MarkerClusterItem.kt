package com.example.test8.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MarkerClusterItem(
    val lat: Double,
    val lan: Double,
    val titleOfStreet: String,
    val address: String
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(lat, lan)
    }

    override fun getTitle(): String {
        return titleOfStreet
    }

    override fun getSnippet(): String {
        return address
    }

}
