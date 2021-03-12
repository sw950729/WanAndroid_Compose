package com.silence.wanandroid.main.home.ui

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
import com.silence.wanandroid.main.home.model.ArticleListBean

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/
@Preview
@Composable
fun ArticleListItem(articleListBean: ArticleListBean) {
    Card(
        shape = RoundedCornerShape(4.dp),
        backgroundColor = Color.White,
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
                "作者：${articleListBean.author}", fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .constrainAs(author) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom, 8.dp)
                        top.linkTo(title.bottom, 18.dp)
                        end.linkTo(time.start)
                        width = Dimension.fillToConstraints
                    },
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