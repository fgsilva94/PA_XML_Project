package entities

class XMLDocument : XMLElement() {
    var child: XMLElement? = null
        private set
    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<?$name${attributes.toText}?>" +
            if (child != null) "\n${child!!.toText}" else ""

    init {
        name = "xml"
        attributes.putAll(mapOf(Pair("version", "1.0"), Pair("encoding", "UTF-8")))
    }


    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            child?.accept(visitor)
    }

    fun addElement(element: XMLElement) {
        element.setParent(this)
        child = element
    }

    fun removeElement() {
        child?.setParent(null) ?: throw Exception("The XML Document doesn't have a child")
        child = null
    }

    fun renameAllElements(name: String, newName: String) {
        accept {
            if (it.name == name)
                it.updateName(newName)
            true
        }
    }

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

    fun renameAllAttributes(elementName: String, attributeName: String, newAttributeName: String) {
        accept {
            if (it.name == elementName)
                it.updateAttributeName(attributeName, newAttributeName)
            true
        }
    }

    fun removeAllAttributes(elementName: String, attributeName: String) {
        accept {
            if (it.name == elementName)
                it.removeAttribute(attributeName)
            true
        }
    }
}
