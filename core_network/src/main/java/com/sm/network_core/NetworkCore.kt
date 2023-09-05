package com.sm.network_core

interface NetworkCore {
    fun <T>getCoreNetwork(baseUrl: String, endPoint: Class<T>): T
}