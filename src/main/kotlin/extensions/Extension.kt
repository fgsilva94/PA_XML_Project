package extensions

import com.sun.jdi.InvalidTypeException
import entities.XMLDocument
import entities.XMLElement
import entities.XMLTag
import entities.XMLTextTag
import kotlin.reflect.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * Updates the key in a LinkedHashMap while preserving the order.
 *
 * @param key The current key to update.
 * @param newKey The new key to replace the current key.
 * @throws IllegalArgumentException If the provided key does not exist in the map.
 */
fun LinkedHashMap<String, String>.updateKey(key: String, newKey: String) {
    for (el in keys zip values) {
        this.remove(el.first)

        if (el.first == key) this[newKey] = el.second else this[el.first] = el.second
    }
}

/**
 * Extension property to convert a LinkedHashMap of attributes to a string format for XML representation.
 */
val LinkedHashMap<String, String>.toText: String
    get() = (this.keys zip this.values).joinToString(separator = "") {
        " ${it.first}=\"${it.second}\""
    }

/**
 * Extension property to retrieve the fields of a data class.
 *
 * @throws IllegalArgumentException If the class is not a data class.
 */
val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "instance must be data class" }
        return primaryConstructor!!.parameters.map { p ->
            declaredMemberProperties.find { it.name == p.name }!!
        }
    }

/**
 * Helper function to create an XML document and build its structure.
 *
 * @param attributes A map of attributes to add to the XML document.
 * @param build A lambda to build the XML document structure.
 * @return The created XMLDocument.
 */
fun document(attributes: Map<String, String> = mapOf(), build: XMLElement.() -> Unit): XMLDocument {
    val doc = XMLDocument()

    attributes.map { doc.addAttribute(it.key, it.value) }

    return doc.apply { build(this) }
}

/**
 * Extension function to add an XMLTag to an XMLElement and build its structure.
 *
 * @param name The name of the XML tag to add.
 * @param attributes A map of attributes to add to the XML tag.
 * @param build A lambda to build the XML tag's structure.
 * @throws InvalidTypeException If the current XMLElement is an XMLTextTag.
 * @throws Exception If the current XMLElement type is unknown.
 */
fun XMLElement.tag(name: String, attributes: Map<String, String> = mapOf(), build: XMLElement.() -> Unit) {
    val tag = XMLTag(name)

    addAttributes(tag, attributes)

    tag.apply { build(this) }
}

/**
 * Adds attributes to an XML element.
 *
 * @param tag The XML element to add attributes to.
 * @param attributes A map of attributes to add.
 * @throws InvalidTypeException If the current XMLElement is an XMLTextTag.
 * @throws Exception If the current XMLElement type is unknown.
 */
private fun XMLElement.addAttributes(
    tag: XMLElement,
    attributes: Map<String, String>
) {
    when (this) {
        is XMLDocument -> this.addElement(tag)
        is XMLTag -> this.addElement(tag)
        is XMLTextTag -> throw InvalidTypeException("TextTag can't have children")
        else -> throw Exception("unknown type ${this.javaClass.canonicalName}")
    }

    attributes.map { tag.addAttribute(it.key, it.value) }
}

/**
 * Extension function to add an XMLTextTag to an XMLElement.
 *
 * @param name The name of the XML text tag to add.
 * @param text The text content of the XML text tag.
 * @param attributes A map of attributes to add to the XML text tag.
 * @throws InvalidTypeException If the current XMLElement is an XMLTextTag.
 * @throws Exception If the current XMLElement type is unknown.
 */
fun XMLElement.textTag(name: String, text: String, attributes: Map<String, String> = mapOf()) {
    val textTag = XMLTextTag(name, text)

    addAttributes(textTag, attributes)
}

/**
 * Operator function to find a child element by name.
 *
 * @param name The name of the child element to find.
 * @return The found child element.
 * @throws Exception if the element type is unknown or the child element is not found.
 */
operator fun XMLElement.div(name: String): XMLElement {
    val tmp = when (this) {
        is XMLDocument -> if (this.child?.name == name) this.child else null
        is XMLTag -> this.children.find { it.name == name }
        else -> null
    }

    return tmp ?: throw Exception("unknown type ${this.javaClass.canonicalName}")
}

/**
 * Operator function to add an attribute to an XMLElement.
 *
 * @param attr A pair representing the attribute name and value.
 */
operator fun XMLElement.plus(attr: Pair<String, String>) =
    addAttribute(attr.first, attr.second)

/**
 * Operator function to remove an attribute from an XMLElement.
 *
 * @param name The name of the attribute to remove.
 */
operator fun XMLElement.minus(name: String) =
    removeAttribute(name)