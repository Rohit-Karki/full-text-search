package index

import Tokenizer
import java.io.Serializable
import java.util.*

class InvertedIndex() : Serializable {
    private val index: MutableMap<String, HashSet<Int>> = HashMap()

    fun buildIndex(documents: List<Document>){
        val tokenizer = Tokenizer()
        documents.forEach { doc ->
            // Use the tokenizer to get clean tokens
            val tokens = tokenizer.tokenize(doc.text).toSet()
            tokens.forEach { token ->
                index.getOrPut(token) { hashSetOf() }.add(doc.id)
            }
        }

    }

    fun search(){

    }
}