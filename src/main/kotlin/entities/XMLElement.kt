package entities

interface XMLElement {
    val name: String
    val parent: XMLElement?
    val attributes: List<XMLAttribute>

    fun addAttribute(attrName: String, attrVal: String) {
        TODO()
    }

    fun removeAttribute(attrName: String) {
        TODO()
    }

    fun updateAttribute(attrName: String, attrNewName: String, attrNewVal: String) {
        TODO()
    }

    abstract override fun toString(): String
}