package index

import Tokenizer
import java.util.concurrent.ConcurrentHashMap

/** An in-memory inverted index. */
object InvertedIndex {
    /** The key data structure for the inverted index - a map from token to a bitmap of document IDs. */
    private val index: MutableMap<String, HashSet<Int>> = ConcurrentHashMap()
    /** A map from document ID to document. */
    private val documentIndex: MutableMap<HashSet<Int>, Document> = ConcurrentHashMap()

    /** Add a document to the index. The document is tokenized and each token is added to the index. */
    private fun buildIndex(document: IndexedDocument){
        // Use the tokenizer to get clean tokens
        val tokens =  Tokenizer.tokenize(document.abstractText!!).toSet()
        tokens.forEach { token ->
            index.getOrPut(token) { hashSetOf() }.add(document.id)
        }
    }

    fun buildIndex(documents: List<IndexedDocument>){
        documents.forEach { buildIndex(it) }
    }

    /** Search the index for the given search string. Returns a bitmap of document IDs that contain the search string. */
    fun search(queryText: String): List<HashSet<Int>> {
        val transformedQuery = queryText.lowercase()
        val tokens = Tokenizer.tokenize(transformedQuery)
        val matchedDocuments = mutableListOf<HashSet<Int>>()
        for (token in tokens){
            val docIds = index[token]
            if (docIds != null) {
                matchedDocuments.add(docIds)
            }
        }
        return matchedDocuments
    }

    fun size(): Int {
        return index.size
    }
    
    fun saveToDisk(filePath: String = "search_index.json") {
        IndexPersistence.saveIndex(index, filePath)
    }
    
    fun loadFromDisk(filePath: String = "search_index.json"): Boolean {
        val loadedIndex = IndexPersistence.loadIndex(filePath)
        return if (loadedIndex != null) {
            index.clear()
            index.putAll(loadedIndex)
            true
        } else {
            false
        }
    }
    
    fun clear() {
        index.clear()
    }

}
