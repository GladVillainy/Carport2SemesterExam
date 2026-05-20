package app;

import org.junit.jupiter.api.Test;

import static app.ListGenerator.raftGenerator;
import static org.junit.jupiter.api.Assertions.*;

class ListGeneratorTest {

    @Test
    void howManyRafts() {
        int rafts = raftGenerator(7800);

        //expected false, expected true
        //assertNotEquals

        assertEquals(15, rafts);
    }
}