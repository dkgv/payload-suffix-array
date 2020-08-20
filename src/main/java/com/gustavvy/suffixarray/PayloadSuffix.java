package com.gustavvy.suffixarray;

/**
 * PayloadSuffix.java
 *
 * @author Gustav V. Y.
 */
public class PayloadSuffix implements Comparable<PayloadSuffix> {

	private final String word;
	private final int payloadIndex;

	public PayloadSuffix(String word, int payloadIndex) {
		this.word = word;
		this.payloadIndex = payloadIndex;
	}

	public int getPayloadIndex() {
		return payloadIndex;
	}

	public String getWord() {
		return word;
	}

	@Override
	public int compareTo(PayloadSuffix o) {
		return word.compareTo(o.word);
	}
}
