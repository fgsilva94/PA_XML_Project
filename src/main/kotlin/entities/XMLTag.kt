package entities

class XMLTag(
    internal var name: String,
) : XMLElement {
    internal var parent: XMLElement? = null
    internal val children = mutableListOf<XMLElement>()
    private val attributes = mutableListOf<XMLAttribute>()

    override val depth: Int
        get() = if (parent is XMLDocument) 1 else 1 + (parent?.depth ?: 0)

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

    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            children.forEach {
                it.accept(visitor)
            }
    }

    fun addElement(element: XMLElement) {
        when (element) {
            is XMLTag,  -> element.parent = this
            is XMLTextTag -> element.parent = this
            else -> throw Exception("Fail to add element. Wrong type")
        }
        children.add(element)
    }

    fun removeElement(name: String) {
        val child = children.find {el ->
            when(el) {
                is XMLTag -> el.name == name
                is XMLTextTag -> el.name == name
                else -> false
            }
        }

        when(child) {
            is XMLTag -> {
                children.remove(child)
                child.parent = null
            }
            is XMLTextTag -> {
                children.remove(child)
                child.parent = null
            }
            else -> throw Exception("Fail to remove element. Wrong type")
        }
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
}