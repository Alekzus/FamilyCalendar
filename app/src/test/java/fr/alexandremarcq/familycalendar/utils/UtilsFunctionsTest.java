package fr.alexandremarcq.familycalendar.utils;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class UtilsFunctionsTest {

    @Test
    public void dateConverterTest() {
        long[] inputs = {1613375636000L, 153653654000L, 163256674000L, 1603245762000L};
        int[][] expected = {
                {15, 1},
                {14, 10},
                {5, 2},
                {21, 9}
        };
        for (int i = 0; i < inputs.length; i++) {
            assertThat(
                    UtilsFunctions.dateConverter(inputs[i])
            ).isEqualTo(
                    expected[i]
            );
        }
    }
}
