package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaftGeneratorTest {

    @Test
    void raftGenerator() {
        int numberOfRafts = RaftGenerator.raftGenerator(7800);

        assertEquals(15, numberOfRafts);
        assertNotEquals(20, numberOfRafts);
    }

    @Test
    void numberOfRafts() {
        int numberOfRafts = RaftGenerator.raftGenerator(8000);

        assertEquals(15, numberOfRafts);
        assertNotEquals(20, numberOfRafts);
    }

    @Test
    void raftLength() {
        double raftLength = RaftGenerator.raftLength(6000);

        assertEquals(6000, raftLength);
        assertNotEquals(3000, raftLength);
    }
}