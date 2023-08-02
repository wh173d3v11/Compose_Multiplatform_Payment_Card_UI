package com.fierydev.composemultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform