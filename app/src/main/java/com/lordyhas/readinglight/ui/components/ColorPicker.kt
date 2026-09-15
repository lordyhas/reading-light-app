package com.lordyhas.readinglight.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorPicker(
    currentColor: Color,
    onColorChanged: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val hsv = remember(currentColor) {
        val array = FloatArray(3)
        AndroidColor.colorToHSV(currentColor.toArgb(), array)
        array
    }

    val hue = hsv[0]
    val saturation = hsv[1]
    val value = hsv[2]

    val hexString = remember(currentColor) {
        String.format("#%06X", (0xFFFFFF and currentColor.toArgb()))
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Color preview & HEX
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(currentColor)
                        .border(1.5.dp, Color.White.copy(alpha = 0.6f), CircleShape)
                )
                Text(
                    text = "Ajustement personnalisé",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Text(
                text = hexString,
                style = MaterialTheme.typography.labelMedium,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        // Hue Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Teinte", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                Text(text = "${hue.toInt()}°", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            }
            Slider(
                value = hue,
                onValueChange = { newHue ->
                    val newColor = Color(AndroidColor.HSVToColor(floatArrayOf(newHue, saturation, value)))
                    onColorChanged(newColor)
                },
                valueRange = 0f..360f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                ),
                modifier = Modifier.height(32.dp)
            )
        }

        // Saturation Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Saturation", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                Text(text = "${(saturation * 100).toInt()}%", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            }
            Slider(
                value = saturation,
                onValueChange = { newSat ->
                    val newColor = Color(AndroidColor.HSVToColor(floatArrayOf(hue, newSat, value)))
                    onColorChanged(newColor)
                },
                valueRange = 0f..1f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                ),
                modifier = Modifier.height(32.dp)
            )
        }

        // Value / Tone Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Intensité", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                Text(text = "${(value * 100).toInt()}%", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
            }
            Slider(
                value = value,
                onValueChange = { newVal ->
                    val newColor = Color(AndroidColor.HSVToColor(floatArrayOf(hue, saturation, newVal.coerceIn(0.1f, 1f))))
                    onColorChanged(newColor)
                },
                valueRange = 0.1f..1f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                ),
                modifier = Modifier.height(32.dp)
            )
        }
    }
}
