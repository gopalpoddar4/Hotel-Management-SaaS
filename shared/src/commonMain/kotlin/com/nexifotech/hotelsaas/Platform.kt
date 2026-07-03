package com.nexifotech.hotelsaas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform