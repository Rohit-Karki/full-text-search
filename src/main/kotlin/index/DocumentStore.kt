package index

import com.ryanharter.kotlinx.serialization.xml.Xml
import com.ryanharter.kotlinx.serialization.xml.XmlAttribute
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import java.io.File

@Serializable
data class Document @OptIn(ExperimentalSerializationApi::class) constructor(
    @XmlAttribute val title: String,
    @XmlAttribute val url: String,
    @XmlAttribute val text: String,
    val id: Int
)

object DocumentStore {
    private lateinit var documents: List<Document>

    @OptIn(ExperimentalSerializationApi::class)
    fun loadDocument(directoryPath: String) {
        /*
        * Load the document from the XML, parse it and save it as List<Document>
        * */
        val directory = File(directoryPath)
        val documentList = mutableListOf<Document>()

        directory.walkTopDown().filter { it.isFile && it.extension == "xml" }.forEach { file ->
            try {
                val textString = file.readText()
                val document = Xml.Default.decodeFromString<Document>(textString)
                documentList.add(document)
            } catch (e: Exception) {
                // Handle parsing errors, e.g., log the error
                println("Error parsing XML file: ${file.name}, Error: ${e.message}")
            }
        }
        this.documents = documentList
    }
}