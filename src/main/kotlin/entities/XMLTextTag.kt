package entities

class XMLTextTag(
    override val name: String,
    val text: String,
    override val parent: XMLElement,
) : XMLElement {
    override val attributes = mutableListOf<XMLAttribute>()

    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<$name${attributes.joinToString(separator = "") { it.toText }}>" +
            text +
            "</$name>"

    init {
        when (parent) {
            is XMLDocument -> parent.root = this
            is XMLTag -> parent.children.add(this)
        }
    }
}