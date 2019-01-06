package code.hao.twit;

import code.hao.twit.utils.MessageUtils;
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
     * The message contains a span of non-whitespace characters longer than limit characters
     */
    @Test(expected = MessageUtils.SplitMessageException.class)
    public void testMessageLengthNonWhitespaceLonger50() throws MessageUtils.SplitMessageException {
        String message = "well, whoamiwhatiamgonnadotheworldissomuchmorepainfulihavetodothatonewayoranother. amigivingup?";
        splitMessage(message, CHARACTER_LIMIT);
    }


    /**
     * The message contains a span of non-whitespace characters longer than limit characters
     */
    @Test(expected = MessageUtils.SplitMessageException.class)
    public void testMessageLengthNonWhitespacePlusIndicatorLonger50() throws MessageUtils.SplitMessageException {
        String message = "well, whoamiwhatiamgonnadotheworldissomuchmorepainful...";
        splitMessage(message, CHARACTER_LIMIT);
    }

    private void assertEqualsList(List<String> expected, List<String>  actual) {
        assertEquals(expected.size(),  actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),  actual.get(i));

        }
    }
}
