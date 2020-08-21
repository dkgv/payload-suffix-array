package com.gustavvy.suffixarray;

import java.util.*;

/**
 * PayloadSuffixArray.java
 *
 * @author Gustav V. Y.
 */
public class PayloadSuffixArray<T> {

	private int textLen = 0;
	private PayloadSuffix[] suffixes;
	private final ArrayList<T> payloads = new ArrayList<>();
	private final ArrayList<String> words = new ArrayList<>();
	private final SuffixArrayConfig config;
	private final HashSet<String> uniqueWords = new HashSet<>();

	protected PayloadSuffixArray(SuffixArrayConfig config) {
		this.config = config;
	}

	public static <T> PayloadSuffixArrayBuilder<T> builder() {
		return new PayloadSuffixArrayBuilder<>();
	}

	private void buildSuffixArray() {
		suffixes = new PayloadSuffix[textLen];
		int k = 0;
		for (int i = 0; i < words.size(); i++) {
			final String word = words.get(i);
			for (int j = 0; j < word.length(); j++) {
				suffixes[k++] = new PayloadSuffix(word.substring(j), i);
			}
		}
		Arrays.sort(suffixes);
		uniqueWords.clear();
	}

	public void addWord(String word, final T payload) {
		if (word.isEmpty()) {
			return;
		}

		if (config.isCaseInsensitive()) {
			word = word.toLowerCase();
		}

		if (config.isDisallowDuplicates() && !uniqueWords.add(word)) {
			return;
		}

		textLen += word.length();
		words.add(word);
		payloads.add(payload);
	}

	private int strncmp(final String s1, final String s2, final int n) {
		final int len1 = Math.min(n, s1.length());
		final int len2 = Math.min(n, s2.length());
		return s1.substring(0, len1).compareTo(s2.substring(0, len2));
	}

	public PayloadSuffix[] getSuffixes() {
		return suffixes;
	}

	public boolean contains(final String pattern) {
		return binarySearch(pattern, true) != -1;
	}

	public List<T> findAll(final String pattern) {
		final int firstIndex = binarySearch(pattern, true);

		if (firstIndex == -1) {
			return new ArrayList<>();
		}

		final int lastIndex = binarySearch(pattern, false);

		if (firstIndex == 0 && lastIndex == textLen - 1) {
			return payloads;
		}

		ArrayList<T> payloads = new ArrayList<>();
		for (int i = firstIndex; i < lastIndex + 1; i++) {
			payloads.add(suffixToPayload(i));
		}

		return payloads;
	}

	public T findFirst(final String pattern) {
		final int first = binarySearch(pattern, true);
		return first != -1 ? suffixToPayload(first) : null;
	}

	private T suffixToPayload(final int index) {
		return payloads.get(suffixes[index].getPayloadIndex());
	}

	private int binarySearch(String pattern, boolean first) {
		if (config.isCaseInsensitive()) {
			pattern = pattern.toLowerCase();
		}

		final int m = pattern.length();
		int l = 0, r = textLen - 1;
		int index = -1;
		while (l <= r) {
			final int mid = (l + r) / 2;
			final int res = strncmp(pattern, suffixes[mid].getWord(), m);
			if (res == 0) {
				index = mid;
				if (first) {
					r = mid - 1;
				} else {
					l = mid + 1;
				}
			} else if (res < 0) {
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return index;
	}

	public static class PayloadSuffixArrayBuilder<T> {

		private final SuffixArrayConfig config = new SuffixArrayConfig();
		private final PayloadSuffixArray<T> suffixArray = new PayloadSuffixArray<>(config);

		public PayloadSuffixArrayBuilder<T> disallowDuplicates() {
			config.setDisallowDuplicates(true);
			return this;
		}

		public PayloadSuffixArrayBuilder<T> addWord(final String word, final T payload) {
			suffixArray.addWord(word, payload);
			return this;
		}

		public PayloadSuffixArrayBuilder<T> ignoreCase() {
			config.setCaseInsensitive(true);
			return this;
		}

		public PayloadSuffixArrayBuilder<T> addWord(final List<String> words, final List<T> payloads) {
			assert words.size() == payloads.size();

			for (int i = 0; i < words.size(); i++) {
				addWord(words.get(i), payloads.get(i));
			}

			return this;
		}

		public PayloadSuffixArray<T> build() {
			suffixArray.buildSuffixArray();
			return suffixArray;
		}
	}
}
