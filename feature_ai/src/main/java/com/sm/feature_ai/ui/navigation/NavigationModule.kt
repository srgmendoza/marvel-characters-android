package com.sm.feature_ai.ui.navigation

import com.sm.feature_ai_api.AiFeatureApi
import org.koin.dsl.module

val navigationModule = module {
    single<AiFeatureApi> { AiFeatureImpl() } //Exposing navigation to the DI system
}