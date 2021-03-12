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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.silence.wanandroid.config.SilenceColors
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.home.model.ArticleListBean

/**
 * @date:2021/3/12
 * @author:Silence
 * @describe:
 **/
@Composable
fun ArticleListItem(articleListBean: ArticleListBean) {
    Box(modifier = Modifier.padding(SilenceSizes.padding10)) {
        Card(
            shape = RoundedCornerShape(SilenceSizes.padding4),
            backgroundColor = Color.White,
            elevation = SilenceSizes.padding4
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(SilenceSizes.padding5)
                    .fillMaxWidth()
            ) {
                val (title, image, author, time) = createRefs()
                Text(
                    articleListBean.title,
                    fontSize = SilenceSizes.textSize16,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top, SilenceSizes.padding10)
                        start.linkTo(parent.start)
                        end.linkTo(image.start)
                        width = Dimension.fillToConstraints
                    }
                )
                Icon(Icons.Outlined.FavoriteBorder, null,
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top, SilenceSizes.padding10)
                        end.linkTo(parent.end)
                        start.linkTo(title.end, SilenceSizes.padding10)
                    })
                Text(
                    if (TextUtils.isEmpty(articleListBean.author)) {
                        "分享者：${articleListBean.shareUser}"
                    } else {
                        "作者：${articleListBean.author}"
                    },
                    fontSize = SilenceSizes.textSize12,
                    modifier = Modifier
                        .padding(start = SilenceSizes.padding5)
                        .constrainAs(author) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom, SilenceSizes.padding8)
                            top.linkTo(title.bottom, SilenceSizes.padding18)
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
                    articleListBean.niceDate, fontSize = SilenceSizes.textSize12,
                    modifier = Modifier
                        .padding(end = SilenceSizes.padding5)
                        .constrainAs(time) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, SilenceSizes.padding8)
                            start.linkTo(author.end, SilenceSizes.padding10)
                        }
                )
            }
        }
    }
}