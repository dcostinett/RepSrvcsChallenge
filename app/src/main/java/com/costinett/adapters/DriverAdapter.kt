package com.costinett.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.costinett.MainViewModel
import com.costinett.database.daos.Driver
import com.costinett.databinding.ItemDriverBinding

class DriverAdapter(private val viewModel: MainViewModel?) : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {

    var drivers = listOf<Driver>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ItemDriverBinding.inflate(inflator)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    inner class DriverViewHolder(private val view: ItemDriverBinding) : RecyclerView.ViewHolder(view.root) {
        fun onBind(position: Int) {
            val driver = drivers[position]
            view.driverId.text = driver.id
            view.name.text = driver.name
            view.name.setOnClickListener {
                viewModel?.showRoutes(driver)
            }
        }
    }
}