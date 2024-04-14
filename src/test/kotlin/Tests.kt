import entities.XMLAttribute
import entities.XMLDocument
import entities.XMLTag
import entities.XMLTextTag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
class Tests {
    val XMLDoc = XMLDocument()
    val plano = XMLTag("plano", XMLDoc)
    val curso = XMLTextTag("curso", "Mestrado em Engenharia Informática", plano)
    val fuc1 = XMLTag("fuc", plano)
    val nome1 = XMLTextTag("nome", "Programação Avançada", fuc1)
    val ects1 = XMLTextTag("ects", "6.0", fuc1)
    val avaliacao1 = XMLTag("avaliacao", fuc1)
    val componente1 = XMLTag("componente", avaliacao1)
    val componente2 = XMLTag("componente", avaliacao1)
    val fuc2 = XMLTag("fuc", plano)
    val nome2 = XMLTextTag("nome", "Dissertação", fuc2)
    val ects2 = XMLTextTag("ects", "42.0", fuc2)
    val avaliacao2 = XMLTag("avaliacao", fuc2)
    val componente3 = XMLTag("componente", avaliacao2)
    val componente4 = XMLTag("componente", avaliacao2)
    val componente5 = XMLTag("componente", avaliacao2)

    @Test
    fun testDepth() {
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
    fun textToText() {
        fuc1.attributes.add(XMLAttribute("codigo", "M4310"))
        fuc2.attributes.add(XMLAttribute("codigo", "03782"))
        componente1.attributes.add(XMLAttribute("nome","Quizzes"))
        componente1.attributes.add(XMLAttribute("peso","20%"))
        componente2.attributes.add(XMLAttribute("nome","Projeto"))
        componente2.attributes.add(XMLAttribute("peso","80%"))
        componente3.attributes.add(XMLAttribute("nome","Dissertação"))
        componente3.attributes.add(XMLAttribute("peso","60%"))
        componente4.attributes.add(XMLAttribute("nome","Apresentação"))
        componente4.attributes.add(XMLAttribute("peso","20%"))
        componente5.attributes.add(XMLAttribute("nome","Discussão"))
        componente5.attributes.add(XMLAttribute("peso","20%"))

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

}

