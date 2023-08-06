package com.fierydev.composemultiplatform.shared

interface Platform {
    val isIosPlatform: Boolean
}

expect fun getPlatform(): Platform