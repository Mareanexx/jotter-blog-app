package ru.mareanexx.feature_collections.presentation.screens.collection.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.common.components.BoxSkeleton

@Composable
fun ConcreteCollectionSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(modifier = Modifier.fillMaxSize().shimmer(shimmer), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 30.dp, bottom = 15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(ru.mareanexx.core.common.R.drawable.arrow_left_icon), contentDescription = null)
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                BoxSkeleton(Modifier.width(130.dp), height = 15)
            }
        }
        (1..7).forEach { _ ->
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(15.dp), verticalAlignment = Alignment.CenterVertically) {
                BoxSkeleton(Modifier.width(100.dp), height = 74)
                Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    BoxSkeleton(Modifier.width(200.dp), height = 18)
                    BoxSkeleton(Modifier.width(130.dp), height = 13)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewConcreteCollectionSkeleton() {
    ConcreteCollectionSkeleton()
}