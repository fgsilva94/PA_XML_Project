package entities

class XMLDocument: XMLElement {
    internal val name: String = "xml"
    internal var child: XMLElement? = null
        private set
    private val attributes = mutableListOf<XMLAttribute>()

    override val depth: Int
        get() = 1

    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<?$name${attributes.joinToString(separator = "") { it.toText }}?>" +
            if (child != null) "\n${child!!.toText}" else ""

    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            child?.accept(visitor)
    }

    fun addElement(element: XMLElement) {
        when (element) {
            is XMLTag -> element.parent = this
            is XMLTextTag -> element.parent = this
            else -> throw Exception("Fail to add element. Wrong type")
        }
        child = element
    }

    fun removeElement() {
        when (child) {
            is XMLTag -> (child as XMLTag).parent = null
            is XMLTextTag -> (child as XMLTextTag).parent = null
            else -> throw Exception("Fail to add element. Wrong type")
        }
        child = null
    }

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

    fun renameAllElements(name: String, newName: String) {
        accept {
            when(it) {
                is XMLTag -> {
                    if (it.name == name)
                        it.name = newName
                }
                is XMLTextTag -> {
                    if (it.name == name)
                        it.name = newName
                }
                else -> throw Exception("Fail to rename element. Wrong type")
            }
            true
        }
    }

    fun removeAllElements(name: String) {
//        accept {
//            when(it) {
//                is XMLTag -> {
//                    for (child in it.children)
//                        it.removeElement(child.)
//                    }
//                }
//                is XMLTextTag -> {
//                    if (it.name == name) {
//                        it.updateAttributeName(attributeName, newAttributeName)
//                    }
//                }
//                is XMLDocument -> {
//                    if (it.name == name) {
//                        it.updateAttributeName(attributeName, newAttributeName)
//                    }
//                }
//            }
//            true
//        }
    }

    fun renameAllAttributes(elementName: String, attributeName: String, newAttributeName: String) {
        accept {
            when(it) {
                is XMLTag -> {
                    if (it.name == elementName)
                        it.updateAttributeName(attributeName, newAttributeName)
                }
                is XMLTextTag -> {
                    if (it.name == elementName)
                        it.updateAttributeName(attributeName, newAttributeName)
                }
                is XMLDocument -> {
                    if (it.name == elementName)
                        it.updateAttributeName(attributeName, newAttributeName)
                }
            }
            true
        }
    }

    fun removeAllAttributes(elementName: String, attributeName: String) {
        accept {
            when(it) {
                is XMLTag -> {
                    if (it.name == elementName) {
                        it.removeAttribute(attributeName)
                    }
                }
                is XMLTextTag -> {
                    if (it.name == elementName) {
                        it.removeAttribute(attributeName)
                    }
                }
                is XMLDocument -> {
                    if (it.name == elementName) {
                        it.removeAttribute(attributeName)
                    }
                }
            }
            true
        }
    }
}