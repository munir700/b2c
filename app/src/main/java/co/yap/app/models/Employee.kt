package co.yap.app.models

data class Employee(override val name: String, override val age: Int) : User(name, age) {
    constructor(name: String, age: Int, gender: String): this(name, age)
}