package com.example.challenge2025.ui.components.assets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.challenge2025.model.BottomNavItem
import com.example.challenge2025.ui.theme.Blue
import com.example.challenge2025.ui.theme.GrayLight
import com.example.challenge2025.ui.theme.Green
import com.example.challenge2025.ui.theme.Orange

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem(route = "home", title = "Home", icon = Icons.Filled.Home),
        BottomNavItem(route = "tests", title = "Testes", icon = Icons.AutoMirrored.Filled.FormatListBulleted),
        BottomNavItem(route = "dashboard", title = "Painel", icon = Icons.Filled.Assessment),
        BottomNavItem(route = "menu", title = "Menu", icon = Icons.Filled.Menu)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                val pillColor = when (item.route) {
                    "home" -> Blue
                    "tests" -> Orange
                    "dashboard" -> Green
                    else -> GrayLight
                }

                BottomNavItem(
                    item = item,
                    isSelected = isSelected,
                    pillColor = pillColor,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    pillColor: Color,
    onClick: () -> Unit
) {
    if (isSelected) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(pillColor)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .clickable(onClick = onClick)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.title,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    } else {

        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(43.dp)
                .clickable(onClick = onClick)
                .padding(8.dp)
        )
    }
}
