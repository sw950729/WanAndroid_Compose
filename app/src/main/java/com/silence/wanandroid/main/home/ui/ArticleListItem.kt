package com.silence.wanandroid.main.home.ui

import android.text.TextUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.main.home.model.ArticleListBean

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun ArticleListItem(articleListBean: ArticleListBean) {
    Box(modifier = Modifier.padding(10.dp)) {
        Card(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = Color.White,
            elevation = 4.dp
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                val (title, image, author, time) = createRefs()
                Text(
                    articleListBean.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(image.start)
                        width = Dimension.fillToConstraints
                    }
                )
                Icon(Icons.Outlined.FavoriteBorder, null,
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top, 10.dp)
                        end.linkTo(parent.end)
                        start.linkTo(title.end, 10.dp)
                    })
                Text(
                    if (TextUtils.isEmpty(articleListBean.author)) {
                        "分享者：${articleListBean.shareUser}"
                    } else {
                        "作者：${articleListBean.author}"
                    },
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .constrainAs(author) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom, 8.dp)
                            top.linkTo(title.bottom, 18.dp)
                            end.linkTo(time.start)
                            width = Dimension.fillToConstraints
                        },
                    color = if (TextUtils.isEmpty(articleListBean.author)) {
                        SilenceColors.colorMain
                    } else {
                        Color.Gray
                    }
                )
                Text(
                    articleListBean.niceDate, fontSize = 12.sp,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .constrainAs(time) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, 8.dp)
                            start.linkTo(author.end, 10.dp)
                        }
                )
            }
        }
    }
}