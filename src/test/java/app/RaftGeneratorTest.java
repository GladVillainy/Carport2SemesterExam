package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaftGeneratorTest {

    @Test
    void raftGenerator() {
        int numberOfRafts = RaftGenerator.raftGenerator(780);

        assertEquals(15, numberOfRafts);
        assertNotEquals(20, numberOfRafts);
    }

    @Test
    void numberOfRafts() {
        int numberOfRafts = RaftGenerator.raftGenerator(800);

        assertEquals(15, numberOfRafts);
        assertNotEquals(20, numberOfRafts);
    }

    @Test
    void raftLength() {
        double raftLength = RaftGenerator.raftLength(600);

        assertEquals(600, raftLength);
        assertNotEquals(300, raftLength);
    }
}