package index

import Tokenizer
import java.util.*
import kotlin.collections.HashSet

object InvertedIndex {
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

    fun search(queryText: String): List<HashSet<Int>> {
        val transformedQuery = queryText.lowercase()
        val tokens = Tokenizer.tokenize(transformedQuery)
        val matchedDocuments = mutableListOf<HashSet<Int>>()
        for (token in tokens){
            matchedDocuments.add(index[token]!!)
        }
        return matchedDocuments
    }
}