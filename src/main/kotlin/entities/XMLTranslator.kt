package entities

import annotations.*
import extensions.dataClassFields
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.*

/**
 * Object responsible for translating data classes annotated with XML annotations into XML elements.
 */
object XMLTranslator {

    /**
     * Translates a given data class instance into an XML element.
     *
     * @param obj The data class instance to be translated.
     * @return The root XML element representing the translated data class.
     */
    fun translate(obj: Any): XMLElement {
        val clazz = obj::class
        val name =  getXmlTransformed(clazz, getXmlName(clazz))
        val rootEl = XMLTag(name)

        clazz.dataClassFields.forEach {
            if (!it.hasAnnotation<XmlIgnore>()) {
                val propName = getXmlTransformed(clazz, getXmlName(it))

                if (it.returnType.isSubtypeOf(Iterable::class.starProjectedType)) {
                    val propValues = it.call(obj)

                    if (propValues is List<*>) {
                        propValues.forEach { prop ->
                            if (prop != null) {
                                val innerTag = getPropTag(prop)
                                rootEl.addElement(innerTag)
                            }
                        }
                    }
                } else {
                    val propValue = getXmlTransformed(clazz, getXmlValue(it, obj))

                    if (it.hasAnnotation<XmlAttribute>()) {
                        rootEl.addAttribute(propName, propValue)
                    } else {
                        val tag = XMLTextTag(propName, propValue)
                        rootEl.addElement(tag)
                    }
                }
            }
        }

        return rootEl;
    }

    /**
     * Recursively translates a property into an XML element.
     *
     * @param obj The property value to be translated.
     * @return The XML element representing the property.
     */
    private fun getPropTag(obj: Any): XMLElement {
        val clazz = obj::class
        val name = getXmlName(clazz)
        val rootEl = XMLTag(name)

        clazz.dataClassFields.forEach {
            if (!it.hasAnnotation<XmlIgnore>()) {
                val propName = getXmlName(it)

                if (it.returnType.isSubtypeOf(Iterable::class.starProjectedType)) {
                    val tag = XMLTag(propName)
                    val propValue = it.call(obj)

                    if (propValue is List<*>) {
                        propValue.forEach { prop ->
                            if (prop != null) {
                                val innerTag = getPropTag(prop)
                                tag.addElement(innerTag)
                            }
                        }
                    }
                    rootEl.addElement(tag)
                } else {
                    val propValue = getXmlValue(it, obj)

                    if (it.hasAnnotation<XmlAttribute>()) {
                        rootEl.addAttribute(propName, propValue)
                    } else {
                        val tag = XMLTextTag(propName, propValue)
                        rootEl.addElement(tag)
                    }
                }
            }
        }

        return rootEl;
    }

    /**
     * Retrieves the XML name for a given class, using the XmlName annotation if present.
     *
     * @param clazz The class to get the XML name for.
     * @return The XML name of the class.
     */
    private fun getXmlName(clazz: KClass<*>): String {
        return clazz.findAnnotation<XmlName>()?.name ?: clazz.simpleName ?: throw Exception("Class has no name.")
    }

    /**
     * Retrieves the XML name for a given property, using the XmlName annotation if present.
     *
     * @param property The property to get the XML name for.
     * @return The XML name of the property.
     */
    private fun getXmlName(property: KProperty<*>): String {
        return property.findAnnotation<XmlName>()?.name ?: property.name
    }

    /**
     * Retrieves the XML value for a given property, applying any [mlString transformation if present.
     *
     * @param property The property to get the XML value for.
     * @param obj The instance containing the property.
     * @return The XML value of the property.
     */
    private fun getXmlValue(property: KProperty<*>, obj: Any): String {
        if (property.hasAnnotation<XmlString>()) {
            val xmlStringAnnotation = property.findAnnotation<XmlString>()!!
            val kclass = xmlStringAnnotation.kclass
            val func = kclass.memberFunctions.find { it.name == "toStringText" }

            if (func != null) {
                val instance = kclass.createInstance()
                val propValue = property.call(obj)

                if (propValue != null) {
                    return func.call(instance, propValue).toString()
                }
            }
        } else {
            return property.call(obj).toString()
        }

        throw Exception("Fail to get property ${property.name}")
    }

    /**
     * Applies an XML transformation using an XmlAdapter annotation if present on the class.
     *
     * @param clazz The class to apply the transformation to.
     * @param value The value to be transformed.
     * @return The transformed value.
     */
    private fun getXmlTransformed(clazz: KClass<*>, value: String): String {
        if (clazz.hasAnnotation<XmlAdapter>()) {
            val xmlTransformerAnnotation = clazz.findAnnotation<XmlAdapter>()!!
            val kclass = xmlTransformerAnnotation.kclass
            val func = kclass.memberFunctions.find { it.name == "toUpperString" }

            if (func != null) {
                val instance = kclass.createInstance()

                return func.call(instance, value).toString()
            }
        } else {
            return value
        }

        throw Exception("Fail to transform value $value")
    }
}