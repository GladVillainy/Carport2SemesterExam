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
        this.beamLength = (BeamGenerator.beamLength(width));
        this.amountRafts = RaftGenerator.raftGenerator(height);
        this.distanceBetweenRafts = RaftGenerator.getDistanceBetweenRafts();
        this.amountPoles = PoleGenerator.poleGenerator(height, carport.isShed());
        this.distanceBetweenPoles = PoleGenerator.poleLength();


        carportSvg = new Svg(0,0, "0 0 855 690", "100%");
        addArrows(carportSvg);
        addTexts(carportSvg);
        Svg innerSvg = new Svg(75,10, "0 0 780 600", "91%");
        addBeams(innerSvg);
        addRafts(innerSvg);
        carportSvg.addSvg(innerSvg);
    }

    public void addTexts(Svg svg) {
        svg.addTextWithRotation(30,300,-90,"600cm");
        svg.addText(502, 670, "780cm");
    }

    public void addArrows(Svg svg){
        svg.addArrow(40,10,40,610);
        svg.addArrow(75,650,855,650);
    }

    public void addBeams(Svg svg){
        svg.addRectangle(0,35,4.5,780,"stroke-width:1px; stroke:#000000; fill: #ffffff");
        svg.addRectangle(0,565,4.5,780,"stroke-width:1px; stroke:#000000; fill: #ffffff");
    }

    public void addRafts(Svg svg){
        for (int i = 0; i <= height; i+= 52) {
            svg.addRectangle(i, 0, 600, 4.5,"stroke:#000000; fill: #ffffff");
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
