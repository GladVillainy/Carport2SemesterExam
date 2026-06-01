package app;

import entities.Carport;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;
    private int amountBeams;
    private double beamLength;
    private int amountRafts;
    private double distanceBetweenRafts;
    private double widthRafts;
    private int amountPoles;
    private double distanceBetweenPoles;
    private boolean withShed;

    public CarportSvg(Carport carport) {
        this.width = carport.getWidth();
        this.length = carport.getLength();
        this.withShed = carport.isShed();
        this.amountBeams = BeamGenerator.beamGenerator(withShed);
        this.beamLength = (BeamGenerator.beamLength(width, withShed));
        this.amountRafts = RaftGenerator.raftGenerator(length);
        this.distanceBetweenRafts = RaftGenerator.getDistanceBetweenRafts();
        this.widthRafts = RaftGenerator.getStandartWidth();
        this.amountPoles = PoleGenerator.poleGenerator(length, carport.isShed());
        this.distanceBetweenPoles = PoleGenerator.getDisOfPoles();



        carportSvg = new Svg(0,0, "0 0 " + (length + 75) + " " + (width + 90), "100%");
        addArrows(carportSvg);
        addTexts(carportSvg);
        Svg innerSvg = new Svg(75,10, "0 0 "+(length+75)+" "+(width+90), "100%");
        addFrame(innerSvg);
        addRafts(innerSvg);
        addBeams(innerSvg);
        //addPoles(innerSvg);
        carportSvg.addSvg(innerSvg);
    }

    public void addFrame(Svg svg) {
        svg.addRectangle(0,0, width,length, "stroke:#000000; fill: #ffffff");
    }

    public void addTexts(Svg svg) {
        svg.addTextWithRotation(30, width / 2, -90, width + "cm");
        svg.addText(75 + length / 2, width + 70, length + "cm");
    }

    public void addArrows(Svg svg){
        svg.addArrow(40, 10, 40, width + 10);
        svg.addArrow(75, width + 50, length + 75, width + 50);
    }

    public void addBeams(Svg svg){
        svg.addRectangle(0,35,4.5,length,"stroke-width:1px; stroke:#000000; fill: #ffffff");
        svg.addRectangle(0,width-35,4.5,length,"stroke-width:1px; stroke:#000000; fill: #ffffff");
    }

    public void addRafts(Svg svg){
        //tegner første og sidste spær
        svg.addRectangle(0, 0, width, 4.5,"stroke:#000000; fill: #ffffff");
        svg.addRectangle(length-widthRafts, 0, width, 4.5,"stroke:#000000; fill: #ffffff");

        //starter med 1 for at ungå første spær og slutter i -2 fra mængden af spær for at ungå sidste
        for (int i = 1; i <= amountRafts-2; i+= 1) {
            svg.addRectangle(i*distanceBetweenRafts, 0, width, 4.5,"stroke:#000000; fill: #ffffff");
        }
    }
    public void addPoles(Svg svg){
        //poles i toppen
        for (int i = (int) distanceBetweenPoles/4; i < (amountPoles*distanceBetweenPoles)/2 ; i+=distanceBetweenPoles) {
            svg.addRectangle(i, 32, 9.7, 10, "stroke:#000000; fill: #ffffff");
        };
        //poles i bunden
        for (int i = (int) distanceBetweenPoles/4; i < (amountPoles*distanceBetweenPoles)/2 ; i+=distanceBetweenPoles) {
            svg.addRectangle(i, 562, 9.7, 10, "stroke:#000000; fill: #ffffff");
        };
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
