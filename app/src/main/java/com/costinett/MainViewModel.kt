package com.costinett

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.room.Room
import com.costinett.database.Database
import com.costinett.database.daos.Driver
import com.costinett.database.daos.Route
import com.costinett.model.Type
import com.costinett.network.ApiClient
import com.costinett.network.DriverInterface
import com.costinett.network.NetworkStateResponse
import com.costinett.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    private val mutableDrivers = MutableLiveData<List<Driver>>()
    val driverList : LiveData<List<Driver>> = mutableDrivers

    private val mutableRoutes = MutableLiveData<List<Route>>()
    val routeList : LiveData<List<Route>> = mutableRoutes

    private val mutableNavigationEvent = MutableLiveData<Fragment>()
    val navigationEvent : LiveData<Fragment> = mutableNavigationEvent

    private lateinit var db: Database

    init {
        db = Room.databaseBuilder(application, Database::class.java, "DriverDataDatabase")
            .build()
    }

    fun fetchAndStoreDriverAndRouteData() {
        val driverDataService = ApiClient.getClient().create(DriverInterface::class.java)

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = safeApiCall ( call = { driverDataService.getDriverData() } ) ) {
                is NetworkStateResponse.Success -> {
                    response.data?.let {
                        mutableDrivers.postValue(it.drivers)
                        mutableRoutes.postValue(it.routes)

                        db.driverDao().addAll(it.drivers)
                        db.routeDao().addAll(it.routes)                    }
                }
                is NetworkStateResponse.Error -> {
                    Log.e("ViewModel", "" + response.exception.message)
                }

                else -> {}
            }
        }
    }

    fun sortByLastName() {
        viewModelScope.launch(Dispatchers.IO) {
            val sorted = db.driverDao().getAllDrivers().sortedBy {
                it.name.split(" ")[1]
            }
            mutableDrivers.postValue(sorted)
        }
    }

    fun showRoutes(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            val routes = db.routeDao().getAllRoutes()

            var route = routes.firstOrNull { it.id == driver.id.toLong() }
            if (route != null) {
                mutableRoutes.postValue(listOf(route))
                mutableNavigationEvent.postValue(RouteFragment())
                return@launch
            }
            when {
                (driver.id.toInt() % 2 == 0) -> {
                    route = routes.first { it.type == Type.R }
                    mutableRoutes.postValue(listOf(route))
                }
                driver.id.toInt() % 5 == 0 -> {
                    route = routes.filter { it.type == Type.C }.take(2).last()
                    mutableRoutes.postValue(listOf(route))
                }
                else -> {
                    route = routes.last { it.type == Type.I }
                    mutableRoutes.postValue(listOf(route))
                }
            }

            mutableNavigationEvent.postValue(RouteFragment())
        }
    }
}