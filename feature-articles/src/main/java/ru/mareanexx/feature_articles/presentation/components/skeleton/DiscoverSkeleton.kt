package ru.mareanexx.feature_articles.presentation.components.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.common.components.BoxSkeleton

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiscoverSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Row(modifier = Modifier.fillMaxWidth().shimmer(shimmer), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            BoxSkeleton(Modifier.fillMaxWidth(), height = 100)
            (1..2).forEach { _ ->
                BoxSkeleton(Modifier.fillMaxWidth().padding(top = 7.dp), height = 15)
            }
            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 10.dp), height = 11)

            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 20.dp), height = 280)
            (1..2).forEach { _ ->
                BoxSkeleton(Modifier.fillMaxWidth().padding(top = 7.dp), height = 15)
            }
            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 10.dp), height = 11)
        }
        Column(modifier = Modifier.weight(1f)) {
            Spacer(Modifier.height(20.dp))
            BoxSkeleton(Modifier.fillMaxWidth(), height = 200)
            (1..2).forEach { _ ->
                BoxSkeleton(Modifier.fillMaxWidth().padding(top = 7.dp), height = 15)
            }
            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 10.dp), height = 11)

            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 20.dp), height = 100)
            (1..2).forEach { _ ->
                BoxSkeleton(Modifier.fillMaxWidth().padding(top = 7.dp), height = 15)
            }
            BoxSkeleton(Modifier.fillMaxWidth().padding(top = 10.dp), height = 11)
        }
    }
}