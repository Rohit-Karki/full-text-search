package index

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class IndexData(
    val index: Map<String, Set<Int>>
)

object IndexPersistence {
    private const val INDEX_FILE = "search_index.json"
    
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    
    fun saveIndex(index: Map<String, HashSet<Int>>, filePath: String = INDEX_FILE) {
        try {
            val indexData = IndexData(
                index = index.mapValues { it.value.toSet() }
            )
            val jsonString = json.encodeToString(indexData)
            File(filePath).writeText(jsonString)
            println("✓ Index saved to disk: $filePath")
        } catch (e: Exception) {
            println("Error saving index: ${e.message}")
        }
    }
    
    fun loadIndex(filePath: String = INDEX_FILE): Map<String, HashSet<Int>>? {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                println("No existing index found at $filePath")
                return null
            }
            
            val jsonString = file.readText()
            val indexData = json.decodeFromString<IndexData>(jsonString)
            val result = indexData.index.mapValues { it.value.toHashSet() }
            println("✓ Index loaded from disk: $filePath (${result.size} tokens)")
            result
        } catch (e: Exception) {
            println("Error loading index: ${e.message}")
            null
        }
    }
    
    fun indexExists(filePath: String = INDEX_FILE): Boolean {
        return File(filePath).exists()
    }
}
