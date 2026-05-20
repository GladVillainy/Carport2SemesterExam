package app;

public class ListGenerator {

    public static int raftGenerator(double length) {
        //omskriv længde til meter
        length = length/1000;
        //carport længde / 0.60 (max centerafstand) + 1(kan ikke være mindre end 2 spær)
        if (length > 1) {
            double numberOfSpacesBetweenRafts = 0.0;
            //
            numberOfSpacesBetweenRafts = (length/0.55);
            System.out.println(numberOfSpacesBetweenRafts);
            //afrunder til højeste
            double roundedTotal = Math.ceil(numberOfSpacesBetweenRafts);
            //hvis der er rundet op, behøves der ikke tilføjes et ekstra spær
            if (roundedTotal <= numberOfSpacesBetweenRafts) {
                roundedTotal = roundedTotal+1;
            }
            double distanceBetweenRafts = (length)/roundedTotal;

            System.out.println(distanceBetweenRafts);
            return (int) roundedTotal;
        }
        return 0;
    }

}
