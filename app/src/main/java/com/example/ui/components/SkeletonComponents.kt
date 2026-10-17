package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.CardSurface
import com.example.ui.theme.LineBorder
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.NavySecondary

private val SkeletonBase = Color(0xFFE3E7EE)
private val SkeletonHighlight = Color(0xFFF4F6FA)

/** Moving light band used by every skeleton block. */
@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "skeleton_shimmer")
    val offset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1800f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "skeleton_shimmer_offset"
    )
    return Brush.linearGradient(
        colors = listOf(SkeletonBase, SkeletonHighlight, SkeletonBase),
        start = Offset(offset - 700f, 0f),
        end = Offset(offset, 350f)
    )
}

@Composable
private fun SkeletonBlock(
    brush: Brush,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(6.dp)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(brush)
    )
}

@Composable
private fun SkeletonCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = CardSurface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, LineBorder),
        modifier = modifier
    ) {
        content()
    }
}

/** Dashboard-style placeholder: stat cards, a chart card and a few list rows. */
@Composable
private fun DashboardSkeletonContent(brush: Brush) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Heading + filter chip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkeletonBlock(brush, Modifier.width(150.dp).height(22.dp))
            SkeletonBlock(brush, Modifier.width(84.dp).height(30.dp), RoundedCornerShape(16.dp))
        }

        // Stat cards (2 x 2)
        repeat(2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(2) {
                    SkeletonCard(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            SkeletonBlock(brush, Modifier.fillMaxWidth(0.55f).height(11.dp))
                            SkeletonBlock(brush, Modifier.fillMaxWidth(0.4f).height(26.dp))
                            SkeletonBlock(brush, Modifier.fillMaxWidth(0.7f).height(9.dp))
                        }
                    }
                }
            }
        }

        // Chart card
        SkeletonCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SkeletonBlock(brush, Modifier.fillMaxWidth(0.4f).height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    listOf(0.5f, 0.8f, 0.35f, 0.95f, 0.6f, 0.75f).forEach { fraction ->
                        SkeletonBlock(
                            brush,
                            Modifier
                                .weight(1f)
                                .fillMaxHeight(fraction)
                        )
                    }
                }
            }
        }

        // List rows
        repeat(3) {
            SkeletonListRow(brush)
        }
    }
}

@Composable
private fun SkeletonListRow(brush: Brush) {
    SkeletonCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SkeletonBlock(brush, Modifier.size(38.dp), CircleShape)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SkeletonBlock(brush, Modifier.fillMaxWidth(0.75f).height(13.dp))
                SkeletonBlock(brush, Modifier.fillMaxWidth(0.45f).height(10.dp))
            }
            SkeletonBlock(brush, Modifier.width(54.dp).height(22.dp), RoundedCornerShape(11.dp))
        }
    }
}

/** Task-list style placeholder: search bar, filter chips and task cards. */
@Composable
private fun ListSkeletonContent(brush: Brush) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SkeletonBlock(brush, Modifier.fillMaxWidth().height(44.dp), RoundedCornerShape(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SkeletonBlock(brush, Modifier.width(78.dp).height(30.dp), RoundedCornerShape(16.dp))
            SkeletonBlock(brush, Modifier.width(96.dp).height(30.dp), RoundedCornerShape(16.dp))
            SkeletonBlock(brush, Modifier.width(70.dp).height(30.dp), RoundedCornerShape(16.dp))
        }
        repeat(5) {
            SkeletonCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SkeletonBlock(brush, Modifier.fillMaxWidth(0.8f).height(14.dp))
                    SkeletonBlock(brush, Modifier.fillMaxWidth(0.5f).height(11.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SkeletonBlock(brush, Modifier.width(64.dp).height(22.dp), RoundedCornerShape(11.dp))
                        SkeletonBlock(brush, Modifier.width(80.dp).height(22.dp), RoundedCornerShape(11.dp))
                    }
                }
            }
        }
    }
}

/**
 * Full-area loading placeholder for the tab content while the first data load is running.
 * It is drawn on top of the tab area and swallows touches until the data arrives.
 */
@Composable
fun TabLoadingSkeleton(dashboardStyle: Boolean, modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .pointerInput(Unit) {}
    ) {
        if (dashboardStyle) DashboardSkeletonContent(brush) else ListSkeletonContent(brush)
    }
}

/**
 * Whole-screen placeholder (top bar + content + bottom bar) shown while the app is starting
 * and the saved session / local data is being restored.
 */
@Composable
fun AppLoadingSkeleton(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()
    Column(modifier = modifier.fillMaxSize()) {
        // Top bar placeholder
        Surface(color = NavyPrimary, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkeletonBlock(brush, Modifier.width(92.dp).height(26.dp))
                SkeletonBlock(brush, Modifier.width(92.dp).height(32.dp), RoundedCornerShape(20.dp))
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            DashboardSkeletonContent(brush)
        }

        // Bottom navigation placeholder
        Surface(color = NavyPrimary, modifier = Modifier.fillMaxWidth()) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(NavySecondary)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(3) {
                        BottomNavPlaceholder(brush)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavPlaceholder(brush: Brush) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkeletonBlock(brush, Modifier.size(22.dp), CircleShape)
        Spacer(modifier = Modifier.height(6.dp))
        SkeletonBlock(brush, Modifier.width(46.dp).height(8.dp))
    }
}
