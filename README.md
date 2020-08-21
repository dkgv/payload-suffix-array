# payload-suffix-array
A Java implementation of the [suffix array](https://en.wikipedia.org/wiki/Suffix_array) with support for multiple strings and associated payloads. Locates elements fast using a slightly modified binary search. 

## Example
```java
enum Temperature {
    HOT, COLD, LUKEWARM
}

PayloadSuffixArray<Temperature> suffixArray = PayloadSuffixArray.<Temperature>builder()
        .ignoreCase()
        .disallowDuplicates()
        .addWord("very hot", Temperature.HOT)
        .addWord("very hot", Temperature.HOT) // Ignored because no duplicates
        .addWord("pretty HOT", Temperature.HOT)
        .build();

Temperature hot = suffixArray.findFirst("hot"); // Matches "very hot"
List<Temperature> hotOnes = suffixArray.findAll("hot"); // Matches both "very hot" and "pretty HOT"
```