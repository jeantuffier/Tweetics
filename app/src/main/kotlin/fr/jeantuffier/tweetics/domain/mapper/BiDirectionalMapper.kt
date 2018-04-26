package fr.jeantuffier.tweetics.domain.mapper

interface BiDirectionalMapper<T, U> : Mapper<T, U> {
    fun toLeft(data: List<U>): List<T>
    fun toLeft(data: U): T
}