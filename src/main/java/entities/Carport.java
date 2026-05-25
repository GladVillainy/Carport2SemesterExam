package entities;

public class Carport {
    private int id;
    private int width;
    private int length;
    private int height;

    private String roofType;
    private boolean shed;

    //To create
    public Carport(int length, int width, int height, String roofType, boolean shed) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.shed = shed;
    }

    //To read
    public Carport(int id, int width, int length, int height, String roofType, boolean shed) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.shed = shed;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public String getRoofType() {
        return roofType;
    }

    public boolean isShed() {
        return shed;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public void setShed(boolean shed) {
        this.shed = shed;
    }

    public int getId() {
        return id;
    }
}
