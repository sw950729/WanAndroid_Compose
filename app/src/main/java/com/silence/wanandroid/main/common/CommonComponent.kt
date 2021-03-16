package com.silence.wanandroid.main.common

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes

class CommonComponent {
}


fun <T> T.asState(): MutableState<T> {
    return mutableStateOf(this)
}

fun <T> T.asLiveData(): MutableLiveData<T> {
    return MutableLiveData(this)
}

fun Boolean.selectColor(selectColor: Color, unSelectColor: Color): Color {
    return if (this) selectColor else unSelectColor
}

fun toastOnUI(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Router.current()
        ?.let {
            it.runOnUiThread {
                Toast.makeText(it, message, duration).show()
            }
        }
}
