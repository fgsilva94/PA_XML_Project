package entities

import annotations.*

/**
 * Class providing a method to convert various number types to a string with a percentage symbol.
 */
class AddPercentage {
    /**
     * Converts a number to a string with a percentage symbol.
     *
     * @param number The number to convert, which can be of types Byte, Short, Int, Long, Float, or Double.
     * @return The number as a string followed by a percentage symbol.
     * @throws NumberFormatException if the provided value is not a number.
     */
    fun toStringText(number: Any): String {
        return when(number) {
            is Byte -> "$number%"
            is Short -> "$number%"
            is Int -> "$number%"
            is Long -> "$number%"
            is Float -> "$number%"
            is Double -> "$number%"
            else -> throw NumberFormatException("$number must be an Number")
        }
    }
}

/**
 * Class providing a method to convert a string to uppercase.
 */
class ToUpperCaseAdapter {
    /**
     * Converts a value to its uppercase string representation.
     *
     * @param value The value to convert, which can be a String or any other type.
     * @return The uppercase string representation of the value.
     */
    fun toUpperString(value: Any): String {
        return when(value) {
            is String -> value.uppercase()
            else -> value.toString().uppercase()
        }
    }
}

/**
 * Data class representing a student with specific attributes.
 *
 * @property number The student's number, annotated to be an XML attribute.
 * @property name The student's name.
 * @property age The student's age.
 * @property course The student's course.
 * @property worker Indicates if the student is a worker.
 */
@XmlAdapter(ToUpperCaseAdapter::class)
data class Student(
    @XmlAttribute
    val number: Int,
    val name: String,
    val age: Int,
    val course: String,
    val worker: Boolean
)

/**
 * Data class representing a component with specific attributes.
 *
 * @property nome The component's name, annotated to be an XML attribute.
 * @property peso The component's weight, annotated to be an XML attribute and converted to a percentage string.
 * @property nota The component's grade, which is ignored during XML translation.
 */
@XmlName("componente")
data class Componente(
    @XmlAttribute
    val nome: String,
    @XmlAttribute
    @XmlString(AddPercentage::class)
    val peso: Int,
    @XmlIgnore
    val nota: Double
)

/**
 * Data class representing a FUC (course) with specific attributes.
 *
 * @property codigo The FUC's code, annotated to be an XML attribute.
 * @property nome The FUC's name.
 * @property ects The FUC's ECTS value.
 * @property avaliacao A list of components associated with the FUC.
 */
@XmlName("fuc")
data class Fuc(
    @XmlAttribute
    val codigo: String,
    val nome: String,
    val ects: Double,
    val avaliacao: List<Componente>
)

/**
 * Data class representing a study plan with specific attributes.
 *
 * @property curso The course name.
 * @property fuc A list of FUCs (courses) included in the study plan.
 */
@XmlName("plano")
data class Plano(
    val curso: String,
    val fuc: List<Fuc>
)