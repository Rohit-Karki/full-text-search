package index

import Tokenizer
import java.io.Serializable
import java.util.*

object InvertedIndex: Serializable {
    private fun readResolve(): Any = InvertedIndex
    private val index: MutableMap<String, HashSet<Int>> = HashMap()

    fun buildIndex(documents: List<IndexedDocument>){
        val tokenizer = Tokenizer
        documents.forEach { doc ->
            // Use the tokenizer to get clean tokens
            val tokens = tokenizer.tokenize(doc.abstractText!!).toSet()
            if (doc.id < 2){
                println(tokens)
            }
            tokens.forEach { token ->
                index.getOrPut(token) { hashSetOf() }.add(doc.id)
            }
            if (doc.id < 2){
                println(index)
            }
        }
    }

    fun search(queryText: String): IndexedDocument {
        val transformedQuery = queryText.lowercase()
        println(transformedQuery)
        val documentId = index.getOrDefault(transformedQuery, 1)
        println(documentId)
        return DocumentStore.documents[documentId as Int]
    }
}