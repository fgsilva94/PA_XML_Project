package entities

sealed interface XMLElement {
    val depth: Int
    val toText: String
    fun addAttribute(name: String, value: String)
    fun updateAttribute(name: String, newName: String, newValue: String)
    fun removeAttribute(name: String)
}