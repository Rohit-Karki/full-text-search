object Tokenizer {
    fun tokenize(text: String): List<String> {
        // Step 1: Normalize and split the text
        val rawTokens = text.lowercase()
            .replace(Regex("[^a-z\\s]"), "") // Remove non-alphabetic characters
            .split(Regex("\\s+")) // Split by whitespace

        // Step 2 & 3: Filter out empty strings
        val filteredEmptyString = rawTokens.filter { it.isNotBlank() }
        return StopWords.removeStopWords(filteredEmptyString)
    }
}