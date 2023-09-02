package com.sm.network_core.di

import com.sm.network_core.NetworkCore
import com.sm.network_core.NetworkCoreImpl
import org.koin.dsl.module

val networkCoreModule = module {
    factory<NetworkCore> {
        NetworkCoreImpl()
    }
}
