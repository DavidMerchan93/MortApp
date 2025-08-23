package com.davidmerchan.presentation.utils

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationComponent(
    animationRes: Int? = null,
    animationUrl: String? = null,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    iterations: Int = LottieConstants.IterateForever,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true
) {
    val composition by rememberLottieComposition(
        spec = when {
            animationRes != null -> LottieCompositionSpec.RawRes(animationRes)
            animationUrl != null -> LottieCompositionSpec.Url(animationUrl)
            else -> LottieCompositionSpec.RawRes(android.R.drawable.ic_dialog_alert)
        }
    )

    LottieAnimation(
        composition = composition,
        modifier = modifier.size(size),
        iterations = iterations,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay
    )
}

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    // You can add a loading animation URL here
    // Example: https://lottie.host/some-loading-animation.json
    LottieAnimationComponent(
        animationUrl = "https://lottie.host/4f47e6c8-c9d1-4f86-8d9a-8b5c5d8e9f0a/LoadingDots.json",
        modifier = modifier,
        size = size
    )
}

@Composable
fun ErrorAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    // Example error animation
    LottieAnimationComponent(
        animationUrl = "https://lottie.host/error-animation.json",
        modifier = modifier,
        size = size,
        iterations = 1
    )
}

@Composable
fun SuccessAnimation(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    // Example success animation
    LottieAnimationComponent(
        animationUrl = "https://lottie.host/success-animation.json",
        modifier = modifier,
        size = size,
        iterations = 1
    )
}