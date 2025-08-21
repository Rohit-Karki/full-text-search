import index.DocumentStore
import index.InvertedIndex
import java.io.File

fun main(args: Array<String>) {
    // Check if a directory path was provided as a command-line argument
    /*if (args.isEmpty()) {
        println("Usage: java -jar YourSearchEngine.jar <directory_path>")
        return
    }*/

    val directoryPath = "C:\\Users\\rohit\\Downloads\\enwiki-latest-abstract1"
    val directory = File(directoryPath)

    if (!directory.exists() || !directory.isDirectory) {
        println("Error: The provided path is not a valid directory.")
        return
    }

    val documentStore = DocumentStore
    documentStore.loadDocument(directoryPath)

    if (documentStore.documents.isEmpty()) {
        println("No documents found in the specified directory.")
        return
    }

    val invertedIndex = InvertedIndex
    invertedIndex.buildIndex(documentStore.documents)
    println("Indexing complete. Indexed ${documentStore.documents.size} documents.")
    
    println("search result is ${invertedIndex.search("Anarchism")}")
}