package entities

class XMLTextTag(
    override var name: String,
    var text: String,
) : XMLElement() {
    override val toText: String
        get() = " ".repeat((depth - 1) * 2) +
            "<$name${attributes.toText}>$text</$name>"
}