import entities.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Tests {
    val XMLDoc = XMLDocument()
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

    fun addElements() {
        XMLDoc.addAttribute("version", "1.0")
        XMLDoc.addAttribute("encoding", "UTF-8")

        XMLDoc.addElement(plano)

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
    }

    fun addAttributes() {
        fuc1.addAttribute("codigo", "M4310")
        fuc2.addAttribute("codigo", "03782")

        componente1.addAttribute("nome", "Quizzes")
        componente1.addAttribute("peso", "20%")

        componente2.addAttribute("nome", "Projeto")
        componente2.addAttribute("peso", "80%")

        componente3.addAttribute("nome", "Dissertação")
        componente3.addAttribute("peso", "60%")

        componente4.addAttribute("nome", "Apresentação")
        componente4.addAttribute("peso", "20%")

        componente5.addAttribute("nome", "Discussão")
        componente5.addAttribute("peso", "20%")
    }

    @Test
    fun testDepth() {
        addElements()

        assertEquals(1, XMLDoc.depth)
        assertEquals(1, plano.depth)
        assertEquals(2, curso.depth)
        assertEquals(2, fuc1.depth)
        assertEquals(3, nome1.depth)
        assertEquals(3, ects1.depth)
        assertEquals(3, avaliacao1.depth)
        assertEquals(4, componente1.depth)
        assertEquals(4, componente2.depth)
        assertEquals(2, fuc2.depth)
        assertEquals(3, nome2.depth)
        assertEquals(3, ects2.depth)
        assertEquals(3, avaliacao2.depth)
        assertEquals(4, componente3.depth)
        assertEquals(4, componente4.depth)
    }

    @Test
    fun testToText() {
        addElements()
        addAttributes()

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
                "</plano>", XMLDoc.toText)
    }

    @Test
    fun testXMLDocumentAddRemoveElement() {
        assertEquals(null, XMLDoc.child)
        XMLDoc.addElement(plano)
        assertEquals(plano, XMLDoc.child)
        XMLDoc.removeElement()
        assertEquals(null, XMLDoc.child)
        XMLDoc.addElement(nome1)
        assertEquals(nome1, XMLDoc.child)
    }

    @Test
    fun testXMLTagAddRemoveElement() {
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

    @Test
    fun testXMLDocumentAddRemoveUpdateAttributes() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", XMLDoc.toText)
        XMLDoc.updateAttributeValue("version", "1.1")
        assertEquals("<?xml version=\"1.1\" encoding=\"UTF-8\"?>", XMLDoc.toText)
        XMLDoc.updateAttributeName("version", "versao")
        assertEquals("<?xml versao=\"1.1\" encoding=\"UTF-8\"?>", XMLDoc.toText)
        XMLDoc.removeAttribute("versao")
        assertEquals("<?xml encoding=\"UTF-8\"?>", XMLDoc.toText)
        XMLDoc.addAttribute("version", "1.0")
        assertEquals("<?xml encoding=\"UTF-8\" version=\"1.0\"?>", XMLDoc.toText)
    }

    @Test
    fun testXMLTagAddRemoveUpdateAttributes() {
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

    @Test
    fun testElementCount() {
        addElements()
        addAttributes()

        assertEquals(Pair(10, 5), XMLDoc.countXMLElements())
    }

    @Test
    fun testRenameRemoveAllElements() {
        addElements()
        addAttributes()

        XMLDoc.renameAllElements("componente", "component")
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
                "</plano>", XMLDoc.toText)
        XMLDoc.removeAllElements("component")
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
                "</plano>", XMLDoc.toText)
    }

    @Test
    fun testRenameRemoveAllAttributes() {
        addElements()
        addAttributes()

        XMLDoc.renameAllAttributes("componente", "nome", "title")

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
                "</plano>", XMLDoc.toText)

        XMLDoc.removeAllAttributes("componente", "title")
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
                "</plano>", XMLDoc.toText)
    }

    @Test
    fun XPathTest() {
        addElements()
        addAttributes()

        assertEquals(
            listOf(componente1, componente2, componente3, componente4, componente5),
            XMLDoc.xPath("fuc/avaliacao/componente")
        )

        assertEquals(
            listOf(fuc1, fuc2),
            XMLDoc.xPath("/plano/fuc")
        )

        assertEquals(
            listOf(nome1, nome2),
            XMLDoc.xPath("fuc/nome")
        )

        assertEquals(
            listOf(avaliacao1, avaliacao2),
            XMLDoc.xPath("/plano/fuc/avaliacao")
        )

        assertEquals(
            emptyList<XMLElement>(),
            XMLDoc.xPath("/plano/ects")
        )

        assertEquals(
            emptyList<XMLElement>(),
            XMLDoc.xPath("ects/componente")
        )

        assertEquals(
            listOf(nome1, nome2),
            XMLDoc.xPath("nome")
        )
    }
}