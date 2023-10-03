package com.sm.base_core

import androidx.fragment.app.Fragment
import org.koin.java.KoinJavaComponent

abstract class BaseFragment<T>: Fragment() {

    protected abstract val classToken: Class<T>

    protected val viewModel: T by KoinJavaComponent.inject(classToken)


}