package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoleGeneratorTest {

    @Test
    void numberOfPoles() {
        int numberOfPoles = PoleGenerator.poleGenerator(780, true);

        assertEquals(11, numberOfPoles);
    }

    @Test
    void poleLength() {
        double poleLength = PoleGenerator.poleLength();

        assertEquals(300, poleLength);
        assertNotEquals(400, poleLength);
    }
}