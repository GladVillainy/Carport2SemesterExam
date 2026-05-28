package app;

public class BeamGenerator {
    private double standartWidth = 45;


    public static int beamGenerator(){
        //altid kun 2 Remme
        return 2;
    }

    public static double beamLength(double length) {
        //omskriv fra cm til mm
        length = length*10;
        //totale længde af Rem, minus 1x25(stern) og minus 2x25(stern og tag fald) og omskriver til cm igen
        return (length-(1*25)-(2*25))/10;
    }

    public double getStandartWidth() {
        return standartWidth;
    }
}
