package ru.mareanexx.feature_settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ru.mareanexx.common.ui.common.components.BoxSkeleton
import ru.mareanexx.common.ui.theme.Shapes

@Composable
fun ProfileSettingsSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Column(modifier = Modifier.shimmer(shimmer).fillMaxWidth().padding(horizontal = 20.dp)) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            BoxSkeleton(Modifier.width(110.dp).padding(top = 25.dp), height = 110, CircleShape)
            BoxSkeleton(Modifier.width(120.dp).padding(top = 25.dp), height = 18)
            BoxSkeleton(Modifier.width(160.dp).padding(top = 15.dp, bottom = 35.dp), height = 14)
        }
        (1..3).forEach { _ ->
            BoxSkeleton(Modifier.width(100.dp).padding(bottom = 15.dp), height = 12)
            BoxSkeleton(Modifier.fillMaxWidth().padding(bottom = 30.dp), height = 45, shape = Shapes.small)
        }
    }
}