package index

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import java.io.File
import java.io.FileInputStream
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamReader

@JacksonXmlRootElement
data class Document(
    @JacksonXmlProperty(localName = "title")
    val title: String? = null,
    @JacksonXmlProperty(localName = "url")
    val url: String? = null,
    @JacksonXmlProperty(localName = "abstract")
    val abstractText: String? = null,
)
data class IndexedDocument(
    val title: String?,
    val url: String?,
    val abstractText: String?,
    val id: Int
)

fun streamDocs(file: File): MutableList<IndexedDocument> {
    val docs = mutableListOf<IndexedDocument>()
    val factory = XMLInputFactory.newFactory()
    val mapper = XmlMapper.builder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()


    FileInputStream(file).use { fis ->
        val sr: XMLStreamReader = factory.createXMLStreamReader(fis)

        // Move to <feed>
        while (sr.hasNext() && sr.eventType != XMLStreamReader.START_ELEMENT) {
            sr.next()
        }
        if (sr.localName != "feed") throw IllegalStateException("Expected <feed> root")
        var counter = 1
        // Iterate through child elements
        while (sr.hasNext()) {
            sr.next()
            if (sr.eventType == XMLStreamReader.START_ELEMENT && sr.localName == "doc") {
                val doc: Document = mapper.readValue(sr, Document::class.java)
                val indexedDoc = IndexedDocument(doc.title, doc.url, doc.abstractText, counter)
                docs.add(indexedDoc)
                counter++
                // after readValue, sr is at </doc>
            }
        }
        sr.close()
    }
    return docs
}

object DocumentStore {
    lateinit var documents: List<IndexedDocument>

    fun loadDocument(directoryPath: String) {
        /*
        * Load the document from the XML, parse it and save it as List<Document>
        * */
        val directory = File(directoryPath)
        val documentList = mutableListOf<IndexedDocument>()

        directory.walkTopDown().filter { it.isFile && it.extension == "xml" }.forEach { file ->
            try {
                println("Streaming parse: ${file.name}")
                val documentsFromFile = streamDocs(file)
                documentList.addAll(documentsFromFile) // Correctly adds documents to the list
                println("Parsed ${documentsFromFile.size} document(s) from ${file.name}")
            } catch (e: Exception) {
                // Handle parsing errors, e.g., log the error
                println("Error parsing XML file: ${file.name}, Error: ${e.message}")
            }
        }
        this.documents = documentList
    }
}