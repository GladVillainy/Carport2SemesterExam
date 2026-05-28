package app;

public class PoleGenerator {
    private static double standartWidth = 97;
    private static double disOfPoles;


    public static int poleGenerator(double length, boolean withShed) {

        //omskriver længden fra cm til meter
        length = length/100;
        //siden der ikke er nogen regler for hvornår 3 stolper ikke er nok længere
        //sætter jeg en begrænsning på at distancen mellem stolperne max må være 4 meter
        double numberOfPoles = 6;
        double disOfPole = length/2;
        int i = 1;

        while (disOfPole > 4) {
            numberOfPoles += 2;
            disOfPole = length / (2 + i++);
        }

        if (withShed) {
            //hvis skur tilføjes, tilføjes der 4, så der er 6 i alt,
            //da man kan genanvende 2 fra carporten, plus 1 ekstra til at lave døren til skuret
            numberOfPoles = numberOfPoles + 4 + 1;

            //logik til at sætte ny disOfPole
        }

        //laver disOfPole om til cm igen
        disOfPole = disOfPole*100;
        disOfPoles = disOfPole;
        return (int) numberOfPoles;

    }

    public static double poleLength() {
        //pælene vil altid være i standart 3 meter, da de skal 90 til 110 cm ned i jorden.
        return 300;
    }

    public double getStandartWidth() {
        return standartWidth;
    }

    public static double getDisOfPoles() {
        return disOfPoles;
    }
}
