package app;

public class PoleGenerator {
    private static double standartWidth = 9.7;
    private static double disOfPoles;


    public static int poleGenerator(double length, boolean withShed) {

        //siden der ikke er nogen regler for hvornår 3 stolper ikke er nok længere
        double numberOfPoles = 6;
        double amountOfPolesNeededForShed = 5;
        double numberOfPolesWithOutShed = numberOfPoles;

        if (withShed) {
            //hvis skur tilføjes, tilføjes der 4, så der er 6 i alt,
            //da man kan genanvende 2 fra carporten, plus 1 ekstra til at lave døren til skuret
            numberOfPoles = numberOfPoles + amountOfPolesNeededForShed;


            numberOfPolesWithOutShed = numberOfPoles-amountOfPolesNeededForShed;
        }

        //distancen mellem pælene udføres ved at trække 2 meter fra længden (1 meter i begge sider)
        //derefter dividere vi med antallet at pæle minus startpælene(altså 2) og slutpælene(2 igen)
        //i situationen hvorpå der er blevet lagt pælene oven i til skuret, trækker vi dem fra.
        int offsetLength = 200;

        double disOfPole = (length-offsetLength)/(numberOfPolesWithOutShed-4);

        disOfPoles = disOfPole;

        if (withShed) {
            //hvis der er skur, så reducer afstanden med pælensBredde + 25 cm.
            //så pælen står lidt forskudt fra midten
            double distanceFromRightsideToShed = standartWidth+25;

            disOfPole -= distanceFromRightsideToShed;
            disOfPoles = disOfPole;
        }


        return (int) numberOfPoles;

    }

    public static double poleLength() {
        //pælene vil altid være i standart 3 meter, da de skal 90 til 110 cm ned i jorden.
        return 300;
    }

    public static double getStandartWidth() {
        return standartWidth;
    }

    public static double getDisOfPoles() {
        return disOfPoles;
    }
}
