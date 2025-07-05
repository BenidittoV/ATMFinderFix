package com.example.atmfinder

import android.content.Context
import com.example.atmfinder.model.Atm
import com.example.atmfinder.model.Provider
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import java.util.UUID

class MongoDBHelper(context: Context) {
    private val app: App

    init {
        Realm.init(context)
        app = App(AppConfiguration.Builder("atmfinderapp-").build()) // Jan lupaa harus update link lgi klo server mongodb atlas dan bener
        val credentials = Credentials.anonymous()
        app.loginAsync(credentials) { }
        insertDummyData()
    }

    private fun insertDummyData() {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                // Clear existing data
                it.deleteAll()

                // Insert Providers
                val provider1 = Provider().apply {
                    id = UUID.randomUUID().toString()
                    name = "Indomart Provider"
                    atmIds = ""
                }
                it.insert(provider1)

                // Insert ATMs
                val atm1 = Atm().apply {
                    id = UUID.randomUUID().toString()
                    photoUrl = "https://example.com/atm1.jpg"
                    bankType = "BCA"
                    locationName = "Indomart Sudirman"
                    address = "Jl. Sudirman No. 10"
                    moneyStatus = "Full"
                    trafficStatus = "Quiet"
                    providerId = provider1.id
                }
                val atm2 = Atm().apply {
                    id = UUID.randomUUID().toString()
                    photoUrl = "https://example.com/atm2.jpg"
                    bankType = "Mandiri"
                    locationName = "Indomart Thamrin"
                    address = "Jl. Thamrin No. 15"
                    moneyStatus = "AlmostEmpty"
                    trafficStatus = "Moderate"
                    providerId = provider1.id
                }
                it.insert(atm1)
                it.insert(atm2)

                // Update provider's atmIds
                provider1.atmIds = "${atm1.id},${atm2.id}"
            }
        }
    }

    fun getAllAtms(): List<Atm> {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(Atm::class.java).findAll().toList()
        }
    }

    fun getAtmById(id: String): Atm? {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(Atm::class.java).equalTo("id", id).findFirst()
        }
    }

    fun getAtmsByProvider(providerId: String): List<Atm> {
        return Realm.getDefaultInstance().use { realm ->
            realm.where(Atm::class.java).equalTo("providerId", providerId).findAll().toList()
        }
    }

    fun updateAtmStatus(atmId: String, moneyStatus: String, trafficStatus: String) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                val atm = it.where(Atm::class.java).equalTo("id", atmId).findFirst()
                atm?.moneyStatus = moneyStatus
                atm?.trafficStatus = trafficStatus
            }
        }
    }
}