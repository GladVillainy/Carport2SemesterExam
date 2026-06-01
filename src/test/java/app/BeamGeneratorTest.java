package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeamGeneratorTest {

    @Test
    void correctLengthOfBeam() {
        double length = BeamGenerator.beamLength(780, false);

        assertEquals(772.5,length);
    }

    @Test
    void correctNumberOfBeams() {
        int totalBeams = BeamGenerator.beamGenerator(false);

        assertEquals(2, totalBeams);
    }
}