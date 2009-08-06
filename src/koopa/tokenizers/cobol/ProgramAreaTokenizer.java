package koopa.tokenizers.cobol;


import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import koopa.tokenizers.Tokenizer;
import koopa.tokenizers.cobol.tags.AreaTag;
import koopa.tokenizers.util.ThreadedTokenizerBase;
import koopa.tokens.BasicToken;
import koopa.tokens.Position;

public class ProgramAreaTokenizer extends ThreadedTokenizerBase implements
		Tokenizer {
	// We use a PushBackReader so that we may peek at the next character without
	// fully consuming it. This makes our own logic that much simpler.
	private PushbackReader reader = null;

	// This defines the maximum lookahead (peek) possible.
	private static final int MAX_PUSHBACK = 2;

	// TODO Make an enum ?
	public static final int FIXED = 1;
	// TODO Test freeflow mode.
	public static final int FREE = 2;

	private int mode = FIXED;

	private int linenumber = 1;
	private int positionInFile = 1;
	private int positionInLine = 1;

	private Position start = null;
	private Position end = null;
	private StringBuffer buffer = null;

	public ProgramAreaTokenizer(Reader reader) {
		super();

		assert (reader != null);

		this.reader = new PushbackReader(reader, MAX_PUSHBACK);
		this.buffer = new StringBuffer();
	}

	protected void tokenize() throws IOException {
		while (!hasQuit()) {
			if (peek() == -1) {
				// End of file => stop lexing.
				break;

			} else if (peek() == '\n') {
				// Newlines take precedence over everything else. If a token
				// holds a newline, it may not hold anything else.
				consume();
				produce(AreaTag.END_OF_LINE);
				newline();

			} else if (peek() == '\r') {
				// Newlines take precedence over everything else. If a token
				// holds a newline, it may not hold anything else.
				consume();
				if (peek() == '\n')
					consume();
				produce(AreaTag.END_OF_LINE);
				newline();

			} else if (mode == FIXED && positionInLine == 1) {
				// Assumption: there is something in the sequence number area
				// other than a newline.
				tokenizeSequenceNumberArea();

			} else if (mode == FIXED && positionInLine == 7) {
				// Assumption: there is something in the indicator area other
				// than a newline.
				tokenizeIndicatorArea();

			} else if (mode == FIXED && positionInLine > 7
					&& positionInLine < 73) {
				// Assumption: there is something in the program text area other
				// than a newline.
				tokenizeProgramTextArea();

			} else if (mode == FIXED && positionInLine == 73) {
				// Assumption: there is something in the identification area
				// other than a newline.
				tokenizeIdentificationArea();

			} else {
				// Unexpected char.
				System.err.println("Unexpected char at " + markStart());
				System.exit(1);
			}
		}
	}

	private void tokenizeProgramTextArea() throws IOException {
		// Program text area.
		while (positionInLine < 73) {
			final int c = peek();
			if (c == -1 || c == '\r' || c == '\n') {
				break;

			} else {
				consume();
			}
		}

		produce(AreaTag.PROGRAM_TEXT_AREA);
	}

	private void tokenizeIdentificationArea() throws IOException {
		// Identification area.
		while (true) {
			int c = peek();
			if (c == -1) {
				break;

			} else if (c != '\r' && c != '\n') {
				consume();

			} else {
				break;
			}
		}

		produce(AreaTag.IDENTIFICATION_AREA);
	}

	private void tokenizeIndicatorArea() throws IOException {
		// Indicator area.
		int c = peek();
		if (c != -1) {
			consume();
			produce(AreaTag.INDICATOR_AREA);
		}
	}

	private void tokenizeSequenceNumberArea() throws IOException {
		// Sequence number area.
		while (positionInLine < 7) {
			int c = peek();
			if (c == -1) {
				break;

			} else if (c != '\r' && c != '\n') {
				consume();

			} else {
				break;
			}
		}

		produce(AreaTag.SEQUENCE_NUMBER_AREA);
	}

	private int peek() throws IOException {
		int c = reader.read();
		if (c != -1)
			reader.unread(c);
		return c;
	}

	private void consume() throws IOException {
		int c = reader.read();

		if (c == -1)
			return;

		if (buffer.length() == 0)
			start = markStart();

		buffer.append((char) c);
		positionInFile += 1;
		positionInLine += 1;
	}

	private void produce(AreaTag type) {
		end = markEnd();
		if (buffer.length() > 0) {
			BasicToken token = new BasicToken(buffer.toString(), start, end);
			token.addTag(type);
			// System.out.println(token);
			buffer = new StringBuffer();

			enqueue(token);
		}
	}

	private void newline() {
		linenumber += 1;
		positionInLine = 1;
	}

	private Position markStart() {
		return new Position(positionInFile, linenumber, positionInLine);
	}

	private Position markEnd() {
		return new Position(positionInFile - 1, linenumber, positionInLine - 1);
	}

	public void quit() {
		quitMe();
	}
}
