package entities

class XMLDocument(
    override val name: String = "xml",
    override val parent: XMLElement? = null,
    val root: XMLElement,
    override val attributes: List<XMLAttribute> =
        listOf(XMLAttribute("version", "1.0"), XMLAttribute("encoding", "UTF-8"))
): XMLElement {
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

    override fun toString(): String {
        TODO()
    }
}