package com.silence.wanandroid.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.silence.wanandroid.MyApplication
import com.silence.wanandroid.R
import com.silence.wanandroid.base.Router
import com.silence.wanandroid.compose.SilenceIcon
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.login.LoginActivity
import com.silence.wanandroid.main.mine.FunctionItem
import com.silence.wanandroid.main.mine.MineFunctionList
import com.silence.wanandroid.main.mine.model.UserInfo
import com.silence.wanandroid.main.mine.model.isLogin

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun MinePage() {

    UserPad(userInfo = MyApplication.getApp().userState().value)
    Column(
        modifier = Modifier
            .padding(
                top = SilenceSizes.mineUserPadHeight - SilenceSizes.mineUserPadFunctionSpacer,
            )
            .fillMaxWidth()
            .background(
                Color.White, RoundedCornerShape(
                    topStart = SilenceSizes.mineFunctionTopCorner,
                    topEnd = SilenceSizes.mineFunctionTopCorner
                )
            )
    ) {
        FunctionList(functionItems = MineFunctionList.functions)
    }
}

@Composable
fun FunctionList(functionItems: List<FunctionItem>) {
    LazyColumn(
        content = {
            functionItems.forEach { item { MineFunctionItem(functionItem = it) } }
        }, modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(
                start = SilenceSizes.mineContentStartPadding,
                end = SilenceSizes.mineContentEndPadding
            )
    )

}

@Composable
fun MineFunctionItem(functionItem: FunctionItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.padding50)
            .clickable {
                functionItem.onClick()
            }
    ) {
        Image(
            painter = painterResource(id = functionItem.iconSource),
            contentDescription = "",
            modifier = Modifier
                .size(SilenceSizes.padding30)
        )
        Text(
            text = functionItem.title,
            style = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = SilenceSizes.textSize16
            ),
            modifier = Modifier.padding(start = SilenceSizes.mineContentStartPadding)
        )
        Text(
            text = functionItem.subTitle,
            style = TextStyle(color = Color.Black, fontSize = SilenceSizes.textSize12)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = functionItem.arrowText,
                style = TextStyle(color = Color.Black, fontSize = SilenceSizes.textSize14),
                modifier = Modifier.padding(start = SilenceSizes.mineContentStartPadding)
            )
            SilenceIcon(
                Icons.Filled.KeyboardArrowRight,
                modifier = Modifier.size(SilenceSizes.padding26)
            )
        }

    }
}

@Composable
fun UserPad(userInfo: UserInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.mineUserPadHeight)
            .background(SilenceColors.colorMain)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = SilenceSizes.mineUserAvatarLeftPadding,
                    end = SilenceSizes.mineUserAvatarRightPadding,
                )
                .width(SilenceSizes.mineUserAvatarHeight)
                .height(SilenceSizes.mineUserAvatarHeight)
        ) {
            val imageData: Any =
                if (userInfo.icon == "") R.mipmap.ic_launcher else userInfo.icon
            Image(
                painter = rememberImagePainter(
                    data = imageData,
                    builder = {
                        transformations(
                            RoundedCornersTransformation(3f)
                        )
                    },
                    onExecute = ImagePainter.ExecuteCallback.Default
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = "avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    SilenceSizes.padding12,
                    SilenceSizes.padding12,
                    SilenceSizes.padding0,
                    SilenceSizes.mineContentEndPadding
                )
        ) {

            Text(
                text = if (userInfo.publicName.isEmpty()) "暂未登录" else userInfo.publicName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = SilenceSizes.textSize16,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.clickable {
                    if (!userInfo.isLogin()) {
                        Router.rout(LoginActivity::class.java)
                    }
                }
            )

            Text(
                text = "等级：${userInfo.level}  排名：${userInfo.rank}  积分：${userInfo.integration}",
                style = TextStyle(color = Color.White, fontSize = SilenceSizes.textSize14),
                modifier = Modifier.padding(top = SilenceSizes.padding16)
            )
        }
    }

}



