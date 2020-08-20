package com.gustavvy.suffixarray;

/**
 * SuffixArrayConfig.java
 *
 * @author Gustav V. Y.
 */
public class SuffixArrayConfig {

	private boolean caseInsensitive = false;
	private boolean disallowDuplicates = false;

	public boolean isCaseInsensitive() {
		return caseInsensitive;
	}

	public void setCaseInsensitive(final boolean caseInsensitive) {
		this.caseInsensitive = caseInsensitive;
	}

	public boolean isDisallowDuplicates() {
		return disallowDuplicates;
	}

	public void setDisallowDuplicates(final boolean disallowDuplicates) {
		this.disallowDuplicates = disallowDuplicates;
	}
}
