package com.example.atmfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atmfinder.R
import com.example.atmfinder.model.Atm

class ATMAdapter(private var atmList: List<Atm>) : RecyclerView.Adapter<ATMAdapter.ATMViewHolder>() {

    class ATMViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBank: TextView = itemView.findViewById(R.id.tvBank)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvMoneyStatus: TextView = itemView.findViewById(R.id.tvMoneyStatus)
        val tvTrafficStatus: TextView = itemView.findViewById(R.id.tvTrafficStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ATMViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.id.item_atm, parent, false)
        return ATMViewHolder(view)
    }

    override fun onBindViewHolder(holder: ATMViewHolder, position: Int) {
        val atm = atmList[position]
        holder.tvBank.text = atm.bank
        holder.tvLocation.text = atm.locationName
        holder.tvAddress.text = atm.address
        holder.tvMoneyStatus.text = "Status Uang: ${atm.moneyStatus}"
        holder.tvTrafficStatus.text = "Status Traffic: ${atm.trafficStatus}"
    }

    override fun getItemCount(): Int = atmList.size

    fun updateData(newList: List<Atm>) {
        atmList = newList
        notifyDataSetChanged()
    }
}