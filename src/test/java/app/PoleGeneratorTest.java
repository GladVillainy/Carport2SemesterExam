package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoleGeneratorTest {

    @Test
    void numberOfPoles() {
        int numberOfPoles = PoleGenerator.poleGenerator(7800, true);

        assertEquals(11, numberOfPoles);
    }

    @Test
    void distancePrPoleBiggerThan8() {
        int numberOfPoles = PoleGenerator.poleGenerator(12000, false);

        assertEquals(8, numberOfPoles);
    }

    @Test
    void distancePrPoleBiggerThan8vol2() {
        int numberOfPoles = PoleGenerator.poleGenerator(16000, false);

        assertEquals(10, numberOfPoles);
    }

    @Test
    void poleLength() {
        double poleLength = PoleGenerator.poleLength();

        assertEquals(3000, poleLength);
        assertNotEquals(4000, poleLength);
    }
}