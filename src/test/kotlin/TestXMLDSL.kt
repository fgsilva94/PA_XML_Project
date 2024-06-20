import extensions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests for XML DSL operations using the provided extensions and classes.
 */
class TestXMLDSL {

    /**
     * Sample XML document created using the DSL
     */
    private val xmlDoc = document {
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
     * Test case to verify the creation of the XML document using DSL.
     */
    @Test
    fun testCreate() {
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
     * Test case to select a specific XML element using the '/' operator.
     */
    @Test
    fun testSelect() {
        val fuc = xmlDoc / "plano" / "fuc"

        assertEquals("  <fuc codigo=\"M4310\">\n" +
                "    <nome>Programação Avançada</nome>\n" +
                "    <ects>6.0</ects>\n" +
                "    <avaliacao>\n" +
                "      <componente nome=\"Quizzes\" peso=\"20%\"/>\n" +
                "      <componente nome=\"Projeto\" peso=\"80%\"/>\n" +
                "    </avaliacao>\n" +
                "  </fuc>", fuc.toText)
    }

    /**
     * Test case to add an attribute to a specific XML element using the '+' operator.
     */
    @Test
    fun testAddAttribute() {
        xmlDoc / "plano" / "fuc" + ("attrTest" to "valTest")

        val fuc = xmlDoc / "plano" / "fuc"

        assertEquals("  <fuc codigo=\"M4310\" attrTest=\"valTest\">\n" +
                "    <nome>Programação Avançada</nome>\n" +
                "    <ects>6.0</ects>\n" +
                "    <avaliacao>\n" +
                "      <componente nome=\"Quizzes\" peso=\"20%\"/>\n" +
                "      <componente nome=\"Projeto\" peso=\"80%\"/>\n" +
                "    </avaliacao>\n" +
                "  </fuc>", fuc.toText)
    }
}