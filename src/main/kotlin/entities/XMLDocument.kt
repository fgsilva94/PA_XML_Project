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

    override fun updateAttribute(name: String, newName: String, newValue: String) {
        attributes.find { it.name == name }?.update(newName, newValue)
    }

    override fun removeAttribute(name: String) {
        attributes.removeIf { it.name == name }
    }
}