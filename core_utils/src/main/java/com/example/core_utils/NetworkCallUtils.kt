package com.example.core_utils

import java.security.MessageDigest
import java.security.PrivateKey

const val TIMESTAMP = "timestamp"
const val HASH = "hash"

fun getTimeStampPlusHash(privateKey: String, publicKey: String): Map<String, String> {
    val result: MutableMap<String, String> = mutableMapOf()

    val timestamp = getTimeStamp()

    result[TIMESTAMP] = timestamp
    result[HASH] = md5(timestamp + privateKey + publicKey).toHex()

    return result
}

private fun getTimeStamp() = (System.currentTimeMillis() / 1000).toString()

fun Int.getOffset() = (if (this == 0) 40 else this * 40)

private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(
    str.toByteArray(
        Charsets.UTF_8
    )
)

private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }