package com.example.navigationndktest

import android.content.Context
import android.util.Log
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.ResourceOptionsManager
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.coordinates
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.RouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation

class NavigationTest {
    fun run(context: Context) {
        try {
            val accessToken = context.resources.getString(R.string.mapbox_access_token)
            var navigation: MapboxNavigation =
                MapboxNavigation(NavigationOptions.Builder(context).accessToken(accessToken).build())

            val waypoints = listOf(
                Point.fromLngLat(-77.0324047, 38.9131752),
                Point.fromLngLat(-77.0365, 38.8977)
            )

            val routeOptions = RouteOptions.builder()
                // .applyDefaultNavigationOptions(DirectionsCriteria.PROFILE_DRIVING)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .coordinatesList(waypoints)
                .coordinates(waypoints.first()!!, waypoints.dropLast(1).drop(1), waypoints.last()!!)
                .build()

            navigation.requestRoutes(routeOptions, object : RouterCallback {
                override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
                    Log.e("RNMBXNDirections", "RRoute cancelled: ${routeOptions}")
                }

                override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
                    Log.e("RNMBXNDirections", "RRoute failure: ${routeOptions}")
                }

                override fun onRoutesReady(
                    routes: List<DirectionsRoute>,
                    routerOrigin: RouterOrigin
                ) {
                    Log.e("RNMBXNDirections", "RRoute ready: ${routes}")
                }
            })
        } catch (e: Exception) {
            Log.e("RNMBXNDirections", "Error: ${e} ${e.stackTrace}")

        }
    }
}