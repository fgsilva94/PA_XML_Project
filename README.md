# XML Library

The XML Library provides a fluent API that simplifies the generation and handling of XML structures programmatically.
It also offers a Kotlin-based Domain-Specific Language (DSL) for convenient creation, manipulation, and traversal of XML documents.

## Author

Felipe Silva - 121851

## Features

- **XML Document Creation:** Easily create XML documents using Kotlin DSL syntax.
- **Element Manipulation:** Add, update, and remove XML elements and attributes dynamically.
- **Traversal and Selection:** Navigate through XML structures using XPath-like expressions.
- **Validation:** Ensures XML element and attribute names conform to XML naming rules.
- **Annotations for XML Elements:** Add and manipulate attributes of XML elements easily.

## Installation

To use the XML Library in your Kotlin project, include the following maven dependency:

| Lib                                          | Description                        |
| -------------------------------------------- | ---------------------------------- |
| `org.jetbrains.kotlin:kotlin-reflect:1.9.22` | Kotlin runtime reflection library. |
| `org.junit.jupiter:junit-jupiter:5.10.2`     | JUnit 5 testing framework          |

## Usage

### Creating an XML Document Using DSL

```kotlin
val xmlDoc = document {
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

// <?xml version="1.0" encoding="UTF-8"?>
// <plano>
//   <curso>Mestrado em Engenharia Informática</curso>
//   <fuc codigo="M4310">
//     <nome>Programação Avançada</nome>
//     <ects>6.0</ects>
//     <avaliacao>
//       <componente nome="Quizzes" peso="20%"/>
//       <componente nome="Projeto" peso="80%"/>
//     </avaliacao>
//   </fuc>
//   <fuc codigo="03782">
//     <nome>Dissertação</nome>
//     <ects>42.0</ects>
//     <avaliacao>
//       <componente nome="Dissertação" peso="60%"/>
//       <componente nome="Apresentação" peso="20%"/>
//       <componente nome="Discussão" peso="20%"/>
//     </avaliacao>
//   </fuc>
// </plano>
```

### Creating and adding children XML Elements

```kotlin
val tag = XMLTag("fuc")
tag.addElement(XMLTextTag("nome", "Programação Avançada"))
tag.addElement(XMLTextTag("ects", "6.0"))
tag.addElement(XMLTag("avaliacoes"))

// <fuc>
//   <nome>Programação Avançada</nome>
//   </ects>
//   </avaliacoes>
// </fuc>
```

### Handling XML Text Tags

```kotlin
val textTag = XMLTextTag("nome", "Programação Avançada")
textTag.addAttribute("ects", "6.0")

// <nome ects="6.0">Programação Avançada</nome>
```

### Renaming and Removing Attributes

```kotlin
val textTag = XMLTextTag("nome", "Programação Avançada")
textTag.addAttribute("codigo", "M4310")
// <nome codigo="M4310">Programação Avançada</nome>

textTag.updateAttributeName("codigo", "CODIGO")
// <nome CODIGO="M4310">Programação Avançada</nome>

textTag.removeAttribute("CODIGO")
// <nome>Programação Avançada</nome>
```

### XPath Navigation

```kotlin
val xmlDoc = document {
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

// Gets all componente XML Elements for this path
val components = xmlDoc.xPath("plano/fuc/avaliacao/componente")

// Gets all curso XML Elements for this path
val cursos = xmlDoc.xPath("/plano/curso")
```
