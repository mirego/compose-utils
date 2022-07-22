package com.mirego.compose.utils.extensions

import androidx.compose.foundation.lazy.LazyListState

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex != 0 || firstVisibleItemScrollOffset != 0
