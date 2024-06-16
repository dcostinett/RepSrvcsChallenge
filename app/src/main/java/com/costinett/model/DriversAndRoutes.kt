// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.costinett.model

import com.costinett.database.daos.Driver
import com.costinett.database.daos.Route

data class DriversAndRoutes (
    val drivers: List<Driver>,
    val routes: List<Route>
)

enum class Type {
    C,
    I,
    R
}
