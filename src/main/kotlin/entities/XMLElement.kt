package entities

interface XMLElement {
    val name: String
    val parent: XMLElement?
    val attributes: MutableList<XMLAttribute>
    val toText: String

    val depth: Int
        get() = if (parent is XMLDocument) 1 else 1 + (parent?.depth ?: 0)

    fun addAttribute(attrName: String, attrVal: String) {
        TODO()
    }

    fun removeAttribute(attrName: String) {
        TODO()
    }

    fun updateAttribute(attrName: String, attrNewName: String, attrNewVal: String) {
        TODO()
    }
}