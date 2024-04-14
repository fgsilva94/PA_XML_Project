package entities

class XMLDocument(
    override val name: String = "xml"
): XMLElement {
    var root: XMLElement? = null
    override val parent: XMLElement? = null

    override val attributes =
        mutableListOf(XMLAttribute("version", "1.0"), XMLAttribute("encoding", "UTF-8"))

    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<?$name${attributes.joinToString(separator = "") { " ${it.name}=\"${it.value}\"" }}?>" +
            "\n${root?.toText ?: ""}"

    fun addAttribute(tagName: String, attrName: String, attrVal: String) {
        TODO()
    }

    fun renameTag(tagName: String, tagNewName: String) {
        TODO()
    }

    fun renameAttribute(tagName: String, attrName: String, attrNewName: String) {
        TODO()
    }

    fun removeTag(tagName: String) {
        TODO()
    }

    fun removeAttribute(tagName: String, attrName: String) {
        TODO()
    }
}