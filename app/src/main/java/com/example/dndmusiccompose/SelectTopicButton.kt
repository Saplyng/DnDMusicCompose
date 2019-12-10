package com.example.dndmusiccompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.dp
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.foundation.shape.DrawShape
import androidx.ui.foundation.shape.border.Border
import androidx.ui.foundation.shape.border.DrawBorder
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.material.MaterialColors
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeColor
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.example.dndmusiccompose.sampledata.testWhatever


@Composable
fun SelectTopicButton(
    onSelected: ((Boolean) -> Unit)? = null,
    selected: Boolean = false,
    itemTitle: String
){
    Ripple(bounded = false) {
        Toggleable(checked = selected, onCheckedChange = onSelected) {
            Container(width = 36.dp, height = 36.dp){
                if (selected){
                    DrawSelectTopicButtonOn()
                    AddToMixer(itemTitle = itemTitle)
                } else {
                    DrawSelectTopicButtonOff()
                    RemoveFromMixer(itemTitle = itemTitle)
                }
            }
        }

    }
}

@Composable
fun AddToMixer(itemTitle: String){
    if(itemTitle !in testWhatever){
        testWhatever.add(itemTitle)
    }else if (itemTitle in testWhatever){
        null
    }

}

@Composable
fun RemoveFromMixer(itemTitle: String){
    if(itemTitle in testWhatever){
        testWhatever.remove(itemTitle)
    } else if (itemTitle !in testWhatever){
        null
    }

}

@Composable
private fun DrawSelectTopicButtonOn(){

    DrawShape(
        shape = CircleShape,
        color = +themeColor { primary}
    )
    DrawVector(+vectorResource(R.drawable.ic_check))
}

@Composable
private fun DrawSelectTopicButtonOff(){
    val borderColor = (+themeColor { onSurface }).copy(alpha = 0.12f)
    DrawBorder(shape = CircleShape, border = Border(borderColor, 2.dp))
    DrawVector(+vectorResource(R.drawable.ic_add))
}


fun getSongs(context: Context) {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "audio/mpeg"
    }
    val extras: Bundle? = intent.getExtras()

    context.startActivity(Intent.createChooser(intent, "Share post"))
}
