package com.example.core_utils

abstract class Mapper<T,out R> {
    abstract fun mapTo(input: T): R

}