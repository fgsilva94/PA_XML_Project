import entities.XMLDocument
import entities.XMLTag
import org.junit.jupiter.api.Test
class Tests {
    val XMLRootEl = XMLTag("plano")
    val XMLDoc = XMLDocument(root = XMLRootEl)

    @Test
    fun tmpTest() {
        println(XMLDoc)
    }

}

