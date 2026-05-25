package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeamGeneratorTest {

    @Test
    void correctLengthOfBeam() {
        double length = BeamGenerator.beamLength(7800);

        assertEquals(7725,length);
    }

    @Test
    void correctNumberOfBeams() {
        int totalBeams = BeamGenerator.beamGenerator();

        assertEquals(2, totalBeams);
    }
}