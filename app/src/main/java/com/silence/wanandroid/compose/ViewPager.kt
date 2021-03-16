import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import kotlin.math.*

/**
 * @date:2021/3/14
 * @author:Silence
 * @describe: 代码来源：https://gist.github.com/adamp/07d468f4bcfe632670f305ce3734f511，有做部分修改
 */
@Composable
fun rememberPagerState(
    currentPage: Int = 0,
    minPage: Int = 0,
    maxPage: Int = 0,
): PagerState {
    return rememberSaveable(
        saver = PagerState.Saver(),
    ) {
        PagerState(currentPage, minPage, maxPage)
    }
}

class PagerState(
    currentPage: Int = 0,
    minPage: Int = 0,
    maxPage: Int = 0,
) {
    private val velocityTracker = VelocityTracker()

    companion object {
        fun Saver(): Saver<PagerState, *> = listSaver(
            save = { listOf(it.currentPage, it.minPage, it.maxPage) },
            restore = {
                PagerState(
                    currentPage = it[0],
                    minPage = it[1],
                    maxPage = it[2],
                )
            }
        )
    }

    private var _minPage by mutableStateOf(minPage)
    var minPage: Int
        get() = _minPage
        set(value) {
            _minPage = value.coerceAtMost(_maxPage)
            _currentPage = _currentPage.coerceIn(_minPage, _maxPage)
        }

    private var _maxPage by mutableStateOf(maxPage, structuralEqualityPolicy())
    var maxPage: Int
        get() = _maxPage
        set(value) {
            _maxPage = value.coerceAtLeast(_minPage)
            _currentPage = _currentPage.coerceIn(_minPage, maxPage)
        }

    private var _currentPage by mutableStateOf(currentPage.coerceIn(minPage, maxPage))
    var currentPage: Int
        get() = _currentPage
        set(value) {
            _currentPage = value.coerceIn(minPage, maxPage)
        }

    enum class SelectionState { Selected, Undecided }

    var selectionState by mutableStateOf(SelectionState.Selected)

    suspend inline fun <R> selectPage(block: PagerState.() -> R): R = try {
        selectionState = SelectionState.Undecided
        block.invoke(this)
    } finally {
        selectPage()
    }

    suspend fun selectPage() {
        currentPage -= currentPageOffset.roundToInt()
        snapToOffset(0f)
        selectionState = SelectionState.Selected
    }

    private var _currentPageOffset = Animatable(0f).apply {
        updateBounds(-1f, 1f)
    }
    val currentPageOffset: Float
        get() = _currentPageOffset.value

    suspend fun snapToOffset(offset: Float) {
        val max = if (currentPage == minPage) 0f else 1f
        val min = if (currentPage == maxPage) 0f else -1f
        _currentPageOffset.snapTo(offset.coerceIn(min, max))
    }

    suspend fun fling(velocity: Float) {
        if (velocity < 0 && currentPage == maxPage) return
        if (velocity > 0 && currentPage == minPage) return
        val currentOffset = _currentPageOffset.value
        when {
            currentOffset.sign == velocity.sign &&
                    (velocity.absoluteValue > 1.5f ||
                            currentOffset.absoluteValue > 0.5 && currentOffset.absoluteValue < 1f
                            ) -> {
                _currentPageOffset.animateTo(1f.withSign(velocity))
                selectPage()
            }
            else -> {
                _currentPageOffset.animateTo(0f)
                selectPage()
            }
        }
    }

    fun addPosition(uptimeMillis: Long, position: Offset) {
        velocityTracker.addPosition(timeMillis = uptimeMillis, position = position)
    }

    suspend fun dragEnd(pageSize: Int) {
        val velocity = velocityTracker.calculateVelocity()
        fling(velocity.x / pageSize)
    }
}

@Immutable
private data class PageData(val page: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = this@PageData
}

private val Measurable.page: Int
    get() = (parentData as? PageData)?.page ?: error("no PageData for measurable $this")

@Composable
fun ViewPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    maxSize: Int? = 0,
    maxHeight: Dp,
    offscreenLimit: Int = 2,
    dragEnabled: Boolean = true,
    pageContent: @Composable (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var pageSize by remember { mutableStateOf(0) }
    Layout(
        content = {
            val minPage = 0
            val maxPage = maxSize ?: run {
                minPage
            }
            for (page in minPage until maxPage) {
                val pageData = PageData(page)
                Column(modifier = pageData) {
                    pageContent(page)
                }
            }
        },
        modifier = modifier
            .pointerInput(Unit) {
                if (dragEnabled) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            with(state) {
                                selectionState = PagerState.SelectionState.Undecided
                                val pos = pageSize * currentPageOffset
                                val max =
                                    if (currentPage == minPage) 0 else pageSize * offscreenLimit
                                val min =
                                    if (currentPage == maxPage) 0 else -pageSize * offscreenLimit
                                val newPos =
                                    (pos + dragAmount).coerceIn(min.toFloat(), max.toFloat())
                                if (newPos != 0f) {
                                    change.consumePositionChange()
                                    addPosition(change.uptimeMillis, change.position)
                                    coroutineScope.launch {
                                        snapToOffset(newPos / pageSize)
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                state.dragEnd(pageSize)
                            }
                        },
                        onDragCancel = {
                            coroutineScope.launch {
                                state.dragEnd(pageSize)
                            }
                        },
                    )
                }
            }
    ) { measurables, constraints ->
        layout(constraints.maxWidth, maxHeight.toPx().toInt()) {
            val currentPage = state.currentPage
            val offset = state.currentPageOffset
            measurables
                .map {
                    it.measure(constraints) to it.page
                }
                .forEach { (placeable, page) ->
                    val xCenterOffset = (constraints.maxWidth - placeable.width) / 2
                    if (currentPage == page) {
                        pageSize = placeable.width
                    }
                    val xItemOffset = ((page + offset - currentPage) * placeable.width).roundToInt()
                    placeable.placeRelative(
                        x = xCenterOffset + xItemOffset,
                        y = 0
                    )
                }
        }
    }
}

