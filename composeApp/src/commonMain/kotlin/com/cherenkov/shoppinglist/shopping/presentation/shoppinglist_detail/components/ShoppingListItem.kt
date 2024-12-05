package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cherenkov.shoppinglist.shopping.domain.ProductItem

@Composable
fun ShoppingListItem(
    item: ProductItem,
    onCheckedChange: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val animatedTextColor by animateColorAsState(
                targetValue = if (item.isChecked) Color.Gray else Color.White
            )

            Text(
                text = item.name,
                color = animatedTextColor,
                fontSize = 16.sp,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None
            )

            CustomCheckBox(isChecked = item.isChecked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun CustomCheckBox(
    isChecked: Boolean,
    onCheckedChange: () -> Unit
) {
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isChecked) Color(0xFF1E6652) else Color.Transparent
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isChecked) Color(0xFF1E6652) else Color.Gray
    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .background(animatedBackgroundColor, CircleShape)
            .border(
                width = 2.dp,
                color = animatedBorderColor,
                shape = CircleShape
            )
            .clickable { onCheckedChange() },
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}