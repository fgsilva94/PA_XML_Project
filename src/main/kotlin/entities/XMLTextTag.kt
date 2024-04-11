package entities

class XMLTextTag(
    override val name: String,
    override val parent: XMLElement?,
    val value: String,
    override val attributes: List<XMLAttribute>
) : XMLElement {

    override fun toString(): String {
        TODO()
    }
}