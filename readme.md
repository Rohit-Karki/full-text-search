# Text Search

A simple Kotlin-based text search engine that indexes XML documents and supports keyword search using an inverted index.

## Features

- Streams and parses XML documents from a directory.
- Removes stop words and tokenizes text for efficient indexing.
- Builds an inverted index for fast keyword search.
- Command-line interface for indexing and searching.

## Project Structure

```
src/
  main/
    kotlin/
      Main.kt
      Tokenizer.kt
      StopWords.kt
      index/
        DocumentStore.kt
        InvertedIndex.kt
        IndexInformation.kt
  test/
build.gradle.kts
settings.gradle.kts
```

## Getting Started

### Prerequisites

- Java 8 or higher
- [Gradle](https://gradle.org/) (or use the included `gradlew` wrapper)
- Kotlin 1.9.x

### Build

```sh
./gradlew build
```

### Run

By default, the main class is `MainKt`. You can run the application with:

```sh
./gradlew run
```

Or, to specify a directory of XML files:

```sh
java -jar build/libs/text_search-1.0-SNAPSHOT.jar <directory_path>
```

The directory should contain XML files with `<feed>` and `<doc>` elements.

## How It Works

1. **Document Loading:**  
   XML files are streamed and parsed into `IndexedDocument` objects using [`DocumentStore`](src/main/kotlin/index/DocumentStore.kt).

2. **Tokenization & Stop Words:**  
   Text is tokenized and cleaned using [`Tokenizer`](src/main/kotlin/Tokenizer.kt) and stop words are removed via [`StopWords`](src/main/kotlin/StopWords.kt).

3. **Indexing:**  
   The inverted index is built with [`InvertedIndex`](src/main/kotlin/index/InvertedIndex.kt).

4. **Searching:**  
   Enter a search query and the engine returns matching document IDs.

## Dependencies

- [Jackson XML](https://github.com/FasterXML/jackson-dataformat-xml) for XML parsing

See [build.gradle.kts](build.gradle.kts) for details.

## License

This project is licensed under the Apache 2.0 License.