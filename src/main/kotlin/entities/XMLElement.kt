package entities

abstract class XMLElement {
    open lateinit var name: String
        protected set
    var parent: XMLElement? = null
        private set
    protected val attributes = linkedMapOf<String, String>()
    val depth: Int
        get() = if (this is XMLDocument || parent is XMLDocument) 1 else 1 + (parent?.depth ?: 0)
    abstract val toText: String
    val path: String
        get() = "${if (this is XMLDocument) "/" else if (parent == null) "" else parent!!.path + "/"}$name"

    fun updateName(newName: String) {
        if (this is XMLDocument) throw Exception("XML Document can't change name")
        name = newName
    }

    fun setParent(element: XMLElement?) {
        if (this is XMLDocument) throw Exception("XML Document can't have parents")
        parent = element
    }

    fun addAttribute(name: String, value: String) {
        attributes[name] = value
    }
    fun updateAttributeName(name: String, newName: String) {
        attributes.updateKey(name, newName)
    }
    fun updateAttributeValue(name: String, newValue: String) {
        attributes[name] = newValue
    }
    fun removeAttribute(name: String) {
        attributes.remove(name)
    }

    open fun accept(visitor: (XMLElement) -> Boolean) {
        visitor(this)
    }

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