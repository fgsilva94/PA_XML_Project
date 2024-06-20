import entities.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.naming.InvalidNameException

/**
 * Test suite for XMLTextTag class, testing its creation, renaming, text updating,
 * and attribute manipulation functionalities.
 */
class TestXMLTextTag {

    /**
     * Tests the creation of an XMLTextTag instance with valid and invalid tag names.
     */
    @Test
    fun testCreate() {
        val textTag = XMLTextTag("nome", "Programação Avançada")
        assertEquals("<nome>Programação Avançada</nome>", textTag.toText)

        val textTag2 = XMLTextTag("_nome", "Programação Avançada")
        assertEquals("<_nome>Programação Avançada</_nome>", textTag2.toText)

        val textTag3 = XMLTextTag("Nome", "Programação Avançada")
        assertEquals("<Nome>Programação Avançada</Nome>", textTag3.toText)

        assertThrows<InvalidNameException> {
            XMLTextTag("0nome", "Programação Avançada")
        }

        assertThrows<InvalidNameException> {
            XMLTextTag(" nome", "Programação Avançada")
        }

        assertThrows<InvalidNameException> {
            XMLTextTag("*nome", "Programação Avançada")
        }

        assertThrows<InvalidNameException> {
            XMLTextTag("!nome", "Programação Avançada")
        }
    }

    /**
     * Tests the renaming functionality of XMLTextTag, including valid and invalid scenarios.
     */
    @Test
    fun testRename() {
        val textTag = XMLTextTag("nome", "Programação Avançada")
        assertEquals("<nome>Programação Avançada</nome>", textTag.toText)

        textTag.updateName("_nome")
        assertEquals("<_nome>Programação Avançada</_nome>", textTag.toText)

        textTag.updateName("Nome")
        assertEquals("<Nome>Programação Avançada</Nome>", textTag.toText)

        assertThrows<InvalidNameException> {
            textTag.updateName("0nome")
        }

        assertThrows<InvalidNameException> {
            textTag.updateName(" nome")
        }

        assertThrows<InvalidNameException> {
            textTag.updateName("*nome")
        }

        assertThrows<InvalidNameException> {
            textTag.updateName("!nome")
        }
    }

    /**
     * Tests the text updating functionality of XMLTextTag.
     */
    @Test
    fun testUpdateText() {
        val textTag = XMLTextTag("nome", "Programação Avançada")
        assertEquals("<nome>Programação Avançada</nome>", textTag.toText)

        textTag.text = "Discertação"
        assertEquals("<nome>Discertação</nome>", textTag.toText)
    }

    /**
     * Tests attribute manipulation functionalities of XMLTextTag.
     */
    @Test
    fun testAttributes() {
        val textTag = XMLTextTag("nome", "Programação Avançada")
        assertEquals("<nome>Programação Avançada</nome>", textTag.toText)

        textTag.addAttribute("ects", "6.0")
        assertEquals("<nome ects=\"6.0\">Programação Avançada</nome>", textTag.toText)

        textTag.addAttribute("codigo", "M4310")
        assertEquals("<nome ects=\"6.0\" codigo=\"M4310\">Programação Avançada</nome>", textTag.toText)

        textTag.updateAttributeName("ects", "ECTS")
        assertEquals("<nome ECTS=\"6.0\" codigo=\"M4310\">Programação Avançada</nome>", textTag.toText)

        textTag.updateAttributeValue("codigo", "M1340")
        assertEquals("<nome ECTS=\"6.0\" codigo=\"M1340\">Programação Avançada</nome>", textTag.toText)

        textTag.removeAttribute("codigo")
        assertEquals("<nome ECTS=\"6.0\">Programação Avançada</nome>", textTag.toText)
    }
}