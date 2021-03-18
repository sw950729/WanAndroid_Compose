package com.silence.wanandroid.main.common

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.net.RxNetWork
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun <T> T.asState(): MutableState<T> {
    return mutableStateOf(this)
}

fun <T> T.asLiveData(): MutableLiveData<T> {
    return MutableLiveData(this)
}

fun Boolean.selectColor(selectColor: Color, unSelectColor: Color): Color {
    return if (this) selectColor else unSelectColor
}

fun CharSequence.toastOnUI() = toastOnUI(this)

fun toastOnUI(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    with(Router.current()) {
        this.runOnUiThread {
            Toast.makeText(this, message, duration).show()
        }
    }
}
