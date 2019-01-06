package code.hao.twit;

import android.util.Log;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static code.hao.twit.utils.MessageUtils.splitMessage;
import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {
    private static final int CHARACTER_LIMIT = 50;
    /**
     * message< 50
     */
    @Test
    public void testMessageLengthLessThanCharacterLimit() throws Exception {
        List<String> expected = new ArrayList<>();
        String message = "I don't know Kotlin, then I have to learn it!";
        System.out.println("Message with length < 50 : " + message.length());

        String ex1 = "I don't know Kotlin, then I have to learn it!";
        expected.add(ex1);
        assertEqualsList(expected, splitMessage(message, CHARACTER_LIMIT));

    }

    private void assertEqualsList(List<String> expected, List<String>  actual) {
        assertEquals(expected.size(),  actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i),  actual.get(i));

        }
    }
}
