package entities

class XMLTag(
    override val name: String,
    override val parent: XMLElement,

) : XMLElement {
    val children = mutableListOf<XMLElement>()
    override val attributes = mutableListOf<XMLAttribute>()

    override val toText: String
        get() = if (children.size > 0) {
            " ".repeat((depth - 1) * 2) +
            "<$name${attributes.joinToString(separator = "") { it.toText }}>" +
            "\n${children.joinToString(separator = "\n") { it.toText }}\n" +
            " ".repeat((depth - 1) * 2) +
            "</$name>"
        } else {
            " ".repeat((depth - 1) * 2) +
            "<$name${attributes.joinToString(separator = "") { it.toText }}/>"
        }

    init {
        when (parent) {
            is XMLDocument -> parent.root = this
            is XMLTag -> parent.children.add(this)
        }
    }

    fun addElement(newElement: XMLElement) {
        TODO()
    }

    fun removeElement(elemName: String) {
        TODO()
    }
}