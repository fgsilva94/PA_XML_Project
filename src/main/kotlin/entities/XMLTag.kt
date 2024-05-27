package entities

/**
 * Class representing a generic XML tag element.
 *
 * @property name The name of the XML tag.
 */
class XMLTag(
    override var name: String,
) : XMLElement() {
    /**
     * A list of child XML elements contained within this XML tag.
     */
    val children = mutableListOf<XMLElement>()

    /**
     * Returns the textual representation of the XML tag.
     * This includes indentation based on the element's depth, it attributes,
     * and the textual representation of its children if any.
     */
    override val toText: String
        get() = if (children.size > 0) {
            " ".repeat((depth - 1) * 2) +
            "<$name${attributes.toText}>" +
            "\n${children.joinToString(separator = "\n") { it.toText }}\n" +
            " ".repeat((depth - 1) * 2) +
            "</$name>"
        } else {
            " ".repeat((depth - 1) * 2) + "<$name${attributes.toText}/>"
        }

    /**
     * Accepts a visitor function that processes this XML tag and its children.
     *
     * @param visitor A function that takes an XMLElement and return a Boolean.
     */
    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            children.forEach { it.accept(visitor) }
    }

    /**
     * Adds a child element to this XML tag.
     *
     * @param element The child element to add.
     */
    fun addElement(element: XMLElement) {
        element.setParent(this)
        children.add(element)
    }

    /**
     * Removes a child element from this XML tag by name.
     *
     * @param name The name of the child element to remove.
     */
    fun removeElement(name: String) {
        val child = children.find { it.name == name }

        if (children.remove(child))
            child!!.setParent(null)
    }
}