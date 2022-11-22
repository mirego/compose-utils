package com.mirego.compose.utils.extensions

import androidx.compose.foundation.lazy.LazyListState

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex != 0 || firstVisibleItemScrollOffset != 0

suspend fun LazyListState.scrollToItemIfLayout(
    index: Int,
    scrollOffset: Int = 0
) {
    if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
        scrollToItem(
            index = index,
            scrollOffset = scrollOffset
        )
    }
}
