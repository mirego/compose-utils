package com.mirego.compose.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mirego.compose.utils.extensions.isScrolled

@Composable
fun AnimatedVisibilityFade(
    visible: Boolean,
    modifier: Modifier = Modifier,
    exitAnimatedSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow),
    enterAnimatedSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow),
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        content = content,
        exit = fadeOut(exitAnimatedSpec),
        enter = fadeIn(enterAnimatedSpec)
    )
}

/**
 * Animates the visibility of a content that depends on a nullable value.
 * If content is null, content is not visible. Otherwise it is.
 * When transitioning from non-null to null, the non-null value will remain available to the content for the duration of the animation.
 */
@Composable
fun <T : Any> AnimatedVisibilityWithNullable(
    modifier: Modifier = Modifier,
    nullableValue: T?,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    label: String = "AnimatedVisibilityNullable",
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    val visible = nullableValue != null
    val remembered = remember(key1 = Unit) { mutableStateOf(nullableValue) }
    if ((remembered.value == null && nullableValue != null) || (remembered.value != null && nullableValue != null && remembered.value != nullableValue)) {
        remembered.value = nullableValue
    }
    val stateCleanNeeded = remembered.value != null && nullableValue == null

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        label = label,
    ) {
        remembered.value?.let {
            content(it)
        }
        DisposableEffect(key1 = stateCleanNeeded) {
            onDispose {
                if (stateCleanNeeded) {
                    remembered.value = null
                }
            }
        }
    }
}

@Composable
fun <T : Any> AnimatedVisibilityWithNullableFade(
    modifier: Modifier = Modifier,
    nullableValue: T?,
    label: String = "AnimatedVisibilityNullable",
    content: @Composable AnimatedVisibilityScope.(T) -> Unit,
) {
    AnimatedVisibilityWithNullable(
        modifier = modifier,
        nullableValue = nullableValue,
        enter = fadeIn(),
        exit = fadeOut(),
        label = label,
        content = content
    )
}

@Composable
fun animateElevationWithLazyListState(
    listState: LazyListState,
    scrolledElevation: Dp = 6.dp,
): State<Dp> {
    val isScrolled: Boolean by remember {
        derivedStateOf { listState.isScrolled }
    }
    return animateDpAsState(
        if (isScrolled) scrolledElevation else 0.dp,
        animationSpec = tween(500)
    )
}

@Composable
fun animateElevationWithScrollState(
    scrollState: ScrollState,
    scrolledElevation: Dp = 6.dp,
): State<Dp> {
    val isScrolled: Boolean by remember {
        derivedStateOf { scrollState.value > 0 }
    }
    return animateDpAsState(
        if (isScrolled) scrolledElevation else 0.dp,
        animationSpec = tween(500)
    )
}
