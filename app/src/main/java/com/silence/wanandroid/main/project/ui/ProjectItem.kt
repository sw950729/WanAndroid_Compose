package com.silence.wanandroid.main.project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.silence.wanandroid.config.SilenceSizes
import com.silence.wanandroid.main.project.model.ProjectListItem
import com.silence.wanandroid.utils.DataUtils
import dev.chrisbanes.accompanist.coil.CoilImage

/**
 * @date:2021/3/27
 * @author:Silence
 * @describe:
 **/
@Composable
fun ProjectItem(projectListItem: ProjectListItem, modifier: Modifier) {
    Box(modifier = modifier.padding(SilenceSizes.padding10)) {
        Card(
            shape = RoundedCornerShape(SilenceSizes.padding4),
            backgroundColor = Color.White,
            elevation = SilenceSizes.padding4
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(SilenceSizes.padding10)
                    .fillMaxWidth()
            ) {
                val (image, title, desc, time) = createRefs()
                CoilImage(
                    data = projectListItem.envelopePic, null,
                    modifier = Modifier
                        .width(SilenceSizes.padding90)
                        .height(SilenceSizes.padding120)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    DataUtils.replaceAll(projectListItem.title),
                    fontSize = SilenceSizes.textSize15,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
                Text(projectListItem.desc,
                    fontSize = SilenceSizes.textSize14,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(desc) {
                        top.linkTo(title.bottom, SilenceSizes.padding8)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
                Text(projectListItem.niceDate, fontSize = SilenceSizes.textSize12,
                    modifier = Modifier
                        .padding(end = SilenceSizes.padding5)
                        .constrainAs(time) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}