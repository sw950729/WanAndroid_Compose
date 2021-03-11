package com.silence.wanandroid.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.silence.wanandroid.main.MainActivity
import com.silence.wanandroid.R

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashView()
        }
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}


@Preview
@Composable
fun SplashView() {
    val image = ImageBitmap.imageResource(id = R.mipmap.splash_bg)
    Scaffold(content = {
        Image(
            image,
            null,
            modifier = Modifier.wrapContentHeight(),
            contentScale = ContentScale.FillBounds
        )
    })
}