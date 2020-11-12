package com.stefita.domain.common

abstract class Mapper<in T,E>{

    abstract fun mapFrom(from: T): E
}