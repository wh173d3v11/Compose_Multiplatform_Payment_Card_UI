package com.fierydev.composemultiplatform.shared

class IOSPlatform : Platform {
    override val isIosPlatform: Boolean
        get() = true
}

actual fun getPlatform(): Platform = IOSPlatform()
