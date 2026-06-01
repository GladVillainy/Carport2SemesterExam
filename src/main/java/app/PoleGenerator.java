package app;

public class PoleGenerator {
    private static double standartWidth = 9.7;
    private static double disOfPoles;


    public static int poleGenerator(double length, boolean withShed) {

        //siden der ikke er nogen regler for hvornår 3 stolper ikke er nok længere
        double numberOfPoles = 6;

        if (withShed) {
            //hvis skur tilføjes, tilføjes der 4, så der er 6 i alt,
            //da man kan genanvende 2 fra carporten, plus 1 ekstra til at lave døren til skuret
            //numberOfPoles = numberOfPoles + 4 + 1;

            //logik til at sætte ny disOfPole
        }

        //distancen mellem pælene udføres ved at trække 2 meter fra længden (1 meter i begge sider)
        //derefter dividere vi med antallet at pæle minus startpælene(altså 2) og slutpælene(2 igen)
        double disOfPole = (length-200)/(numberOfPoles-4);

        disOfPoles = disOfPole;
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
