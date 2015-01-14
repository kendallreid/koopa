package koopa.cobol.grammar.preprocessing;

import static koopa.cobol.data.tags.SyntacticTag.CHARACTER_STRING;
import static koopa.core.data.tags.AreaTag.PROGRAM_TEXT_AREA;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import koopa.cobol.data.tags.SyntacticTag;
import koopa.core.data.Token;
import koopa.core.data.tags.AreaTag;
import koopa.core.grammars.KoopaGrammar;
import koopa.core.parsers.FutureParser;
import koopa.core.parsers.ParseStream;
import koopa.core.parsers.Parser;

public abstract class CobolPreprocessingBaseGrammar extends KoopaGrammar {
	// ============================================================================
	// Reserved keywords. Based on the list found in Fujitsu Siemens, document
	// U41112-J-Z125-3-76 (p. 67 and on).
	//
	// TODO Make things work without this list.
	// ----------------------------------------------------------------------------

	public static final Set<String> RESERVED_WORDS = new HashSet<String>();

	static {
		RESERVED_WORDS.add("ACCEPT");
		RESERVED_WORDS.add("ACCESS");
		RESERVED_WORDS.add("ACTIVE-CLASS");
		RESERVED_WORDS.add("ADD");
		RESERVED_WORDS.add("ADDRESS");
		RESERVED_WORDS.add("ADVANCING");
		RESERVED_WORDS.add("AFTER");
		RESERVED_WORDS.add("ALL");
		RESERVED_WORDS.add("ALLOCATE");
		RESERVED_WORDS.add("ALPHABET");
		RESERVED_WORDS.add("ALPHABETIC");
		RESERVED_WORDS.add("ALPHABETIC-LOWER");
		RESERVED_WORDS.add("ALPHABETIC-UPPER");
		RESERVED_WORDS.add("ALPHANUMERIC");
		RESERVED_WORDS.add("ALPHANUMERIC-EDITED");
		RESERVED_WORDS.add("ALSO");
		RESERVED_WORDS.add("ALTER");
		RESERVED_WORDS.add("ALTERNATE");
		RESERVED_WORDS.add("AND");
		RESERVED_WORDS.add("ANY");
		RESERVED_WORDS.add("ANYCASE");
		RESERVED_WORDS.add("ARE");
		RESERVED_WORDS.add("AREA");
		RESERVED_WORDS.add("AREAS");
		RESERVED_WORDS.add("AS");
		RESERVED_WORDS.add("ASCENDING");
		RESERVED_WORDS.add("ASSIGN");
		RESERVED_WORDS.add("AT");
		RESERVED_WORDS.add("AUTHOR");
		RESERVED_WORDS.add("B-AND");
		RESERVED_WORDS.add("B-NOT");
		RESERVED_WORDS.add("B-OR");
		RESERVED_WORDS.add("B-XOR");
		RESERVED_WORDS.add("BASED");
		RESERVED_WORDS.add("BEFORE");
		// RESERVED_WORDS.add("BEGINNING");
		RESERVED_WORDS.add("BINARY");
		RESERVED_WORDS.add("BINARY-CHAR");
		RESERVED_WORDS.add("BINARY-DOUBLE");
		RESERVED_WORDS.add("BINARY-LONG");
		RESERVED_WORDS.add("BINARY-SHORT");
		RESERVED_WORDS.add("BIT");
		RESERVED_WORDS.add("BLANK");
		RESERVED_WORDS.add("BLOCK");
		RESERVED_WORDS.add("BOOLEAN");
		RESERVED_WORDS.add("BOTTOM");
		RESERVED_WORDS.add("BY");
		RESERVED_WORDS.add("CALL");
		RESERVED_WORDS.add("CANCEL");
		// RESERVED_WORDS.add("CASE");
		RESERVED_WORDS.add("CBL-CTR");
		RESERVED_WORDS.add("CF");
		RESERVED_WORDS.add("CH");
		RESERVED_WORDS.add("CHAINING");
		RESERVED_WORDS.add("CHARACTER");
		RESERVED_WORDS.add("CHARACTERS");
		RESERVED_WORDS.add("CHECKING");
		RESERVED_WORDS.add("CLASS");
		RESERVED_WORDS.add("CLASS-ID");
		RESERVED_WORDS.add("CLOCK-UNITS");
		RESERVED_WORDS.add("CLOSE");
		RESERVED_WORDS.add("CODE");
		RESERVED_WORDS.add("CODE-SET");
		RESERVED_WORDS.add("COL");
		RESERVED_WORDS.add("COLLATING");
		RESERVED_WORDS.add("COLS");
		RESERVED_WORDS.add("COLUMN");
		RESERVED_WORDS.add("COLUMNS");
		RESERVED_WORDS.add("COMMA");
		RESERVED_WORDS.add("COMMIT");
		RESERVED_WORDS.add("COMMON");
		RESERVED_WORDS.add("COMMUNICATION");
		RESERVED_WORDS.add("COMP");
		RESERVED_WORDS.add("COMP-1");
		RESERVED_WORDS.add("COMP-2");
		RESERVED_WORDS.add("COMP-3");
		RESERVED_WORDS.add("COMP-5");
		RESERVED_WORDS.add("COMPUTATIONAL");
		RESERVED_WORDS.add("COMPUTATIONAL-1");
		RESERVED_WORDS.add("COMPUTATIONAL-2");
		RESERVED_WORDS.add("COMPUTATIONAL-3");
		RESERVED_WORDS.add("COMPUTATIONAL-5");
		RESERVED_WORDS.add("COMPUTE");
		RESERVED_WORDS.add("CONDITION");
		RESERVED_WORDS.add("CONFIGURATION");
		// RESERVED_WORDS.add("CONNECT");
		RESERVED_WORDS.add("CONSTANT");
		RESERVED_WORDS.add("CONTAINS");
		RESERVED_WORDS.add("CONTENT");
		RESERVED_WORDS.add("CONTINUE");
		RESERVED_WORDS.add("CONTROL");
		RESERVED_WORDS.add("CONTROLS");
		RESERVED_WORDS.add("CONVERTING");
		RESERVED_WORDS.add("COPY");
		RESERVED_WORDS.add("CORR");
		RESERVED_WORDS.add("CORRESPONDING");
		RESERVED_WORDS.add("COUNT");
		RESERVED_WORDS.add("CREATING");
		RESERVED_WORDS.add("CRT");
		RESERVED_WORDS.add("CURRENCY");
		// RESERVED_WORDS.add("CURRENT");
		// RESERVED_WORDS.add("CURSOR");
		RESERVED_WORDS.add("DATA");
		RESERVED_WORDS.add("DATA-POINTER");
		// RESERVED_WORDS.add("DATABASE-EXCEPTION");
		RESERVED_WORDS.add("DATABASE-KEY");
		RESERVED_WORDS.add("DATABASE-KEY-LONG");
		RESERVED_WORDS.add("DATE");
		RESERVED_WORDS.add("DATE-COMPILED");
		RESERVED_WORDS.add("DATE-WRITTEN");
		RESERVED_WORDS.add("DAY");
		RESERVED_WORDS.add("DAY-OF-WEEK");
		// RESERVED_WORDS.add("DB");
		RESERVED_WORDS.add("DE");
		// RESERVED_WORDS.add("DEBUG-CONTENTS");
		// RESERVED_WORDS.add("DEBUG-ITEM");
		// RESERVED_WORDS.add("DEBUG-LINE");
		// RESERVED_WORDS.add("DEBUG-NAME");
		// RESERVED_WORDS.add("DEBUG-SUB-1");
		// RESERVED_WORDS.add("DEBUG-SUB-2");
		// RESERVED_WORDS.add("DEBUG-SUB-3");
		RESERVED_WORDS.add("DEBUGGING");
		RESERVED_WORDS.add("DECIMAL-POINT");
		RESERVED_WORDS.add("DECLARATIVES");
		RESERVED_WORDS.add("DEFAULT");
		RESERVED_WORDS.add("DELETE");
		RESERVED_WORDS.add("DELIMITED");
		RESERVED_WORDS.add("DELIMITER");
		RESERVED_WORDS.add("DEPENDING");
		RESERVED_WORDS.add("DESCENDING");
		RESERVED_WORDS.add("DETAIL");
		RESERVED_WORDS.add("DISABLE");
		RESERVED_WORDS.add("DISC");
		// RESERVED_WORDS.add("DISCONNECT");
		RESERVED_WORDS.add("DISPLAY");
		RESERVED_WORDS.add("DIVIDE");
		RESERVED_WORDS.add("DIVISION");
		RESERVED_WORDS.add("DOWN");
		// RESERVED_WORDS.add("DUPLICATE");
		RESERVED_WORDS.add("DUPLICATES");
		RESERVED_WORDS.add("DYNAMIC");
		RESERVED_WORDS.add("EBCDIC");
		RESERVED_WORDS.add("EC");
		RESERVED_WORDS.add("ELSE");
		// RESERVED_WORDS.add("EMPTY");
		RESERVED_WORDS.add("ENABLE");
		RESERVED_WORDS.add("END");
		RESERVED_WORDS.add("END-ACCEPT");
		RESERVED_WORDS.add("END-ADD");
		RESERVED_WORDS.add("END-CALL");
		RESERVED_WORDS.add("END-COMPUTE");
		RESERVED_WORDS.add("END-DELETE");
		RESERVED_WORDS.add("END-DISPLAY");
		RESERVED_WORDS.add("END-DIVIDE");
		RESERVED_WORDS.add("END-EVALUATE");
		RESERVED_WORDS.add("END-IF");
		RESERVED_WORDS.add("END-INVOKE");
		RESERVED_WORDS.add("END-MULTIPLY");
		RESERVED_WORDS.add("END-OF-PAGE");
		RESERVED_WORDS.add("END-PERFORM");
		RESERVED_WORDS.add("END-READ");
		RESERVED_WORDS.add("END-RECEIVE");
		RESERVED_WORDS.add("END-RETURN");
		RESERVED_WORDS.add("END-REWRITE");
		RESERVED_WORDS.add("END-SEARCH");
		RESERVED_WORDS.add("END-START");
		RESERVED_WORDS.add("END-STRING");
		RESERVED_WORDS.add("END-SUBTRACT");
		RESERVED_WORDS.add("END-UNSTRING");
		RESERVED_WORDS.add("END-WRITE");
		// RESERVED_WORDS.add("ENDING");
		RESERVED_WORDS.add("ENTRY");
		RESERVED_WORDS.add("ENVIRONMENT");
		RESERVED_WORDS.add("EO");
		RESERVED_WORDS.add("EO");
		RESERVED_WORDS.add("EOP");
		RESERVED_WORDS.add("EOP");
		RESERVED_WORDS.add("EQUAL");
		RESERVED_WORDS.add("EQUAL");
		// RESERVED_WORDS.add("ERASE");
		RESERVED_WORDS.add("ERROR");
		// RESERVED_WORDS.add("ESCAPE");
		RESERVED_WORDS.add("EVALUATE");
		RESERVED_WORDS.add("EVERY");
		RESERVED_WORDS.add("EXCEPTION");
		RESERVED_WORDS.add("EXCEPTION-OBJECT");
		// RESERVED_WORDS.add("EXCLUSIVE");
		RESERVED_WORDS.add("EXEC");
		RESERVED_WORDS.add("EXIT");
		RESERVED_WORDS.add("EXTEND");
		RESERVED_WORDS.add("EXTENDED");
		RESERVED_WORDS.add("EXTERNAL");
		RESERVED_WORDS.add("FACTORY");
		// RESERVED_WORDS.add("FALSE");
		RESERVED_WORDS.add("FD");
		// RESERVED_WORDS.add("FETCH");
		RESERVED_WORDS.add("FILE");
		RESERVED_WORDS.add("FILE-CONTROL");
		RESERVED_WORDS.add("FILLER");
		RESERVED_WORDS.add("FINAL");
		// RESERVED_WORDS.add("FIND");
		// RESERVED_WORDS.add("FINISH");
		RESERVED_WORDS.add("FIRST");
		RESERVED_WORDS.add("FLOAT-EXTENDED");
		RESERVED_WORDS.add("FLOAT-LONG");
		RESERVED_WORDS.add("FLOAT-SHORT");
		RESERVED_WORDS.add("FOOTING");
		RESERVED_WORDS.add("FOR");
		RESERVED_WORDS.add("FORMAT");
		RESERVED_WORDS.add("FREE");
		RESERVED_WORDS.add("FROM");
		RESERVED_WORDS.add("FUNCTION");
		RESERVED_WORDS.add("FUNCTION-ID");
		RESERVED_WORDS.add("GENERATE");
		RESERVED_WORDS.add("GET");
		RESERVED_WORDS.add("GIVING");
		RESERVED_WORDS.add("GLOBAL");
		RESERVED_WORDS.add("GO");
		RESERVED_WORDS.add("GOBACK");
		RESERVED_WORDS.add("GREATER");
		RESERVED_WORDS.add("GROUP");
		RESERVED_WORDS.add("GROUP-USAGE");
		RESERVED_WORDS.add("HEADING");
		RESERVED_WORDS.add("HIGH-VALUE");
		RESERVED_WORDS.add("HIGH-VALUES");
		RESERVED_WORDS.add("I-O");
		RESERVED_WORDS.add("I-O-CONTROL");
		RESERVED_WORDS.add("ID");
		RESERVED_WORDS.add("IDENTIFICATION");
		RESERVED_WORDS.add("IF");
		RESERVED_WORDS.add("IGNORING");
		RESERVED_WORDS.add("IN");
		// RESERVED_WORDS.add("INCLUDING");
		RESERVED_WORDS.add("INDEX");
		RESERVED_WORDS.add("INDEXED");
		RESERVED_WORDS.add("INDICATE");
		RESERVED_WORDS.add("INHERITS");
		RESERVED_WORDS.add("INITIAL");
		RESERVED_WORDS.add("INITIALIZE");
		RESERVED_WORDS.add("INITIATE");
		RESERVED_WORDS.add("INPUT");
		RESERVED_WORDS.add("INPUT-OUTPUT");
		RESERVED_WORDS.add("INSPECT");
		RESERVED_WORDS.add("INSTALLATION");
		RESERVED_WORDS.add("INTERFACE");
		RESERVED_WORDS.add("INTERFACE-ID");
		RESERVED_WORDS.add("INTO");
		RESERVED_WORDS.add("INVALID");
		RESERVED_WORDS.add("INVOKE");
		RESERVED_WORDS.add("IS");
		RESERVED_WORDS.add("JUST");
		RESERVED_WORDS.add("JUSTIFIED");
		// RESERVED_WORDS.add("KEEP");
		RESERVED_WORDS.add("KEY");
		RESERVED_WORDS.add("LABEL");
		RESERVED_WORDS.add("LAST");
		// RESERVED_WORDS.add("LEADING");
		RESERVED_WORDS.add("LEFT");
		// RESERVED_WORDS.add("LENGTH");
		RESERVED_WORDS.add("LESS");
		RESERVED_WORDS.add("LIMIT");
		// RESERVED_WORDS.add("LIMITED");
		RESERVED_WORDS.add("LIMITS");
		RESERVED_WORDS.add("LINAGE");
		RESERVED_WORDS.add("LINE");
		// RESERVED_WORDS.add("LINE-COUNTER");
		RESERVED_WORDS.add("LINES");
		RESERVED_WORDS.add("LINKAGE");
		RESERVED_WORDS.add("LOCAL-STORAGE");
		RESERVED_WORDS.add("LOCALE");
		RESERVED_WORDS.add("LOCK");
		RESERVED_WORDS.add("LOW-VALUE");
		RESERVED_WORDS.add("LOW-VALUES");
		// RESERVED_WORDS.add("MASK");
		// RESERVED_WORDS.add("MATCHING");
		// RESERVED_WORDS.add("MEMBER");
		// RESERVED_WORDS.add("MEMBERS");
		// RESERVED_WORDS.add("MEMBERSHIP");
		RESERVED_WORDS.add("MEMORY");
		RESERVED_WORDS.add("MERGE");
		RESERVED_WORDS.add("MESSAGE");
		RESERVED_WORDS.add("METHOD");
		RESERVED_WORDS.add("METHOD-ID");
		RESERVED_WORDS.add("MINUS");
		RESERVED_WORDS.add("MODE");
		// RESERVED_WORDS.add("MODIFY");
		RESERVED_WORDS.add("MODULES");
		RESERVED_WORDS.add("MORE-LABELS");
		RESERVED_WORDS.add("MOVE");
		RESERVED_WORDS.add("MULTIPLE");
		RESERVED_WORDS.add("MULTIPLY");
		RESERVED_WORDS.add("NATIONAL");
		RESERVED_WORDS.add("NATIONAL-EDITED");
		RESERVED_WORDS.add("NATIVE");
		RESERVED_WORDS.add("NEGATIVE");
		RESERVED_WORDS.add("NESTED");
		RESERVED_WORDS.add("NEXT");
		RESERVED_WORDS.add("NO");
		RESERVED_WORDS.add("NOT");
		RESERVED_WORDS.add("NULL");
		RESERVED_WORDS.add("NUMBER");
		RESERVED_WORDS.add("NUMERIC");
		RESERVED_WORDS.add("NUMERIC-EDITED");
		RESERVED_WORDS.add("OBJECT");
		RESERVED_WORDS.add("OBJECT-COMPUTER");
		// RESERVED_WORDS.add("OCCURENCE");
		RESERVED_WORDS.add("OCCURS");
		RESERVED_WORDS.add("OF");
		RESERVED_WORDS.add("OFF");
		RESERVED_WORDS.add("OMITTED");
		RESERVED_WORDS.add("ON");
		RESERVED_WORDS.add("OPEN");
		RESERVED_WORDS.add("OPTIONAL");
		// RESERVED_WORDS.add("OPTIONS");
		RESERVED_WORDS.add("OR");
		RESERVED_WORDS.add("ORDER");
		RESERVED_WORDS.add("ORGANIZATION");
		RESERVED_WORDS.add("OTHER");
		RESERVED_WORDS.add("OUTPUT");
		RESERVED_WORDS.add("OVERFLOW");
		RESERVED_WORDS.add("OVERRIDE");
		// RESERVED_WORDS.add("OWNER");
		RESERVED_WORDS.add("PACKED-DECIMAL");
		RESERVED_WORDS.add("PADDING");
		RESERVED_WORDS.add("PAGE");
		// RESERVED_WORDS.add("PAGE-COUNTER");
		RESERVED_WORDS.add("PERFORM");
		// RESERVED_WORDS.add("PERMANENT");
		RESERVED_WORDS.add("PF");
		RESERVED_WORDS.add("PH");
		RESERVED_WORDS.add("PIC");
		RESERVED_WORDS.add("PICTURE");
		RESERVED_WORDS.add("PLUS");
		RESERVED_WORDS.add("POINTER");
		RESERVED_WORDS.add("POSITION");
		RESERVED_WORDS.add("POSITIVE");
		RESERVED_WORDS.add("PRESENT");
		RESERVED_WORDS.add("PRINT-SWITCH");
		RESERVED_WORDS.add("PRINTING");
		// RESERVED_WORDS.add("PRIOR");
		RESERVED_WORDS.add("PROCEDURE");
		RESERVED_WORDS.add("PROCEED");
		RESERVED_WORDS.add("PROGRAM");
		RESERVED_WORDS.add("PROGRAM-ID");
		RESERVED_WORDS.add("PROGRAM-POINTER");
		RESERVED_WORDS.add("PROPERTY");
		// RESERVED_WORDS.add("PROTECTED");
		RESERVED_WORDS.add("PROTOTYPE");
		RESERVED_WORDS.add("PURGE");
		RESERVED_WORDS.add("QUOTE");
		RESERVED_WORDS.add("QUOTES");
		RESERVED_WORDS.add("RAISE");
		RESERVED_WORDS.add("RAISING");
		// RESERVED_WORDS.add("RANDOM");
		RESERVED_WORDS.add("RD");
		RESERVED_WORDS.add("READ");
		// RESERVED_WORDS.add("READY");
		// RESERVED_WORDS.add("REALM");
		// RESERVED_WORDS.add("REALM-NAME");
		RESERVED_WORDS.add("RECEIVE");
		RESERVED_WORDS.add("RECORD");
		RESERVED_WORDS.add("RECORDING");
		RESERVED_WORDS.add("RECORDS");
		RESERVED_WORDS.add("REDEFINES");
		RESERVED_WORDS.add("REEL");
		RESERVED_WORDS.add("REFERENCE");
		RESERVED_WORDS.add("RELATIVE");
		RESERVED_WORDS.add("RELEASE");
		RESERVED_WORDS.add("REMAINDER");
		RESERVED_WORDS.add("REMOVAL");
		RESERVED_WORDS.add("RENAMES");
		RESERVED_WORDS.add("REPEATED");
		RESERVED_WORDS.add("REPLACE");
		RESERVED_WORDS.add("REPLACING");
		RESERVED_WORDS.add("REPORT");
		RESERVED_WORDS.add("REPORTING");
		RESERVED_WORDS.add("REPORTS");
		RESERVED_WORDS.add("REPOSITORY");
		RESERVED_WORDS.add("RERUN");
		RESERVED_WORDS.add("RESERVE");
		RESERVED_WORDS.add("RESET");
		// RESERVED_WORDS.add("RESULT");
		RESERVED_WORDS.add("RESUME");
		// RESERVED_WORDS.add("RETAINING");
		// RESERVED_WORDS.add("RETRIEVAL");
		RESERVED_WORDS.add("RETRY");
		RESERVED_WORDS.add("RETURN");
		RESERVED_WORDS.add("RETURNING");
		RESERVED_WORDS.add("REVERSED");
		RESERVED_WORDS.add("REWIND");
		RESERVED_WORDS.add("REWRITE");
		RESERVED_WORDS.add("RF");
		RESERVED_WORDS.add("RH");
		RESERVED_WORDS.add("RIGHT");
		// RESERVED_WORDS.add("ROLLBACK");
		RESERVED_WORDS.add("ROUNDED");
		RESERVED_WORDS.add("RUN");
		RESERVED_WORDS.add("SAME");
		RESERVED_WORDS.add("SCREEN");
		RESERVED_WORDS.add("SD");
		RESERVED_WORDS.add("SEARCH");
		RESERVED_WORDS.add("SECTION");
		RESERVED_WORDS.add("SECURITY");
		RESERVED_WORDS.add("SEGMENT-LIMIT");
		RESERVED_WORDS.add("SELECT");
		// RESERVED_WORDS.add("SELECTIVE");
		RESERVED_WORDS.add("SELF");
		RESERVED_WORDS.add("SEND");
		RESERVED_WORDS.add("SENTENCE");
		RESERVED_WORDS.add("SEPARATE");
		RESERVED_WORDS.add("SEQUENCE");
		RESERVED_WORDS.add("SEQUENTIAL");
		RESERVED_WORDS.add("SET");
		// RESERVED_WORDS.add("SET-SELECTION");
		// RESERVED_WORDS.add("SETS");
		RESERVED_WORDS.add("SHARING");
		RESERVED_WORDS.add("SIGN");
		RESERVED_WORDS.add("SIZE");
		RESERVED_WORDS.add("SORT");
		RESERVED_WORDS.add("SORT-MERGE");
		RESERVED_WORDS.add("SORT-TAPE");
		RESERVED_WORDS.add("SORT-TAPES");
		// RESERVED_WORDS.add("SORTED");
		RESERVED_WORDS.add("SOURCE");
		RESERVED_WORDS.add("SOURCE-COMPUTER");
		RESERVED_WORDS.add("SOURCES");
		RESERVED_WORDS.add("SPACE");
		RESERVED_WORDS.add("SPACES");
		RESERVED_WORDS.add("SPECIAL-NAMES");
		RESERVED_WORDS.add("STANDARD");
		RESERVED_WORDS.add("STANDARD-1");
		RESERVED_WORDS.add("STANDARD-2");
		RESERVED_WORDS.add("START");
		RESERVED_WORDS.add("STATUS");
		RESERVED_WORDS.add("STOP");
		// RESERVED_WORDS.add("STORE");
		RESERVED_WORDS.add("STRING");
		// RESERVED_WORDS.add("SUB-SCHEMA");
		RESERVED_WORDS.add("SUBTRACT");
		// RESERVED_WORDS.add("SUM");
		RESERVED_WORDS.add("SUPER");
		RESERVED_WORDS.add("SUPPRESS");
		RESERVED_WORDS.add("SUPPRESSING");
		RESERVED_WORDS.add("SYMBOLIC");
		RESERVED_WORDS.add("SYNC");
		RESERVED_WORDS.add("SYNCHRONIZED");
		// RESERVED_WORDS.add("SYSTEM");
		RESERVED_WORDS.add("SYSTEM-DEFAULT");
		RESERVED_WORDS.add("TABLE");
		// RESERVED_WORDS.add("TALLY");
		RESERVED_WORDS.add("TALLYING");
		RESERVED_WORDS.add("TAPE");
		RESERVED_WORDS.add("TAPES");
		// RESERVED_WORDS.add("TENANT");
		RESERVED_WORDS.add("TERMINAL");
		RESERVED_WORDS.add("TERMINATE");
		RESERVED_WORDS.add("TEST");
		RESERVED_WORDS.add("THAN");
		RESERVED_WORDS.add("THEN");
		RESERVED_WORDS.add("THROUGH");
		RESERVED_WORDS.add("THRU");
		RESERVED_WORDS.add("TIME");
		RESERVED_WORDS.add("TIMES");
		RESERVED_WORDS.add("TO");
		RESERVED_WORDS.add("TOP");
		// RESERVED_WORDS.add("TRAILING");
		// RESERVED_WORDS.add("TRUE");
		RESERVED_WORDS.add("TRY");
		RESERVED_WORDS.add("TYPE");
		RESERVED_WORDS.add("TYPEDEF");
		RESERVED_WORDS.add("UNIT");
		RESERVED_WORDS.add("UNITS");
		RESERVED_WORDS.add("UNIVERSAL");
		RESERVED_WORDS.add("UNLOCK");
		RESERVED_WORDS.add("UNSTRING");
		RESERVED_WORDS.add("UNTIL");
		RESERVED_WORDS.add("UP");
		// RESERVED_WORDS.add("UPDATE");
		RESERVED_WORDS.add("UPON");
		RESERVED_WORDS.add("USAGE");
		// RESERVED_WORDS.add("USAGE-MODE");
		RESERVED_WORDS.add("USE");
		RESERVED_WORDS.add("USER-DEFAULT");
		RESERVED_WORDS.add("USING");
		RESERVED_WORDS.add("VAL-STATUS");
		RESERVED_WORDS.add("VALID");
		RESERVED_WORDS.add("VALIDATE");
		RESERVED_WORDS.add("VALIDATE-STATUS");
		RESERVED_WORDS.add("VALUE");
		RESERVED_WORDS.add("VALUES");
		RESERVED_WORDS.add("VARYING");
		// RESERVED_WORDS.add("VIA");
		RESERVED_WORDS.add("WHEN");
		RESERVED_WORDS.add("WITH");
		// RESERVED_WORDS.add("WITHIN");
		RESERVED_WORDS.add("WORDS");
		RESERVED_WORDS.add("WORKING-STORAGE");
		RESERVED_WORDS.add("WRITE");
		RESERVED_WORDS.add("ZERO");
		RESERVED_WORDS.add("ZEROES");
		RESERVED_WORDS.add("ZEROS");
	}

	private static final int DEFAULT_MAX_COBOL_WORD_LENGTH = 31;
	private static final int MAX_COBOL_WORD_LENGTH;

	static {
		String maxCobolWordLengthSetting = System.getProperty(
				"koopa.maxCobolWordLength", "" + DEFAULT_MAX_COBOL_WORD_LENGTH);

		int maxCobolWordLengthValue;
		try {
			maxCobolWordLengthValue = Integer
					.parseInt(maxCobolWordLengthSetting);

		} catch (NumberFormatException e) {
			System.err
					.println("Warning: value for koopa.maxCobolWordLength is not a number: "
							+ maxCobolWordLengthSetting);
			maxCobolWordLengthValue = DEFAULT_MAX_COBOL_WORD_LENGTH;
		}

		if (maxCobolWordLengthValue == 0)
			maxCobolWordLengthValue = DEFAULT_MAX_COBOL_WORD_LENGTH;

		MAX_COBOL_WORD_LENGTH = maxCobolWordLengthValue;
	}

	// ============================================================================
	// Program text and separators
	// ............................................................................

	@Override
	public boolean isProgramText(Token token) {
		return token.hasTag(AreaTag.PROGRAM_TEXT_AREA);
	}

	@Override
	protected boolean isSeparator(String text) {
		return text.equals(",") || text.equals(";")
				|| text.trim().length() == 0;
	}

	@Override
	public boolean isSeparator(Token token) {
		return token.hasTag(SyntacticTag.SEPARATOR)
				&& isSeparator(token.getText());
	}

	// ============================================================================
	// cobolWord
	// ............................................................................

	private Parser cobolWordParser = null;

	/*
	 * "A COBOL word is a character-string of not more than 31 characters that
	 * forms a compiler-directive word, a context-sensitive word, an
	 * intrinsic-function-name, a reserved word, a system-name, or a
	 * user-defined word. Each character of a COBOL word that is not a special
	 * character word shall be selected from the set of basic letters, basic
	 * digits, extended letters, and the basic special characters hyphen and
	 * underscore. The hyphen or underscore shall not appear as the first or
	 * last character in such words."
	 * 
	 * ------------------------------------------------------------------------
	 * Except where specific rules apply, the hyphen (-) and the underline (_)
	 * are treated as the same character in a user- defined word. The underline
	 * (_), however, can begin or end a user-defined word, and the hyphen (-)
	 * cannot.
	 * 
	 * Description from: HP COBOL Reference Manual http://h71000.
	 * www7.hp.com/doc/82final/6296/6296pro_002.html
	 */
	public Parser cobolWord() {
		if (cobolWordParser == null) {
			FutureParser future = scoped("cobolWord");
			cobolWordParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					List<Token> parts = new LinkedList<Token>();
					while (true) {
						Token token = stream.forward();

						if (token == null)
							break;

						if (!token.hasTag(PROGRAM_TEXT_AREA)
								|| !token.hasTag(CHARACTER_STRING)
								|| !isCobolWordPart(token.getText())) {
							stream.rewind(token);
							break;
						}

						parts.add(token);
					}

					Token cobolWord = new Token(parts, CHARACTER_STRING,
							PROGRAM_TEXT_AREA);

					String text = cobolWord.getText();
					if (isCobolWord(cobolWord.getText())
							&& !RESERVED_WORDS.contains(text.toUpperCase())) {
						returnToken(cobolWord);
						return true;
					} else
						return false;
				}

				private boolean isCobolWordPart(String text) {
					final int len = text.length();

					for (int i = 0; i < len; i++) {
						final char c = text.charAt(i);

						if (c >= 'A' && c <= 'Z')
							continue;

						if (c >= 'a' && c <= 'z')
							continue;

						if (c >= '0' && c <= '9')
							continue;

						if (c == '-' || c == '_')
							continue;

						return false;
					}

					return true;
				}

				private boolean isCobolWord(String text) {
					final int len = text.length();
					if (len < 1
							|| (MAX_COBOL_WORD_LENGTH > 0 && len > MAX_COBOL_WORD_LENGTH))
						return false;

					if (text.charAt(0) == '-')
						return false;

					if (text.charAt(len - 1) == '-')
						return false;

					for (int i = 0; i < len; i++) {
						final char c = text.charAt(i);

						if (c < '0' || c > '9')
							return true;
					}

					return false;
				}
			});
		}
		return cobolWordParser;
	}

	// ============================================================================
	// integerLiteral
	// ............................................................................

	private Parser integerLiteralParser = null;

	public Parser integerLiteral() {
		if (integerLiteralParser == null) {
			FutureParser future = scoped("integerLiteral");
			integerLiteralParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					Token token = stream.forward();

					if (token != null
							&& token.hasTag(SyntacticTag.CHARACTER_STRING)) {
						if (token.hasTag(SyntacticTag.INTEGER_LITERAL)) {

							returnToken(token);
							return true;

						} else
							return false;

					} else
						return false;
				}
			});
		}
		return integerLiteralParser;
	}

	// ============================================================================
	// booleanLiteral
	// ............................................................................

	private Parser booleanLiteralParser = null;

	public Parser booleanLiteral() {
		if (booleanLiteralParser == null) {
			FutureParser future = scoped("booleanLiteral");
			booleanLiteralParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					Token token = stream.forward();

					if (token != null
							&& token.hasTag(SyntacticTag.CHARACTER_STRING)) {
						if (token.hasTag(SyntacticTag.BOOLEAN_LITERAL)) {

							returnToken(token);
							return true;

						} else
							return false;

					} else
						return false;
				}
			});
		}
		return booleanLiteralParser;
	}

	// ============================================================================
	// hexadecimal
	// ............................................................................

	private Parser hexadecimalParser = null;

	public Parser hexadecimal() {
		if (hexadecimalParser == null) {
			FutureParser future = scoped("hexadecimal");
			hexadecimalParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					Token token = stream.forward();

					if (token != null
							&& token.hasTag(SyntacticTag.CHARACTER_STRING)) {
						if (token.hasTag(SyntacticTag.HEXADECIMAL_LITERAL)) {

							returnToken(token);
							return true;

						} else
							return false;

					} else
						return false;
				}
			});
		}
		return hexadecimalParser;
	}

	// ============================================================================
	// alphanumericLiteral
	// ............................................................................

	private Parser alphanumericLiteralParser = null;

	public Parser alphanumericLiteral() {
		if (alphanumericLiteralParser == null) {
			FutureParser future = scoped("alphanumericLiteral");
			alphanumericLiteralParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					Token token = stream.forward();

					if (token != null
							&& token.hasTag(SyntacticTag.CHARACTER_STRING)) {
						if (token.hasTag(SyntacticTag.STRING_LITERAL)) {

							returnToken(token);
							return true;

						} else
							return false;

					} else
						return false;
				}
			});
		}
		return alphanumericLiteralParser;
	}

	// ============================================================================
	// pseudo literal
	// ............................................................................

	private Parser pseudoLiteralParser = null;

	public Parser pseudoLiteral() {
		if (pseudoLiteralParser == null) {
			FutureParser future = scoped("pseudoLiteral");
			pseudoLiteralParser = future;
			future.setParser(new Parser() {
				public boolean accepts(ParseStream stream) {
					skipSeparators(stream);

					Token token = stream.forward();

					if (token != null
							&& token.hasTag(SyntacticTag.CHARACTER_STRING)) {
						if (token.hasTag(SyntacticTag.PSEUDO_LITERAL)) {

							returnToken(token);
							return true;

						} else
							return false;

					} else
						return false;
				}
			});
		}
		return pseudoLiteralParser;
	}

}