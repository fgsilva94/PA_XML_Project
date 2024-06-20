package entities

import extensions.toText

/**
 * Class representing an XML document.
 */
class XMLDocument : XMLElement() {

    /**
     * The root child element of this XML document.
     */
    var child: XMLElement? = null
        private set

    /**
     * Returns the textual representation of the XML document.
     * This includes the XML declaration and the textual representation of its child element, if present.
     */
    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<?$name${attributes.toText}?>" +
            if (child != null) "\n${child!!.toText}" else ""

    /**
     * Initializes the XML document with a default name and attributes.
     * Sets the name to "xml" and adds the version and encoding attributes.
     *
     */
    init {
        name = "xml"
        attributes.putAll(mapOf(Pair("version", "1.0"), Pair("encoding", "UTF-8")))
    }

    /**
     * Accepts a visitor function that processes this XML document and its child element.
     *
     * @param visitor A function that takes an XMLElement and returns a Boolean.
     */
    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            child?.accept(visitor)
    }

    /**
     * Adds a child element to this XML document.
     *
     * @param element The child element to add.
     */
    fun addElement(element: XMLElement) {
        element.setParent(this)
        child = element
    }

    /**
     * Removes the child element from this XML document.
     * Throws an exception if there is no child element.
     *
     * @throws Exception if the XML document does not have a child element.
     */
    fun removeElement() {
        child?.setParent(null) ?: throw Exception("The XML Document doesn't have a child")
        child = null
    }

    /**
     * Renames all elements with a specific name in the document to a new name.
     *
     * @param name The current name of the elements to rename.
     * @param newName The new name for the elements.
     */
    fun renameAllElements(name: String, newName: String) {
        accept {
            if (it.name == name)
                it.updateName(newName)
            true
        }
    }

    /**
     * Removes all elements with a specific name from the document.
     *
     * @param name The name of the elements to remove.
     */
    fun removeAllElements(name: String) {
        accept {
            when (it) {
                is XMLDocument -> {
                    if (it.child?.name == name)
                        it.removeElement()
                }
                is XMLTag -> {
                    while (it.children.find { el -> el.name == name } != null)
                        it.removeElement(name)
                }
            }
            true
        }
    }

    /**
     * Renames all attributes with a specific name in elements with a specific name to a new attribute name.
     *
     * @param elementName The name of the elements containing the attribute to rename.
     * @param attributeName The current name of the attribute to rename.
     * @param newAttributeName The new name for the attribute.
     */
    fun renameAllAttributes(elementName: String, attributeName: String, newAttributeName: String) {
        accept {
            if (it.name == elementName)
                it.updateAttributeName(attributeName, newAttributeName)
            true
        }
    }

    /**
     * Removes all attributes with a specific name from elements with a specific name.
     *
     * @param elementName The name of the elements containing the attribute to remove.
     * @param attributeName The name of the attribute to remove.
     */
    fun removeAllAttributes(elementName: String, attributeName: String) {
        accept {
            if (it.name == elementName)
                it.removeAttribute(attributeName)
            true
        }
    }
}
