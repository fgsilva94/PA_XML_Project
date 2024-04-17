package entities

class XMLTag(
    override var name: String,
) : XMLElement() {
    val children = mutableListOf<XMLElement>()
    override val toText: String
        get() = if (children.size > 0) {
            " ".repeat((depth - 1) * 2) +
            "<$name${attributes.toText}>" +
            "\n${children.joinToString(separator = "\n") { it.toText }}\n" +
            " ".repeat((depth - 1) * 2) +
            "</$name>"
        } else {
            " ".repeat((depth - 1) * 2) +
            "<$name${attributes.toText}/>"
        }

    override fun accept(visitor: (XMLElement) -> Boolean) {
        if (visitor(this))
            children.forEach {
                it.accept(visitor)
            }
    }
    fun addElement(element: XMLElement) {
        element.setParent(this)
        children.add(element)
    }

    fun removeElement(name: String) {
        val child = children.find {el ->
            el.name == name
        }

        if (children.remove(child)) {
            child!!.setParent(null)
        }
    }
}