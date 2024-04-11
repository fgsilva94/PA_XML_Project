package entities

class XMLTag(
    override val name: String,
    override val parent: XMLElement? = null,
    val children: List<XMLElement> = emptyList(),
    override val attributes: List<XMLAttribute> = emptyList()
) : XMLElement {

    fun addElement(newElement: XMLElement) {
        TODO()
    }

    fun removeElement(elemName: String) {
        TODO()
    }

    override fun toString(): String {
        TODO()
    }
}