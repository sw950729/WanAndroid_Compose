package com.silence.wanandroid.main.common

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.silence.wanandroid.base.Router

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

fun CharSequence.toastOnUI() = toastOnUI(this)

fun toastOnUI(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Router.current().let {
        it.runOnUiThread {
            Toast.makeText(it, message, duration).show()
        }
    }
}
