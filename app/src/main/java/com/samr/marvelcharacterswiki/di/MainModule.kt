package com.samr.marvelcharacterswiki.di

import com.samr.di.dataDomainModule
import com.sm.feature_ai.di.mainIAModule
import com.sm.feature_detail.di.mainDetailsModule
import com.sm.feature_listing.di.mainListingModule
import com.sm.feature_search.di.mainSearchModule
import org.koin.dsl.module

val mainModule = module {}
    .plus(presentationModule)
    .plus(dataDomainModule)
    .plus(mainListingModule)
    .plus(mainSearchModule)
    .plus(mainDetailsModule)
    .plus(mainIAModule)