package com.silence.wanandroid.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.mine.FunctionItem
import com.silence.wanandroid.main.mine.MineFunctionList
import com.silence.wanandroid.main.mine.UserInfo
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * @date:2021/3/11
 * @author:Silence
 * @describe:
 **/
//@Preview
@Composable
fun MinePage() {

    var userInfo: UserInfo = UserInfo();
    val userState = mutableStateOf(userInfo)

    UserPad(userInfo = userState.value)
    Column(
        modifier = Modifier
            .padding(
                top = SilenceSizes.mine_user_pad_height - SilenceSizes.mine_user_pad_function_spacer,
            )
            .fillMaxWidth()
            .background(
                Color.White, RoundedCornerShape(
                    topStart = SilenceSizes.mine_function_top_corner,
                    topEnd = SilenceSizes.mine_function_top_corner
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
                start = SilenceSizes.mine_content_start_padding,
                end = SilenceSizes.mine_content_end_padding
            )
    )

}

@Composable
fun MineFunctionItem(functionItem: FunctionItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                functionItem.onClick()
            }
    ) {
        Image(
            painter = painterResource(id = functionItem.iconSource),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
        )
        Text(
            text = functionItem.title,
            style = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(start = SilenceSizes.mine_content_start_padding)
        )
        Text(
            text = functionItem.subTitle,
            style = TextStyle(color = Color.Black, fontSize = 12.sp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = functionItem.arrowText,
                style = TextStyle(color = Color.Black, fontSize = 14.sp),
                modifier = Modifier.padding(start = SilenceSizes.mine_content_start_padding)
            )
            Image(
                painter = painterResource(id = functionItem.arrowSource),
                contentDescription = "",
                modifier = Modifier.size(26.dp)
            )
        }

    }
}

@Composable
fun UserPad(userInfo: UserInfo) {

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(SilenceSizes.mine_user_pad_height)
            .background(SilenceColors.colorMain)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = SilenceSizes.mine_user_avatar_left_padding,
                    0.dp,
                    end = SilenceSizes.mine_user_avatar_right_padding,
                    0.dp,
                )
                .width(SilenceSizes.mine_user_avatar_height)
                .height(SilenceSizes.mine_user_avatar_height)
        ) {
            CoilImage(
                data = userInfo.avatarUrl,
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
                .padding(12.dp, 12.dp, 0.dp, SilenceSizes.mine_content_end_padding)
        ) {

            Text(
                text = userInfo.nickName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace
                )
            )

            Text(
                text = "等级：${userInfo.level}  排名：${userInfo.rank}  积分：${userInfo.integration}",
                style = TextStyle(color = Color.White, fontSize = 14.sp),
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)
            )
        }
    }


}



