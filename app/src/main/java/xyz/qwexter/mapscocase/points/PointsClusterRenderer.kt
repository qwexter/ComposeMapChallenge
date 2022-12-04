package xyz.qwexter.mapscocase.points

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.SparseArray
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import xyz.qwexter.mapscocase.R

class PointsClusterRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<ClusterItem>,
    private val buckets: Buckets = Buckets.default()
) : DefaultClusterRenderer<ClusterItem>(
    context,
    map,
    clusterManager
) {

    private val iconGenerator = IconGenerator(context.applicationContext)
    private val clusterIcons = SparseArray<BitmapDescriptor>()

    override fun getDescriptorForCluster(cluster: Cluster<ClusterItem>): BitmapDescriptor {

        val bucket = getBucket(cluster)
        var descriptor = clusterIcons[bucket]

        if (descriptor == null) {
            val bucketScale = findBucket(cluster)!!.scaleFactor

            val bmp = AppCompatResources.getDrawable(
                context,
                R.drawable.ic_baseline_wifi_24
            )!!.apply {
                colorFilter = ColorFilter.tint(Color(getColor(cluster.size))).asAndroidColorFilter()
            }
                .toBitmap()
            val bmpScaled =
                Bitmap.createScaledBitmap(
                    bmp,
                    (bmp.width * bucketScale).toInt(),
                    (bmp.height * bucketScale).toInt(),
                    false
                )
            val wifi = BitmapDrawable(context.resources, bmpScaled)

            iconGenerator.setBackground(wifi)
            descriptor = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())
            clusterIcons.put(bucket, descriptor)
        }
        return descriptor
    }

    override fun getClusterText(bucket: Int): String {
        return if (bucket < buckets.ranges[0].range) {
            bucket.toString()
        } else {
            "$bucket+"
        }
    }

    override fun getBucket(cluster: Cluster<ClusterItem>): Int {
        val bucket = findBucket(cluster) ?: return cluster.size
        return bucket.range
    }

    private fun findBucket(cluster: Cluster<ClusterItem>): Bucket? {
        val size = cluster.size
        if (size <= buckets.ranges[0].range) {
            return null
        }
        for (i in 0 until buckets.ranges.size - 1) {
            if (size < buckets.ranges[i + 1].range) {
                return buckets.ranges[i]
            }
        }
        return buckets.ranges[buckets.ranges.size - 1]
    }

    class Buckets private constructor(val ranges: List<Bucket>) {
        companion object {
            fun buildFrom(ranges: List<Bucket>): Buckets =
                Buckets(ranges.distinct().sortedBy { it.range })

            fun default(): Buckets = buildFrom(
                listOf(
                    Bucket(2, 1f),
                    Bucket(50, 1.5f),
                    Bucket(200, 2f),
                    Bucket(300, 2.5f)
                )
            )
        }
    }

    data class Bucket(val range: Int, val scaleFactor: Float)
}