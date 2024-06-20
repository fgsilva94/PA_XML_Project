package annotations

import kotlin.reflect.KClass

/**
 * Annotation used to specify a custom name for XML element.
 *
 * @property name The custom name to use for the XML element.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class XmlName(val name: String)

/**
 * Annotation used to indicate that a property should be ignored during XML translation.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XmlIgnore

/**
 * Annotation used to indicate that a property should be translated as an XML attribute.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XmlAttribute

/**
 * Annotation used to specify a custom adapter for converting a property to a string during XML translation.
 *
 * @property kclass The class with the function to use for converting the property to a string.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XmlString(val kclass: KClass<*>)

/**
 * Annotation used to specify a custom adapter for a class during XML translation.
 *
 * @property kclass The class with the function to use for translate the annotated class.
 */
@Target(AnnotationTarget.CLASS)
annotation class XmlAdapter(val kclass: KClass<*>)