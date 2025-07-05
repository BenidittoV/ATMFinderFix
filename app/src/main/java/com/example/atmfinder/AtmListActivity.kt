package com.example.atmfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atmfinder.databinding.ActivityAtmListBinding
import com.example.atmfinder.model.Atm

class AtmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmListBinding
    private lateinit var adapter: AtmAdapter
    private val dbHelper = MongoDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFilters()
    }

    private fun setupRecyclerView() {
        adapter = AtmAdapter(dbHelper.getAllAtms()) { atm ->
            val intent = Intent(this, AtmDetailActivity::class.java)
            intent.putExtra("ATM_ID", atm.id)
            startActivity(intent)
        }
        binding.rvAtms.layoutManager = LinearLayoutManager(this)
        binding.rvAtms.adapter = adapter
    }

    private fun setupFilters() {
        binding.btnFilter.setOnClickListener {
            val bank = binding.etBankFilter.text.toString()
            val money = binding.etMoneyFilter.text.toString()
            val traffic = binding.etTrafficFilter.text.toString()

            val filteredAtms = dbHelper.getAllAtms().filter { atm ->
                (bank.isEmpty() || atm.bankType.contains(bank, true)) &&
                        (money.isEmpty() || atm.moneyStatus.contains(money, true)) &&
                        (traffic.isEmpty() || atm.trafficStatus.contains(traffic, true))
            }
            adapter.updateData(filteredAtms)
        }
    }
}

class AtmAdapter(
    private var atms: List<Atm>,
    private val onClick: (Atm) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<AtmAdapter.AtmViewHolder>() {

    class AtmViewHolder(val binding: AtmListItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): AtmViewHolder {
        val binding = AtmListItemBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return AtmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AtmViewHolder, position: Int) {
        val atm = atms[position]
        with(holder.binding) {
            tvBank.text = atm.bankType
            tvLocation.text = atm.locationName
            tvMoneyStatus.text = atm.moneyStatus
            tvTrafficStatus.text = atm.trafficStatus
            root.setOnClickListener { onClick(atm) }
        }
    }

    override fun getItemCount(): Int = atms.size

    fun updateData(newAtms: List<Atm>) {
        atms = newAtms
        notifyDataSetChanged()
    }
}