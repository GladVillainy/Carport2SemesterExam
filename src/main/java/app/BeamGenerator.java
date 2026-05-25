package app;

public class BeamGenerator {


    public static int beamGenerator(){
        //altid kun 2 Remme
        return 2;
    }

    public static double beamLength(double length) {
        //totale længde af Rem, minus 1x25(stern) og minus 2x25(stern og tag fald)
        return (length-(1*25)-(2*25));
    }
}
