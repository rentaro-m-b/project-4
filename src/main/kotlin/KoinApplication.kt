package com.example

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.example")
@Configuration
class KoinModule

@KoinApplication
object KoinApplication
