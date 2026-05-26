package app;

import entities.Carport;

public class CarportSvg {
    private int width;
    private int height;
    private Svg carportSvg;
    private int amountBeams;
    private double beamLength;
    private int amountRafts;
    private double distanceBetweenRafts;
    private int amountPoles;
    private double distanceBetweenPoles;

    public CarportSvg(Carport carport) {
        this.width = carport.getWidth();
        this.height = carport.getHeight();
        this.amountBeams = BeamGenerator.beamGenerator();
        this.beamLength = (BeamGenerator.beamLength(width)*10);
        this.amountRafts = RaftGenerator.raftGenerator(height);
        this.distanceBetweenRafts = RaftGenerator.getDistanceBetweenRafts();
        this.amountPoles = PoleGenerator.poleGenerator(height, carport.isShed());
        this.distanceBetweenPoles = PoleGenerator.poleLength();


        carportSvg = new Svg(0,0, "0 0 855 690", "100%");
        addBeams();
        addRafts();
    }

    public void addBeams(){
        carportSvg.addRectangle(0,35,4.5,780,"stroke-width:1px; stroke:#000000; fill: #ffffff");
        carportSvg.addRectangle(0,565,4.5,780,"stroke-width:1px; stroke:#000000; fill: #ffffff");
    }

    public void addRafts(){
        for (int i = 0; i <= height; i+= 52) {
            carportSvg.addRectangle(i, 0, 600, 4.5,"stroke:#000000; fill: #ffffff");
        }
    }
    //public void addPoles(){
        //for (int i = 0; i < ; i++) {

        //};

    //}

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
