package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.HintParser.generateHint;

import org.junit.Test;

public class HintParserTest {


    @Test
    public void generate_add_hint() {
        assertHintEquals("add", " n/NAME");
        assertHintEquals("add ", "n/NAME");
        assertHintEquals("add n", "/NAME");
        assertHintEquals("add n/", "NAME");
        assertHintEquals("add n/name", " p/PHONE");
        assertHintEquals("add n/name ", "p/PHONE");
        assertHintEquals("add n/name p", "/PHONE");
        assertHintEquals("add n/name p/", "PHONE");
        assertHintEquals("add n/name p/123", " e/EMAIL");
        assertHintEquals("add n/name p/notValid", " e/EMAIL");
        assertHintEquals("add n/name p/123 ", "e/EMAIL");
        assertHintEquals("add n/name p/123 e", "/EMAIL");
        assertHintEquals("add n/name p/123 e/", "EMAIL");
        assertHintEquals("add n/name p/123 e/e@e.com", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/notValid", " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com" , " a/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a" , "/ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/" , "ADDRESS");
        assertHintEquals("add n/name p/123 e/e@e.com a/address" , " t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address " , "t/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t" , "/TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/" , "TAG");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag" , " ");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    " , "");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag    bla bla" , " ");

        assertHintEquals("add p/phone", " n/NAME");
        assertHintEquals("add p/phone n", "/NAME");
        assertHintEquals("add p/phone t", "/TAG");

        //hints repeated args
        assertHintEquals("add t/tag t", "/TAG");

        //TODO: remove repeated args for unrepeatbles
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p" , "/PHONE");
        assertHintEquals("add n/name p/123 e/e@e.com a/address t/tag p/" , "PHONE");
    }

    @Test
    public void generate_edit_hint() {
        assertHintEquals("edit", " index");
        assertHintEquals("edit ", "index");
        assertHintEquals("edit 12", " n/NAME");
        assertHintEquals("edit 12 ", "n/NAME");

        assertHintEquals("edit 12 p", "/PHONE");
        assertHintEquals("edit 12 p/", "PHONE");

        assertHintEquals("edit 12 n", "/NAME");
        assertHintEquals("edit 12 n/", "NAME");

        assertHintEquals("edit 12 e", "/EMAIL");
        assertHintEquals("edit 12 e/", "EMAIL");

        assertHintEquals("edit 12 a", "/ADDRESS");
        assertHintEquals("edit 12 a/", "ADDRESS");

        assertHintEquals("edit 12 t", "/TAG");
        assertHintEquals("edit 12 t/", "TAG");

        assertHintEquals("edit 12 p/123", " n/NAME");
        assertHintEquals("edit 12 p/123 ", "n/NAME");

        //TODO: change this functionality
        assertHintEquals("edit p/123", " index");
        assertHintEquals("edit p/123 ", "index");
        assertHintEquals("edit p/123 1", " index");
        assertHintEquals("edit p/123 1 ", "index");
    }

    @Test
    public void generate_find_hint() {
        assertHintEquals("find", " n/NAME");
        assertHintEquals("find ", "n/NAME");
        assertHintEquals("find", " n/NAME");

        assertHintEquals("find n", "/NAME");
        assertHintEquals("find n/", "NAME");
        assertHintEquals("find n/1", " p/PHONE");

        assertHintEquals("find p", "/PHONE");
        assertHintEquals("find p/", "PHONE");
        assertHintEquals("find p/1", " n/NAME");

        assertHintEquals("find e", "/EMAIL");
        assertHintEquals("find e/", "EMAIL");
        assertHintEquals("find e/1", " n/NAME");

        assertHintEquals("find a", "/ADDRESS");
        assertHintEquals("find a/", "ADDRESS");
        assertHintEquals("find a/1", " n/NAME");

        assertHintEquals("find t", "/TAG");
        assertHintEquals("find t/", "TAG");
        assertHintEquals("find t/1", " n/NAME");

        assertHintEquals("find r", "/REMARK");
        assertHintEquals("find r/", "REMARK");
        assertHintEquals("find r/a", " n/NAME");


    }

    @Test
    public void generate_select_hint() {
        assertHintEquals("select", " index");
        assertHintEquals("select ", "index");
        assertHintEquals("select 1", "");
        assertHintEquals("select bla 1", " index");
    }

    @Test
    public void generate_delete_hint() {
        assertHintEquals("delete", " index");
        assertHintEquals("delete ", "index");
        assertHintEquals("delete 1", "");
        assertHintEquals("delete bla 1", " index");
    }

    @Test
    public void generate_standard_hint() {
        assertHintEquals("history", " show command history");
        assertHintEquals("exit", " exits the app");
        assertHintEquals("clear", " clears address book");
        assertHintEquals("help", " shows user guide");
        assertHintEquals("undo", " undo command");
        assertHintEquals("redo", " redo command");
        assertHintEquals("unknown", " type help for guide");

        //TODO: to change
        assertHintEquals("alias", " creates an alias");
    }

    public void assertHintEquals(String userInput, String expected) {
        assertEquals(expected, generateHint(userInput));
    }

}