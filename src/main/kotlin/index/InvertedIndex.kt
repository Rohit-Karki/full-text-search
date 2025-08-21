package index

import Tokenizer
import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

object InvertedIndex: Serializable {
    private fun readResolve(): Any = InvertedIndex
    private val index: MutableMap<String, HashSet<Int>> = HashMap()

    fun buildIndex(documents: List<IndexedDocument>){
        val tokenizer = Tokenizer
        documents.forEach { doc ->
            // Use the tokenizer to get clean tokens
            val tokens = tokenizer.tokenize(doc.abstractText!!).toSet()
            tokens.forEach { token ->
                index.getOrPut(token) { hashSetOf() }.add(doc.id)
            }
        }
    }

    fun search(queryText: String): Serializable {
        val transformedQuery = queryText.lowercase()
        println(transformedQuery)

        return index.getOrDefault(transformedQuery, 1)
    }
}