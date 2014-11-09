package koopa.core.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is the main data item generated by Koopa. They represent pieces of text,
 * and can carry some metadata in the form of tags.
 * <p>
 * Tags themselves can be anything; though we recommend using enums.
 * <p>
 * <b>Trying to keep this class immutable.</b>
 */
public class Token implements Data {

	private final String text;
	private final List<Range> ranges;
	private final Set<Object> tags;

	public Token(String text, Position start, Position end, Object... tags) {
		assert(start != null);
		assert(end != null);
		
		this.text = text;

		List<Range> ranges = new ArrayList<Range>(1);
		ranges.add(new Range(start, end));

		this.ranges = Collections.unmodifiableList(ranges);
		this.tags = Collections.unmodifiableSet(new HashSet<Object>(Arrays
				.asList(tags)));
	}

	public Token(String text, List<Range> ranges, Object... tags) {
		assert(ranges != null);
		assert(ranges.size() > 0);
		
		this.text = text;
		this.ranges = Collections.unmodifiableList(ranges);
		this.tags = Collections.unmodifiableSet(new HashSet<Object>(Arrays
				.asList(tags)));
	}

	public Token(String text, List<Range> ranges, Set<Object> tags) {
		assert(ranges != null);
		assert(ranges.size() > 0);
		
		this.text = text;

		this.ranges = Collections.unmodifiableList(ranges);
		this.tags = Collections.unmodifiableSet(tags);
	}

	/**
	 * Creates a new token which is equivalent to the composition of the given
	 * ones, except for their tags.
	 * <p>
	 * <b>The tags of the original tokens are not aggregated.</b> Instead you
	 * can specify whatever tags the new token should have as extra parameters.
	 * <p>
	 * The {@linkplain Range}s of the original tokens, however, do get
	 * aggregated into the new one.
	 */
	public Token(List<Token> tokens, Object... tags) {
		StringBuffer buffer = new StringBuffer();

		for (Token token : tokens)
			buffer.append(token.getText());

		this.text = buffer.toString();

		List<Range> ranges = new ArrayList<Range>();
		for (Token token : tokens)
			ranges.addAll(token.ranges);
		
		assert(ranges.size() > 0);

		this.ranges = Collections.unmodifiableList(ranges);
		this.tags = Collections.unmodifiableSet(new HashSet<Object>(Arrays
				.asList(tags)));
	}

	public String getText() {
		return this.text;
	}

	public int getLength() {
		return text.length();
	}

	public Position getStart() {
		return ranges.get(0).getStart();
	}

	public Position getEnd() {
		return ranges.get(ranges.size() - 1).getEnd();
	}

	public Set<Object> getTags() {
		return tags;
	}

	public boolean hasTag(Object tag) {
		return tags.contains(tag);
	}

	public int tagCount() {
		return tags.size();
	}

	public List<Range> getRanges() {
		return ranges;
	}

	/**
	 * Creates a new token which is a copy of this one, with the addition of the
	 * given tags. If there are no tags given, returns <code>this</code>
	 * instead.
	 */
	public Token withTags(Object... additionalTags) {
		if (additionalTags.length == 0)
			return this;

		Set<Object> newTags = new HashSet<Object>(tags);
		newTags.addAll(Arrays.asList(additionalTags));
		return new Token(text, ranges, newTags);
	}

	/**
	 * Creates a new token which is a copy of this one, with the exception of
	 * the given tags. If there are no tags given, returns <code>this</code>
	 * instead.
	 */
	public Token withoutTags(Object... theseTags) {
		if (theseTags.length == 0)
			return this;

		Set<Object> newTags = new HashSet<Object>(tags);
		newTags.removeAll(Arrays.asList(theseTags));
		return new Token(text, ranges, newTags);
	}

	/**
	 * Creates a new token which is a copy of this one, minus one tag (if
	 * present), and with the addition of the other.
	 */
	public Token replacingTag(Object oldTag, Object newTag) {
		Set<Object> newTags = new HashSet<Object>(tags);
		newTags.remove(oldTag);
		newTags.add(newTag);
		return new Token(text, ranges, newTags);
	}

	@Override
	public String toString() {
		String s = "[" + getStart() + "|" + text + "|" + getEnd() + "]";

		if (!tags.isEmpty()) {
			for (Object tag : tags)
				s += " @" + tag;
		}

		return s;
	}
}
