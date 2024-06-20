import entities.*
import extensions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Test suite for XMLDocument and related XML elements.
 */
class TestXMLDocument {

    /**
     * Helper function to create an example XML document for testing.
     * @return An XMLDocument instance representing a study plan.
     */
    private fun exampleXmlDoc() =
        document {
            tag("plano") {
                textTag("curso", "Mestrado em Engenharia Informática")
                tag("fuc", mapOf("codigo" to "M4310")) {
                    textTag("nome", "Programação Avançada")
                    textTag("ects", "6.0")
                    tag("avaliacao") {
                        tag("componente", mapOf("nome" to "Quizzes", "peso" to "20%")) {}
                        tag("componente", mapOf("nome" to "Projeto", "peso" to "80%")) {}
                    }
                }
                tag("fuc", mapOf("codigo" to "03782")) {
                    textTag("nome", "Dissertação")
                    textTag("ects", "42.0")
                    tag("avaliacao") {
                        tag("componente", mapOf("nome" to "Dissertação", "peso" to "60%")) {}
                        tag("componente", mapOf("nome" to "Apresentação", "peso" to "20%")) {}
                        tag("componente", mapOf("nome" to "Discussão", "peso" to "20%")) {}
                    }
                }
            }
        }

    /**
     * Tests the conversion of an XML document to its text representation.
     */
    @Test
    fun testToText() {
        val xmlDoc = exampleXmlDoc()

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<plano>\n" +
            "  <curso>Mestrado em Engenharia Informática</curso>\n" +
            "  <fuc codigo=\"M4310\">\n" +
            "    <nome>Programação Avançada</nome>\n" +
            "    <ects>6.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente nome=\"Quizzes\" peso=\"20%\"/>\n" +
            "      <componente nome=\"Projeto\" peso=\"80%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "  <fuc codigo=\"03782\">\n" +
            "    <nome>Dissertação</nome>\n" +
            "    <ects>42.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente nome=\"Dissertação\" peso=\"60%\"/>\n" +
            "      <componente nome=\"Apresentação\" peso=\"20%\"/>\n" +
            "      <componente nome=\"Discussão\" peso=\"20%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "</plano>", xmlDoc.toText)
    }

    /**
     * Tests adding and removing elements from an XML document.
     */
    @Test
    fun testXMLDocumentAddRemoveElement() {
        val xmlDoc = XMLDocument()
        assertEquals(null, xmlDoc.child)

        val plano = XMLTag("plano")
        xmlDoc.addElement(plano)
        assertEquals(plano, xmlDoc.child)

        xmlDoc.removeElement()
        assertEquals(null, xmlDoc.child)

        val nome1 = XMLTextTag("nome", "Programação Avançada")
        xmlDoc.addElement(nome1)
        assertEquals(nome1, xmlDoc.child)
    }

    /**
     * Tests adding and removing elements from an XML tag.
     */
    @Test
    fun testXMLTagAddRemoveElement() {
        val plano = XMLTag("plano")
        val curso = XMLTag("curso")
        val fuc1 = XMLTag("fuc")
        val fuc2 = XMLTag("fuc")

        assertEquals(listOf<XMLElement>(), plano.children)

        plano.addElement(curso)
        plano.addElement(fuc1)
        plano.addElement(fuc2)
        assertEquals(listOf(curso, fuc1, fuc2), plano.children)

        plano.removeElement("fuc")
        plano.removeElement("curso")
        assertEquals(listOf(fuc2), plano.children)

        plano.removeElement("fuc")
        assertEquals(listOf<XMLElement>(), plano.children)
    }

    /**
     * Tests adding, removing, and updating attributes in an XML document.
     */
    @Test
    fun testXMLDocumentAddRemoveUpdateAttributes() {
        val xmlDoc = XMLDocument()
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", xmlDoc.toText)

        xmlDoc.updateAttributeValue("version", "1.1")
        assertEquals("<?xml version=\"1.1\" encoding=\"UTF-8\"?>", xmlDoc.toText)

        xmlDoc.updateAttributeName("version", "versao")
        assertEquals("<?xml versao=\"1.1\" encoding=\"UTF-8\"?>", xmlDoc.toText)

        xmlDoc.removeAttribute("versao")
        assertEquals("<?xml encoding=\"UTF-8\"?>", xmlDoc.toText)

        xmlDoc.addAttribute("version", "1.0")
        assertEquals("<?xml encoding=\"UTF-8\" version=\"1.0\"?>", xmlDoc.toText)
    }

    /**
     * Tests adding, removing, and updating attributes in an XML tag.
     */
    @Test
    fun testXMLTagAddRemoveUpdateAttributes() {
        val fuc1 = XMLTag("fuc")
        assertEquals("<fuc/>", fuc1.toText)

        fuc1.addAttribute("nome", "Programação Avançada")
        assertEquals("<fuc nome=\"Programação Avançada\"/>", fuc1.toText)

        fuc1.addAttribute("nota", "19")
        assertEquals("<fuc nome=\"Programação Avançada\" nota=\"19\"/>", fuc1.toText)

        fuc1.updateAttributeValue("nome", "Dissertação")
        assertEquals("<fuc nome=\"Dissertação\" nota=\"19\"/>", fuc1.toText)

        fuc1.updateAttributeName("nome", "title")
        assertEquals("<fuc title=\"Dissertação\" nota=\"19\"/>", fuc1.toText)

        fuc1.removeAttribute("nota")
        assertEquals("<fuc title=\"Dissertação\"/>", fuc1.toText)
    }

    /**
     * Tests the count of XML elements in a document.
     */
    @Test
    fun testElementCount() {
        val xmlDoc = exampleXmlDoc()

        assertEquals(Pair(10, 5), xmlDoc.countXMLElements())
    }

    /**
     * Tests renaming and removing all elements of a specific type in an XML document.
     */
    @Test
    fun testRenameRemoveAllElements() {
        val xmlDoc = exampleXmlDoc()

        xmlDoc.renameAllElements("componente", "component")
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<plano>\n" +
            "  <curso>Mestrado em Engenharia Informática</curso>\n" +
            "  <fuc codigo=\"M4310\">\n" +
            "    <nome>Programação Avançada</nome>\n" +
            "    <ects>6.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <component nome=\"Quizzes\" peso=\"20%\"/>\n" +
            "      <component nome=\"Projeto\" peso=\"80%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "  <fuc codigo=\"03782\">\n" +
            "    <nome>Dissertação</nome>\n" +
            "    <ects>42.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <component nome=\"Dissertação\" peso=\"60%\"/>\n" +
            "      <component nome=\"Apresentação\" peso=\"20%\"/>\n" +
            "      <component nome=\"Discussão\" peso=\"20%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "</plano>", xmlDoc.toText)

        xmlDoc.removeAllElements("component")
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<plano>\n" +
            "  <curso>Mestrado em Engenharia Informática</curso>\n" +
            "  <fuc codigo=\"M4310\">\n" +
            "    <nome>Programação Avançada</nome>\n" +
            "    <ects>6.0</ects>\n" +
            "    <avaliacao/>\n" +
            "  </fuc>\n" +
            "  <fuc codigo=\"03782\">\n" +
            "    <nome>Dissertação</nome>\n" +
            "    <ects>42.0</ects>\n" +
            "    <avaliacao/>\n" +
            "  </fuc>\n" +
            "</plano>", xmlDoc.toText)
    }

    /**
     * Test case to verify renaming attributes of XML elements.
     */
    @Test
    fun testRenameRemoveAllAttributes() {
        val xmlDoc = exampleXmlDoc()
        xmlDoc.renameAllAttributes("componente", "nome", "title")
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<plano>\n" +
            "  <curso>Mestrado em Engenharia Informática</curso>\n" +
            "  <fuc codigo=\"M4310\">\n" +
            "    <nome>Programação Avançada</nome>\n" +
            "    <ects>6.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente title=\"Quizzes\" peso=\"20%\"/>\n" +
            "      <componente title=\"Projeto\" peso=\"80%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "  <fuc codigo=\"03782\">\n" +
            "    <nome>Dissertação</nome>\n" +
            "    <ects>42.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente title=\"Dissertação\" peso=\"60%\"/>\n" +
            "      <componente title=\"Apresentação\" peso=\"20%\"/>\n" +
            "      <componente title=\"Discussão\" peso=\"20%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "</plano>", xmlDoc.toText)

        xmlDoc.removeAllAttributes("componente", "title")
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<plano>\n" +
            "  <curso>Mestrado em Engenharia Informática</curso>\n" +
            "  <fuc codigo=\"M4310\">\n" +
            "    <nome>Programação Avançada</nome>\n" +
            "    <ects>6.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente peso=\"20%\"/>\n" +
            "      <componente peso=\"80%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "  <fuc codigo=\"03782\">\n" +
            "    <nome>Dissertação</nome>\n" +
            "    <ects>42.0</ects>\n" +
            "    <avaliacao>\n" +
            "      <componente peso=\"60%\"/>\n" +
            "      <componente peso=\"20%\"/>\n" +
            "      <componente peso=\"20%\"/>\n" +
            "    </avaliacao>\n" +
            "  </fuc>\n" +
            "</plano>", xmlDoc.toText)
    }

    /**
     * Test suite for XPath-like queries on XML structure.
     */
    @Test
    fun XPathTest() {
        val xmlDoc = XMLDocument()
        val plano = XMLTag("plano")
        val curso = XMLTextTag("curso", "Mestrado em Engenharia Informática")
        val fuc1 = XMLTag("fuc")
        val nome1 = XMLTextTag("nome", "Programação Avançada")
        val ects1 = XMLTextTag("ects", "6.0")
        val avaliacao1 = XMLTag("avaliacao")
        val componente1 = XMLTag("componente")
        val componente2 = XMLTag("componente")
        val fuc2 = XMLTag("fuc")
        val nome2 = XMLTextTag("nome", "Dissertação")
        val ects2 = XMLTextTag("ects", "42.0")
        val avaliacao2 = XMLTag("avaliacao")
        val componente3 = XMLTag("componente")
        val componente4 = XMLTag("componente")
        val componente5 = XMLTag("componente")

        xmlDoc.addElement(plano)

        plano.addElement(curso)
        plano.addElement(fuc1)
        plano.addElement(fuc2)

        fuc1.addElement(nome1)
        fuc1.addElement(ects1)
        fuc1.addElement(avaliacao1)

        avaliacao1.addElement(componente1)
        avaliacao1.addElement(componente2)

        fuc2.addElement(nome2)
        fuc2.addElement(ects2)
        fuc2.addElement(avaliacao2)

        avaliacao2.addElement(componente3)
        avaliacao2.addElement(componente4)
        avaliacao2.addElement(componente5)

        assertEquals(
            listOf(componente1, componente2, componente3, componente4, componente5),
            xmlDoc.xPath("fuc/avaliacao/componente")
        )

        assertEquals(
            listOf(fuc1, fuc2),
            xmlDoc.xPath("/plano/fuc")
        )

        assertEquals(
            listOf(nome1, nome2),
            xmlDoc.xPath("fuc/nome")
        )

        assertEquals(
            listOf(avaliacao1, avaliacao2),
            xmlDoc.xPath("/plano/fuc/avaliacao")
        )

        assertEquals(
            emptyList<XMLElement>(),
            xmlDoc.xPath("/plano/ects")
        )

        assertEquals(
            emptyList<XMLElement>(),
            xmlDoc.xPath("ects/componente")
        )

        assertEquals(
            listOf(nome1, nome2),
            xmlDoc.xPath("nome")
        )
    }
}