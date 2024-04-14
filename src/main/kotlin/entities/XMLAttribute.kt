package entities

class XMLAttribute(
    val name: String,
    val value: String
) {
    val toText: String
        get() = " $name=\"$value\""
}