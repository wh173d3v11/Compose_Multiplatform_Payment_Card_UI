package com.fierydev.composemultiplatform.shared

class AndroidPlatform : Platform {
    override val isIosPlatform: Boolean
        get() = false

}

actual fun getPlatform(): Platform = AndroidPlatform()
