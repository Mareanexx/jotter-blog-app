package ru.mareanexx.core.ui.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mareanexx.core.R
import ru.mareanexx.core.ui.theme.LightBlue
import ru.mareanexx.core.ui.theme.Shapes

@Composable
fun BottomNavBar(selectedTab: Tabs, onTabSelected: (Tabs) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 15.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = R.drawable.home_icon,
                cd = R.string.nav_route_home,
                selected = selectedTab == Tabs.Home,
                navigateTo = { onTabSelected(Tabs.Home) }
            )
            NavItem(
                icon = R.drawable.collections_icon,
                cd = R.string.nav_route_collections,
                selected = selectedTab == Tabs.Collections,
                navigateTo = { onTabSelected(Tabs.Collections) }
            )
            CirclePlaceholder()
            NavItem(
                icon = R.drawable.notifications_icon,
                cd = R.string.nav_route_notifications,
                selected = selectedTab == Tabs.Notifications,
                navigateTo = { onTabSelected(Tabs.Notifications) }
            )
            NavItem(
                icon = R.drawable.settings_icon,
                cd = R.string.nav_route_settings,
                selected = selectedTab == Tabs.Settings,
                navigateTo = { onTabSelected(Tabs.Settings) }
            )
        }
        AddArticleButton(alignModifier = Modifier.align(Alignment.TopCenter), onAddArticle = {  })
    }
}

@Composable
fun CirclePlaceholder() {
    Box(modifier = Modifier.size(70.dp).background(Color.Transparent, shape = CircleShape))
}

@Composable
fun AddArticleButton(alignModifier: Modifier, onAddArticle: () -> Unit) {
    LargeFloatingActionButton(
        modifier = alignModifier.size(70.dp).addBlueShadow(),
        shape = CircleShape,
        onClick = onAddArticle,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(modifier = Modifier.size(30.dp), painter = painterResource(R.drawable.add_icon), contentDescription = stringResource(R.string.add_new_article))
    }
}

fun Modifier.addBlueShadow() = shadow(elevation = 10.dp, shape = Shapes.extraLarge, ambientColor = LightBlue, spotColor = LightBlue)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(
        selectedTab = Tabs.Home,
        onTabSelected = {  }
    )
}