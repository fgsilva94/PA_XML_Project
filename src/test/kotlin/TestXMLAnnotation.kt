import entities.*
import annotations.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Test suite for XML annotation processing.
 */
class TestXMLAnnotation {

    /**
     * Tests the XMLName annotation for class and properties.
     */
    @Test
    fun testXMLNameAnnotation() {
        @XmlName("estudante")
        data class StudentNameTest(
            @XmlName("numero")
            val number: Int,
            @XmlName("nome")
            val name: String,
            @XmlName("idade")
            val age: Int,
            @XmlName("curso")
            val course: String,
            @XmlName("trabalhador")
            val worker: Boolean
        )

        val student = StudentNameTest(121851, "Felipe Silva", 30, "Engenharia Informática", true)
        assertEquals("<estudante>\n" +
            "  <numero>121851</numero>\n" +
            "  <nome>Felipe Silva</nome>\n" +
            "  <idade>30</idade>\n" +
            "  <curso>Engenharia Informática</curso>\n" +
            "  <trabalhador>true</trabalhador>\n" +
            "</estudante>", XMLTranslator.translate(student).toText)
    }

    /**
     * Tests the XMLIgnore annotation for ignoring specific properties.
     */
    @Test
    fun testXMLIgnoreAnnotation() {
        @XmlName("student")
        data class StudentIgnoreTest(
            val number: Int,
            val name: String,
            @XmlIgnore
            val age: Int,
            val course: String,
            @XmlIgnore
            val worker: Boolean
        )

        val student = StudentIgnoreTest(121851, "Felipe Silva", 30, "Engenharia Informática", true)
        assertEquals("<student>\n" +
            "  <number>121851</number>\n" +
            "  <name>Felipe Silva</name>\n" +
            "  <course>Engenharia Informática</course>\n" +
            "</student>", XMLTranslator.translate(student).toText)
    }

    /**
     * Tests the XMLAttribute annotation for adding properties as attributes.
     */
    @Test
    fun testXMLAttributeAnnotation() {
        @XmlName("student")
        data class StudentIgnoreTest(
            @XmlAttribute
            val number: Int,
            val name: String,
            val age: Int,
            val course: String,
            val worker: Boolean
        )

        val student = StudentIgnoreTest(121851, "Felipe Silva", 30, "Engenharia Informática", true)
        assertEquals("<student number=\"121851\">\n" +
            "  <name>Felipe Silva</name>\n" +
            "  <age>30</age>\n" +
            "  <course>Engenharia Informática</course>\n" +
            "  <worker>true</worker>\n" +
            "</student>", XMLTranslator.translate(student).toText)
    }

    /**
     * Tests the XMLString annotation for nested structure and formatted attributes.
     */
    @Test
    fun testXMLStringAnnotation() {
        val comp1 = Componente("Quizzes", 20, 16.0)
        val comp2 = Componente("Projeto", 80, 19.5)
        val comp3 = Componente("Dissertação", 60, 18.0)
        val comp4 = Componente("Apresentação", 20, 16.5)
        val comp5 = Componente("Discussão", 20, 18.0)

        val fuc1 = Fuc("M4310", "Programação Avançada", 6.0, listOf(comp1, comp2))
        val fuc2 = Fuc("03782", "Dissertação", 42.0, listOf(comp3, comp4, comp5))

        val plano = Plano("Mestrado em Egenharia Informática", listOf(fuc1, fuc2))

        assertEquals("<plano>\n" +
            "  <curso>Mestrado em Egenharia Informática</curso>\n" +
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
            "</plano>", XMLTranslator.translate(plano).toText)
    }

    /**
     * Tests the XMLAdapter annotation for transforming XML element names and attributes.
     */
    @Test
    fun testXMLAdapterAnnotation() {
        val student = Student(20190795, "Felipe Silva", 30, "Engenharia Informática", true)

        assertEquals("<STUDENT NUMBER=\"20190795\">\n" +
            "  <NAME>FELIPE SILVA</NAME>\n" +
            "  <AGE>30</AGE>\n" +
            "  <COURSE>ENGENHARIA INFORMÁTICA</COURSE>\n" +
            "  <WORKER>TRUE</WORKER>\n" +
            "</STUDENT>", XMLTranslator.translate(student).toText)
    }
}