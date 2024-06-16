package com.costinett.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.costinett.database.daos.Route
import com.costinett.databinding.ItemRouteBinding

class RouteAdapter : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    var routes = listOf<Route>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = ItemRouteBinding.inflate(inflator)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return routes.size
    }

    inner class RouteViewHolder(private val view: ItemRouteBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind(position: Int) {
            val driver = routes[position]
            view.name.text = driver.name
        }
    }

}