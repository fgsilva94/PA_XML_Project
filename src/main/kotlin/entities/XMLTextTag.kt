package entities

import extensions.toText
import javax.naming.InvalidNameException

/**
 * Class representing an XML text tag element.
 *
 * @property name The name of the XML text tag.
 * @property text The text content of the XML text tag.
 */
class XMLTextTag(
    override var name: String,
    var text: String,
) : XMLElement() {

    init {
        if (!isValidName(name)) throw InvalidNameException("Invalid name $name")
    }

    /**
     * Returns the textual representation of the XML text tag.
     * This includes indentation based on the element's depth and its attributes.
     */
    override val toText: String
        get() = " ".repeat((depth - 1) * 2) + "<$name${attributes.toText}>$text</$name>"
}