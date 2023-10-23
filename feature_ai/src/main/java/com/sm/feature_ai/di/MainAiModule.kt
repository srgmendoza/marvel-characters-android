package com.sm.feature_ai.di

import com.sm.feature_ai.ui.navigation.navigationModule
import org.koin.dsl.module

val mainIAModule = module {}
    .plus(
        listOf(
            navigationModule
        )
    )