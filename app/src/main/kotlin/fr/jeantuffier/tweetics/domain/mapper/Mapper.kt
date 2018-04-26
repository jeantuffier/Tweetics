package fr.jeantuffier.tweetics.domain.mapper

interface Mapper<T, U> {
    fun toRight(data: List<T>): List<U>
    fun toRight(data: T): U
}