package koopa.grammars.cobol.test;

import junit.framework.TestCase;
import koopa.parsers.Parser;
import koopa.tokenizers.cobol.TestTokenizer;

import org.junit.Test;

/** This code was generated from EntryStatement.stage. */
public class EntryStatementTest extends TestCase {

  private static koopa.grammars.cobol.CobolGrammar grammar = new koopa.grammars.cobol.CobolGrammar();

    @Test
    public void testEntryStatement_1() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY");
      assertFalse(parser.accepts(tokenizer));
    }

    @Test
    public void testEntryStatement_2() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY \"MAIN-ENTRANCE\"");
      assertTrue(parser.accepts(tokenizer));
      assertTrue(tokenizer.isWhereExpected());
    }

    @Test
    public void testEntryStatement_3() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY \"MAIN-ENTRANCE\"\n         USING FIELD-A");
      assertTrue(parser.accepts(tokenizer));
      assertTrue(tokenizer.isWhereExpected());
    }

    @Test
    public void testEntryStatement_4() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY \"MAIN-ENTRANCE\"\n         USING FIELD-A FIELD-B");
      assertTrue(parser.accepts(tokenizer));
      assertTrue(tokenizer.isWhereExpected());
    }

    @Test
    public void testEntryStatement_5() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY \"MAIN-ENTRANCE\"\n          KOOPAH_TO_HERE  FIELD-A");
      assertTrue(parser.accepts(tokenizer));
      assertTrue(tokenizer.isWhereExpected());
    }

    @Test
    public void testEntryStatement_6() {
      Parser parser = grammar.entryStatement();
      assertNotNull(parser);
      TestTokenizer tokenizer = new TestTokenizer("ENTRY \"MAIN-ENTRANCE\"\n         USING FIELD-A\n          KOOPAH_TO_HERE  USING FIELD-B");
      assertTrue(parser.accepts(tokenizer));
      assertTrue(tokenizer.isWhereExpected());
    }
}