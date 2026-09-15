package com.lordyhas.readinglight.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lordyhas.readinglight.ui.main.LightPreset

@Composable
fun PresetsRow(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Teintes recommandées",
            style = MaterialTheme.typography.labelLarge,
            color = Color.White.copy(alpha = 0.9f),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LightPreset.values().forEach { preset ->
                val isSelected = isColorClose(selectedColor, preset.color)
                PresetItem(
                    preset = preset,
                    isSelected = isSelected,
                    onClick = { onColorSelected(preset.color) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PresetItem(
    preset: LightPreset,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Transparent,
        label = "presetBorder"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 2.5.dp else 0.dp,
        label = "presetBorderWidth"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) Color.White.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.06f),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(preset.color)
                    .border(borderWidth, borderColor, CircleShape)
            ) {
                if (isSelected) {
                    val checkColor = if (preset.color.luminance() > 0.5f) Color.Black else Color.White
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Sélectionné",
                        tint = checkColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = preset.title,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.75f),
                maxLines = 2,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

private fun isColorClose(c1: Color, c2: Color): Boolean {
    val rDiff = Math.abs(c1.red - c2.red)
    val gDiff = Math.abs(c1.green - c2.green)
    val bDiff = Math.abs(c1.blue - c2.blue)
    return (rDiff + gDiff + bDiff) < 0.05f
}
