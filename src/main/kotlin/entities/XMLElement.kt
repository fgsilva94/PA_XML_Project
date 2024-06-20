package entities

import com.sun.jdi.InvalidTypeException
import extensions.updateKey
import javax.naming.InvalidNameException

/**
 * Abstract class representing a generic XML element.
 */
abstract class XMLElement {

    /**
     * The name of the XML element.
     */
    open lateinit var name: String
        protected set

    /**
     * The parent of this XML element. It can be null.
     */
    private var parent: XMLElement? = null

    /**
     * A map of the attributes of this XML element.
     */
    protected val attributes = linkedMapOf<String, String>()

    /**
     * Abstract property that return the textual representation of the XML element.
     */
    abstract val toText: String

    /**
     * The depth of this XML element in the hierarchy.
     */
    protected val depth: Int
        get() = if (this is XMLDocument || parent is XMLDocument) 1 else 1 + (parent?.depth ?: 0)

    /**
     * The full path of this XML element in the document tree.
     */
    private val path: String
        get() = "${if (this is XMLDocument) "/" else if (parent == null) "" else parent!!.path + "/"}$name"

    /**
     * Checks if the provided name is valid for an XML element or attribute.
     *
     * @param name The name to validate.
     * @return True if the name is valid, false otherwise.
     */
    protected fun isValidName(name: String): Boolean {
        var regex =  "^(?!xml|Xml|XMl|XML|xMl|xML|Xml|xmL)[a-zA-Z_][a-zA-Z0-9._-]*$".toRegex()
        return regex.matches(name)
    }
    /**
     * Update the name of this XML element.
     * Throws an exception if called on an XMLDocument.
     *
     * @param newName The new name for the XML element.
     * @throws InvalidTypeException if this is an XMLDocument.
     * @throws InvalidNameException if the new name is not valid.
     */
    fun updateName(newName: String) {
        if (this is XMLDocument) throw InvalidTypeException("XML Document tag can't change name")
        if (!isValidName(newName)) throw InvalidNameException("Invalid name $newName")
        name = newName
    }

    /**
     * Sets the parent of this XML element.
     * Throws an exception if called on an XMLDocument.
     *
     * @param element The new parent element.
     * @throws InvalidTypeException if this is an XMLDocument.
     */
    fun setParent(element: XMLElement?) {
        if (this is XMLDocument) throw InvalidTypeException("XML Document can't have parents")
        parent = element
    }

    /**
     * Adds an attribute to this XML element.
     *
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     * @throws InvalidNameException if the attribute name is not valid.
     */
    fun addAttribute(name: String, value: String) {
        if (!isValidName(name)) throw InvalidNameException("Invalid name $name")
        attributes[name] = value
    }

    /**
     * Removes an attribute from this XML element.
     *
     * @param name The name of the attribute to remove.
     */
    fun removeAttribute(name: String) {
        attributes.remove(name)
    }

    /**
     * Update the name of an existing attribute.
     *
     * @param name The current name of the attribute.
     * @param newName The new name for the attribute.
     * @throws InvalidNameException if the new attribute name is not valid.
     */
    fun updateAttributeName(name: String, newName: String) {
        if (!isValidName(newName)) throw InvalidNameException("Invalid name $newName")
        attributes.updateKey(name, newName)
    }

    /**
     * Update the value of an existing attribute.
     *
     * @param name The name of the attribute to update.
     * @param newValue The new value for the attribute.
     */
    fun updateAttributeValue(name: String, newValue: String) {
        attributes[name] = newValue
    }

    /**
     * Accepts a visitor function that processes this XML element.
     *
     * @param visitor A function that takes an XMLElement and returns a Boolean.
     */
    open fun accept(visitor: (XMLElement) -> Boolean) {
        visitor(this)
    }

    /**
     * Count the number of XMLTag and XMLTextTag elements inside this XMLElement.
     *
     * @return a Pair where the first value is the count of XMLTag elements and the second value is the count of XMLTextTag elements.
     */
    fun countXMLElements(): Pair<Int, Int> {
        var countTag = 0
        var countTextTag = 0

        accept {
            when(it) {
                is XMLTag -> countTag++
                is XMLTextTag -> countTextTag++
                else -> {}
            }
            true
        }

        return Pair(countTag, countTextTag)
    }

    /**
     * Finds all XML elements that match a given XPath.
     *
     * @param path The XPath to match.
     * @return A list of XMLElements that match the given XPath.
     */
    fun xPath(path: String): List<XMLElement> {
        val results = mutableListOf<XMLElement>()

        accept {
            if (path.startsWith("/") && it.path == path) {
                results.add(it)
            } else if (it.path.endsWith(path)) {
                results.add(it)
            }
            true
        }

        return results
    }
}