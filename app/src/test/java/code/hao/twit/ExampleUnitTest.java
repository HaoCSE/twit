package code.hao.twit;

import code.hao.twit.utils.MessageUtils;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static code.hao.twit.utils.MessageUtils.splitMessage;
import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {
    private static final int CHARACTER_LIMIT = 50;
    /**
     * message < 50
     */
    @Test
    public void testMessageLengthLessThanCharacterLimit() throws Exception {
        List<String> expected = new ArrayList<>();
        String message = "¡Hola, amigo! ¿Cómo estás?";

        String ex1 = "¡Hola, amigo! ¿Cómo estás?";
        expected.add(ex1);
        assertEqualsList(expected, splitMessage(message, CHARACTER_LIMIT));

    }

    /**
     * message > 50
     */
    @Test
    public void testMessageLengthMoreThanCharacterLimit() throws Exception {
        List<String> expected = new ArrayList<>();

        String message = "Ich öffnete die Wohnungstür und rief: Hallo? Jemand da?";

        expected.add("1/2 Ich öffnete die Wohnungstür und rief: Hallo?");
        expected.add("2/2 Jemand da?");
        assertEqualsList(expected, splitMessage(message, CHARACTER_LIMIT));
    }

    /**
     * message = 50
     */
    @Test
    public void testMessageLengthEqualsToCharacterLimit() throws Exception {
        List<String> expected = new ArrayList<>();

        String message = "Aujourd’hui, maman est morte. Ou peut-être hier,..";

        expected.add("Aujourd’hui, maman est morte. Ou peut-être hier,..");
        assertEqualsList(expected, splitMessage(message, CHARACTER_LIMIT));
    }



    /**
     * The message contains a span of non-whitespace characters longer than limit characters.
     * Expected: Exception
     */
    @Test(expected = MessageUtils.SplitMessageException.class)
    public void testMessageLengthNonWhitespaceLonger50() throws MessageUtils.SplitMessageException {
        String message = "well, whoamiwhatiamgonnadotheworldissomuchmorepainfulihavetodothatonewayoranother. amigivingup?";
        splitMessage(message, CHARACTER_LIMIT);
    }


    /**
     * The message contains a span of non-whitespace characters and indicator strings longer than limit characters
     * Expected: Exception
     */
    @Test(expected = MessageUtils.SplitMessageException.class)
    public void testMessageLengthNonWhitespacePlusIndicatorLonger50() throws MessageUtils.SplitMessageException {
        String message = "well, whoamiwhatiamgonnadotheworldissomuchmorepainful...";
        splitMessage(message, CHARACTER_LIMIT);
    }

    /**
     *
     * @throws MessageUtils.SplitMessageException
     */
    @Test
    public void testOverThanTenParts() throws MessageUtils.SplitMessageException {
        List<String> expected = new ArrayList<>();
        String message = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself. I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself. I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself. I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself. I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.";
        String ex1 = "1/11 I can't believe Tweeter now supports chunking";
        String ex2 = "2/11 my messages, so I don't have to do it myself.";
        String ex3 = "3/11 I can't believe Tweeter now supports chunking";
        String ex4 = "4/11 my messages, so I don't have to do it myself.";
        String ex5 = "5/11 I can't believe Tweeter now supports chunking";
        String ex6 = "6/11 my messages, so I don't have to do it myself.";
        String ex7 = "7/11 I can't believe Tweeter now supports chunking";
        String ex8 = "8/11 my messages, so I don't have to do it myself.";
        String ex9 = "9/11 I can't believe Tweeter now supports chunking";
        String ex10 = "10/11 my messages, so I don't have to do it";
        String ex11 = "11/11 myself.";

        expected.add(ex1);
        expected.add(ex2);
        expected.add(ex3);
        expected.add(ex4);
        expected.add(ex5);
        expected.add(ex6);
        expected.add(ex7);
        expected.add(ex8);
        expected.add(ex9);
        expected.add(ex10);
        expected.add(ex11);
        assertEqualsList(expected, splitMessage(message, CHARACTER_LIMIT));

    }

    /**
     * Expected: Exception
     */
    @Test(expected = MessageUtils.SplitMessageException.class)
    public void testEdgeCases() throws MessageUtils.SplitMessageException {

        String message = "abcfdsadefghijilmnosafdsafdsafsapqrstuvwxyz1234567890!@#$%^& abcdefghijilfdsafsamnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvfsafwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^& abcdefghijilmnopqrstuvwxyz1234567890!@#$%^&22";
        splitMessage(message, CHARACTER_LIMIT);
    }

    private void assertEqualsList(List<String> expected, List<String>  actual) {
        assertEquals(expected.size(),  actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),  actual.get(i));

        }
    }

    @After
    public void tearDown() {

    }
}
