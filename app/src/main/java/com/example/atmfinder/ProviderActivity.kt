package com.example.atmfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atmfinder.databinding.ActivityProviderBinding
import com.example.atmfinder.model.Atm

class ProviderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProviderBinding
    private val dbHelper = MongoDBHelper(this)
    private lateinit var adapter: ProviderAtmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val providerId = dbHelper.getAllAtms().firstOrNull()?.providerId ?: ""
        adapter = ProviderAtmAdapter(dbHelper.getAtmsByProvider(providerId)) { atm, moneyStatus, trafficStatus ->
            dbHelper.updateAtmStatus(atm.id, moneyStatus, trafficStatus)
            adapter.updateData(dbHelper.getAtmsByProvider(providerId))
        }
        binding.rvProviderAtms.layoutManager = LinearLayoutManager(this)
        binding.rvProviderAtms.adapter = adapter
    }
}

class ProviderAtmAdapter(
    private var atms: List<Atm>,
    private val onUpdate: (Atm, String, String) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<ProviderAtmAdapter.ProviderViewHolder>() {

    class ProviderViewHolder(val binding: AtmListItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = AtmListItemBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return ProviderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val atm = atms[position]
        with(holder.binding) {
            tvBank.text = atm.bankType
            tvLocation.text = atm.locationName
            tvMoneyStatus.text = atm.moneyStatus
            tvTrafficStatus.text = atm.trafficStatus
            root.setOnClickListener {
                // Simulate update (in real app, use dialog)
                val newMoneyStatus = when (atm.moneyStatus) {
                    "Full" -> "AlmostEmpty"
                    "AlmostEmpty" -> "Empty"
                    else -> "Full"
                }
                val newTrafficStatus = when (atm.trafficStatus) {
                    "Quiet" -> "Moderate"
                    "Moderate" -> "Busy"
                    else -> "Quiet"
                }
                onUpdate(atm, newMoneyStatus, newTrafficStatus)
            }
        }
    }

    override fun getItemCount(): Int = atms.size

    fun updateData(newAtms: List<Atm>) {
        atms = newAtms
        notifyDataSetChanged()
    }
}