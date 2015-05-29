package koopa.core.parsers;

import koopa.core.data.Data;
import koopa.core.data.Marker;
import koopa.core.data.Token;
import koopa.core.sources.Source;
import koopa.core.targets.Target;

/**
 * This is not so much a stream, as a parse in progress. It holds on to all
 * {@linkplain Token}s it consumed from a {@linkplain Source}, and
 * {@linkplain Marker}s which were added by the grammar, until (that part of)
 * the parse has been completed. At that time it forwards the result to the
 * {@linkplain Target}.
 * <p>
 * When the parse is complete (either successfully or not) the
 * {@linkplain ParseStream} will no longer be holding on to any
 * {@linkplain Data} itself. In particular, any {@linkplain Token}s which were
 * consumed but did not match will have been returned to their
 * {@linkplain Source}.
 */
public interface ParseStream {

	/**
	 * Get the next token in the stream.
	 */
	Token forward();

	/**
	 * This inserts a given marker at the current position. When the stream gets
	 * committed the marker will be sent along to the target. When the stream
	 * gets rollbacked the marker will be removed instead.
	 */
	void insert(Marker marker);

	/**
	 * Move the stream back towards where we're just about to see this token.
	 * <p>
	 * We expect tokens to be rewound in the order in which they were given.
	 */
	void rewind(Token token);

	/**
	 * Basically a {@linkplain #forward()}, followed by an immediate
	 * {@linkplain #rewind(Token)}.
	 */
	Token peek();

	/**
	 * This is for tracing purposes. Gives up to five tokens worth of text.
	 */
	String peekMore();

	/**
	 * Bookmark the current position in the stream. This will impact the
	 * behaviour of {@linkplain BasicParseStream#rewind()} and
	 * {@linkplain BasicParseStream#commit()}.
	 */
	void bookmark();

	/**
	 * Moves the stream back towards the last bookmark, or to the last commit.
	 * <p>
	 * Anything that's not a {@linkplain Token} will get removed again.
	 */
	void rewind();

	/**
	 * Commit all {@linkplain Data}s the latest bookmark (in effect removing
	 * it). You won't be able to rewind beyond this point again.
	 * <p>
	 * If there was no bookmark then this will committing the entire stream so
	 * far, which means pushing all tokens to that given {@linkplain Target}.
	 */
	void commit();

	/**
	 * The stack of currently active parsers.
	 */
	ParseStack getStack();
}
