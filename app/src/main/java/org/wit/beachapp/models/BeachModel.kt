package org.wit.beachapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeachModel(var id: Long = 0,
                      var title: String = "",
                      var description: String = "") : Parcelable