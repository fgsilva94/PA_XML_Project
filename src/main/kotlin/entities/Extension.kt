package entities

fun LinkedHashMap<String, String>.updateKey(key: String, newKey: String) {
    for (el in keys zip values) {
        this.remove(el.first)

        if (el.first == key)
            this[newKey] = el.second
        else
            this[el.first] = el.second
    }
}

val LinkedHashMap<String, String>.toText: String
    get() = (this.keys zip this.values).joinToString(separator = "") {
        " ${it.first}=\"${it.second}\""
    }
