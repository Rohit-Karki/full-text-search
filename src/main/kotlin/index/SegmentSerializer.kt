import index.InvertedIndex
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

object SegmentSerializer {
    fun save(index: InvertedIndex, file: File) {
        val entries = index.allTerms().map { term ->
            TermEntry(term, index.getPostings(term) ?: emptyList())
        }
        file.writeText(Json.encodeToString(entries))
    }

    fun load(file: File): InvertedIndex {
        val entries: List<TermEntry> = Json.decodeFromString(file.readText())
        val index = InvertedIndex
        for (entry in entries) {
            for (posting in entry.postings) {
                for (position in posting.positions) {
                    index.addTerm(entry.term, posting.docId, position)
                }
            }
        }
        return index
    }
}