import entities.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.naming.InvalidNameException

/**
 * Test suite for XMLTag class, testing its creation, renaming, child element manipulation,
 * and attribute functionalities.
 */
class TestXMLTag {

    /**
     * Tests the creation of an XMLTag instance with valid and invalid tag names.
     */
    @Test
    fun testCreate() {
        val tag = XMLTag("fuc")
        assertEquals("<fuc/>", tag.toText)

        tag.addElement(XMLTag("nome"))
        tag.addElement(XMLTag("ects"))
        tag.addElement(XMLTag("avaliacoes"))
        assertEquals("<fuc>\n" +
            "  <nome/>\n" +
            "  <ects/>\n" +
            "  <avaliacoes/>\n" +
        "</fuc>", tag.toText)


        val tag2 = XMLTag("_fuc")
        assertEquals("<_fuc/>", tag2.toText)

        val tag3 = XMLTag("Fuc")
        assertEquals("<Fuc/>", tag3.toText)

        assertThrows<InvalidNameException> {
            XMLTag("0fuc")
        }

        assertThrows<InvalidNameException> {
            XMLTag(" nome")
        }

        assertThrows<InvalidNameException> {
            XMLTag("*fuc")
        }

        assertThrows<InvalidNameException> {
            XMLTag("!fuc")
        }
    }

    /**
     * Tests the renaming functionality of XMLTag, including valid and invalid scenarios.
     */
    @Test
    fun testRename() {
        val tag = XMLTag("nome")
        assertEquals("<nome/>", tag.toText)

        tag.updateName("_nome")
        assertEquals("<_nome/>", tag.toText)

        tag.updateName("Nome")
        assertEquals("<Nome/>", tag.toText)

        assertThrows<InvalidNameException> {
            tag.updateName("0nome")
        }

        assertThrows<InvalidNameException> {
            tag.updateName(" nome")
        }

        assertThrows<InvalidNameException> {
            tag.updateName("*nome")
        }

        assertThrows<InvalidNameException> {
            tag.updateName("!nome")
        }
    }

    /**
     * Tests adding and removing child elements from an XMLTag.
     */
    @Test
    fun testAddRemoveChildren() {
        val tag = XMLTag("nome")
        assertEquals("<nome/>", tag.toText)

        tag.addElement(XMLTag("quiz"))
        assertEquals("<nome>\n" +
            "  <quiz/>\n" +
        "</nome>", tag.toText)

        tag.addElement(XMLTag("projeto"))
        assertEquals("<nome>\n" +
            "  <quiz/>\n" +
            "  <projeto/>\n" +
        "</nome>", tag.toText)
    }

    /**
     * Tests attribute manipulation functionalities of XMLTag.
     */
    @Test
    fun testAttributes() {
        val tag = XMLTag("nome")
        assertEquals("<nome/>", tag.toText)

        tag.addAttribute("ects", "6.0")
        assertEquals("<nome ects=\"6.0\"/>", tag.toText)

        tag.addAttribute("codigo", "M4310")
        assertEquals("<nome ects=\"6.0\" codigo=\"M4310\"/>", tag.toText)

        tag.updateAttributeName("ects", "ECTS")
        assertEquals("<nome ECTS=\"6.0\" codigo=\"M4310\"/>", tag.toText)

        tag.updateAttributeValue("codigo", "M1340")
        assertEquals("<nome ECTS=\"6.0\" codigo=\"M1340\"/>", tag.toText)

        tag.removeAttribute("codigo")
        assertEquals("<nome ECTS=\"6.0\"/>", tag.toText)
    }
}