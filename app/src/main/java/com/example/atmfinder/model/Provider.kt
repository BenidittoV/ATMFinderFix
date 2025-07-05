package com.example.atmfinder.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Provider : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var atmIds: String = "" // Comma-separated ATM IDs
}