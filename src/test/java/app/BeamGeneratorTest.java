package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeamGeneratorTest {

    @Test
    void correctLengthOfBeam() {
        double length = BeamGenerator.beamLength(7800, false);

        assertEquals(7725,length);
    }

    @Test
    void correctNumberOfBeams() {
        int totalBeams = BeamGenerator.beamGenerator(false);

        assertEquals(2, totalBeams);
    }
}