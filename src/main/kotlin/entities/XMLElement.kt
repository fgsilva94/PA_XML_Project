package entities

sealed interface XMLElement {
    val depth: Int
    val toText: String
    fun addAttribute(name: String, value: String)
    fun updateAttributeName(name: String, newName: String)
    fun updateAttributeValue(name: String, newValue: String)
    fun removeAttribute(name: String)

    fun accept(visitor: (XMLElement) -> Boolean) {
        visitor(this)
    }

    fun countXMLElements(): Pair<Int, Int> {
        var countTag = 0
        var countTextTag = 0

        accept {
            when(it) {
                is XMLTag -> countTag++
                is XMLTextTag -> countTextTag++
                else -> {}
            }
            true
        }

        return Pair(countTag, countTextTag)
    }
}