package entities

class XMLTextTag(
    internal var name: String,
    internal var text: String,
) : XMLElement {
    var parent: XMLElement? = null
    private val attributes = mutableListOf<XMLAttribute>()

    override val depth: Int
        get() = if (parent is XMLDocument) 1 else 1 + (parent?.depth ?: 0)

    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<$name${attributes.joinToString(separator = "") { it.toText }}>" +
            text +
            "</$name>"

    override fun addAttribute(name: String, value: String) {
        attributes.add(XMLAttribute(name, value))
    }

    override fun updateAttribute(name: String, newName: String, newValue: String) {
        attributes.find { it.name == name }?.update(newName, newValue)
    }

    override fun removeAttribute(name: String) {
        attributes.removeIf { it.name == name }
    }
}