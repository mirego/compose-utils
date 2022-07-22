package com.mirego.compose.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
fun statusBarPadding(): Dp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

@Composable
fun navigationBarPadding(): Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

@Composable
fun imePadding(): Dp = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
