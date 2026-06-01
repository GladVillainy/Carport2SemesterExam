package app;

public class RaftGenerator {
    private static double standartWidth = 4.5;
    private static double distanceBetweenRafts;


    public static int raftGenerator(double length) {
        //carport længde / 60cm (max centerafstand) + 1(kan ikke være mindre end 2 spær)
        if (length > 100) {
            double numberOfSpacesBetweenRafts = 0.0;
            //finder det lavest mulige mellemrum imellem spærne
            numberOfSpacesBetweenRafts = (length/60);
            //afrunder til højeste
            double roundedTotal = Math.ceil(numberOfSpacesBetweenRafts);
            //hvis der ikke er rundet op,
            //tilføjes et ekstra mellemrum for at sikre afstanden mellem spærne aldrig rammer 0.6m
            if (roundedTotal <= numberOfSpacesBetweenRafts) {
                roundedTotal = roundedTotal+1;
            }
            //Tilføjer et spær mere end der er mellemrum i mellem dem.
            roundedTotal = roundedTotal+1;

            //gemmer distancen mellem, til at lave tegning
            //da distancen ikke tager højde fra startspær trækker jeg -1 fra roundedTotal
            distanceBetweenRafts = length/(roundedTotal-1);

            return (int) roundedTotal;
        }
        return 0;
    }

    public static double raftLength(double width) {
        return width;
    }



    public static double getDistanceBetweenRafts() {
        return distanceBetweenRafts;
    }

    public static double getStandartWidth() {
        return standartWidth;
    }
}
