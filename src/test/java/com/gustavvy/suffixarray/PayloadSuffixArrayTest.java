package com.gustavvy.suffixarray;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * PayloadSuffixArrayTest.java
 *
 * @author Gustav V. Y.
 */
public class PayloadSuffixArrayTest {

	@Test
	public void testCreateEmpty() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder().build();
		Assert.assertTrue(suffixArray.getSuffixes().isEmpty());
	}

	@Test
	public void testSuffixOrder() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.addWord("bam", "")
				.build();
		List<PayloadSuffix> suffixes = suffixArray.getSuffixes();
		Assert.assertEquals("am", suffixes.get(0).getWord());
		Assert.assertEquals("bam", suffixes.get(1).getWord());
		Assert.assertEquals("m", suffixes.get(2).getWord());
	}

	@Test
	public void testDisallowDuplicates() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.setDisallowDuplicates(true)
				.addWord("bam", "")
				.addWord("bam", "")
				.build();
		List<PayloadSuffix> suffixes = suffixArray.getSuffixes();
		Assert.assertEquals(3, suffixes.size());
	}

	@Test
	public void testCaseInsensitive() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.ignoreCase()
				.addWord("BAM", "")
				.build();
		Assert.assertTrue(suffixArray.contains("bam"));
	}

	@Test
	public void testNoCaseInsensitive() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.addWord("BAM", "")
				.build();
		Assert.assertFalse(suffixArray.contains("bam"));
	}

	@Test
	public void testContains() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.addWord("bam", "")
				.build();
		Assert.assertTrue(suffixArray.contains("bam"));
		Assert.assertTrue(suffixArray.contains("am"));
		Assert.assertTrue(suffixArray.contains("ba"));
	}

	@Test
	public void testFindFirst() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.addWord("bam", "1")
				.addWord("bam", "2")
				.build();
		Assert.assertEquals("1", suffixArray.findFirst("bam"));
	}

	@Test
	public void testFindAll() {
		final PayloadSuffixArray<String> suffixArray = PayloadSuffixArray.<String>builder()
				.addWord("bam", "1")
				.addWord("bam", "2")
				.build();
		ArrayList<String> all = suffixArray.findAll("bam");
		Assert.assertEquals("1", all.get(0));
		Assert.assertEquals("2", all.get(1));
	}
}
