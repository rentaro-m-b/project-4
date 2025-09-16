package com.example.common

import java.time.Clock

object ClockProvider {
    fun provideUtcClock(): Clock = Clock.systemUTC()
}
