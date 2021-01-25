package com.samr.core.utils

import java.security.MessageDigest

object Utils {

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(
        Charsets.UTF_8
    ))

    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}