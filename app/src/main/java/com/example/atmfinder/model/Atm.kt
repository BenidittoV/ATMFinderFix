package com.example.atmfinder.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Atm : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var photoUrl: String = ""
    var bankType: String = ""
    var locationName: String = ""
    var address: String = ""
    var moneyStatus: String = "" // Full, AlmostEmpty, Empty
    var trafficStatus: String = "" // Quiet, Moderate, Busy
    var providerId: String = ""
}