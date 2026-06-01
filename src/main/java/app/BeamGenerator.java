package app;

public class BeamGenerator {
    private static double standartWidth = 4.5;


    public static int beamGenerator(boolean withShed){
        //standart Remmeantal
        int amountBeams = 2;
        if (withShed) {

        }
        return amountBeams;
    }

    public static double beamLength(double length, boolean withShed) {
        //totale længde af Rem, minus 1x25(stern) og minus 2x25(stern og tag fald)
        if (withShed){

        }
        double sternL = 25;
        length = length-sternL-(2*sternL);
        return length;
    }

    public static double getStandartWidth() {
        return standartWidth;
    }
}
