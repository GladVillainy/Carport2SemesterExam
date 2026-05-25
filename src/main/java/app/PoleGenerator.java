package app;

public class PoleGenerator {


    public static int poleGenerator(double length, boolean withShed) {

        //omskriver længden til meter
        length = length/1000;
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
        }
        return (int) numberOfPoles;

    }

    public static double poleLength() {
        //pælene vil altid være i standart 3 meter, da de skal 90 til 110 cm ned i jorden.
        return 3000;
    }
}
