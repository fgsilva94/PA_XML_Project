package entities

class XMLAttribute(
    internal var name: String,
    internal var value: String
) {
    val toText: String
        get() = " $name=\"$value\""
}