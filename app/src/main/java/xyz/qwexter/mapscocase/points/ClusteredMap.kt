package xyz.qwexter.mapscocase.points

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import xyz.qwexter.mapscocase.points.mappoints.MapPoint
import xyz.qwexter.mapscocase.points.mappoints.MarkerProvider


@Composable
fun ClusteredMap(initialCameraPosition: LatLng, markerProvider: MarkerProvider) {
    val scope = rememberCoroutineScope()
    val markersRemember = remember {
        mutableStateOf(emptyList<MapPoint>())
    }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            markersRemember.value = markerProvider.getMarkerForNearArea(initialCameraPosition)
        }
    }
    Map(markers = markersRemember.value, initialCameraPosition) {
        scope.launch {
//            markersRemember.value = markerProvider.getMarkerForNearArea(it)
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun Map(
    markers: List<MapPoint>,
    initialCameraPosition: LatLng,
    onCameraMoved: (LatLng) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialCameraPosition, 10f)
    }
    GoogleMap(cameraPositionState = cameraPositionState) {
        val context = LocalContext.current
        var clusterManager by remember {
            mutableStateOf<ClusterManager<MapPoint>?>(null)
        }

        MapEffect(markers) { map ->
            if (clusterManager == null) {
                clusterManager = ClusterManager(context, map)
            }
            clusterManager?.clearItems()
            clusterManager?.addItems(markers)
            clusterManager?.cluster()
        }
        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                clusterManager?.onCameraIdle()
                onCameraMoved(cameraPositionState.position.target)
            }
        }
        MarkerInfoWindow()
    }
}