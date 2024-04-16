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

    override fun updateAttributeName(name: String, newName: String) {
        attributes.find { it.name == name }?.name = newName
    }

    override fun updateAttributeValue(name: String, newValue: String) {
        attributes.find { it.name == name }?.value = newValue
    }

    override fun removeAttribute(name: String) {
        attributes.removeIf { it.name == name }
    }
}