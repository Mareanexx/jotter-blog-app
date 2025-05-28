package ru.mareanexx.feature_articles.presentation.components.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import ru.mareanexx.common.ui.theme.Shapes

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesSkeleton() {
    val shimmer = rememberShimmer(ShimmerBounds.View)

    Row(
        modifier = Modifier.fillMaxWidth().shimmer(shimmer)
            .padding(vertical = 7.dp).height(32.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        (1..5).forEach { _ ->
            BoxSkeleton(Modifier.width(70.dp), height = 32, shape = Shapes.medium)
        }
    }
}