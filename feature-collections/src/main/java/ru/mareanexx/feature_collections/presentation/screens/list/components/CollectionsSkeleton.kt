package ru.mareanexx.feature_collections.presentation.screens.list.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.common.components.BoxSkeleton
import ru.mareanexx.common.ui.theme.LightGray
import ru.mareanexx.common.ui.theme.Shapes

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CollectionsSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(
        modifier = Modifier.shimmer(shimmer).fillMaxWidth().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        (1..3).forEach { _ ->
            Column(modifier = Modifier.fillMaxWidth().border(width = 2.dp, LightGray, Shapes.extraSmall).padding(25.dp)) {
                BoxSkeleton(Modifier.width(90.dp), height = 15)
                BoxSkeleton(Modifier.width(50.dp).padding(top = 10.dp, bottom = 20.dp), height = 10)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BoxSkeleton(Modifier.weight(0.3f), height = 80)
                    BoxSkeleton(Modifier.weight(0.3f), height = 80)
                    BoxSkeleton(Modifier.weight(0.3f), height = 80)
                }
            }
        }
    }
}