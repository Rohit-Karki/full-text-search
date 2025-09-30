/** Tokenizes a string into an array of tokens. For example, the string "Hello, world!" would be tokenized into
 * ["Hello", "world"].
 */
object Tokenizer {
    fun tokenize(text: String): List<String> {
        // Step 1: Normalize and split the text
        val rawTokens = text.lowercase()
            .replace(Regex("[^a-z\\s]"), "") // Remove non-alphabetic characters
            .split(Regex("\\s+")) // Split by whitespace

        // Step 2 & 3: Filter out empty strings
        val listafterfilteredEmptyString = rawTokens.filter { it.isNotBlank() }
        // Remove stop words from the list
        return StopWords.removeStopWords(listafterfilteredEmptyString)
    }
}