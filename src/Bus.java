public class Bus extends Automobile{
    private int places;
    private int doors;

    @Override
    public boolean equals(Object o){
        Bus bus = (Bus) o;
        return super.equals(o) && doors == bus.doors;
    }

    public String toString(){
        String temp;
        temp = super.toString();
        temp += ", seat places: " + this.getPlaces() + ", doors: " + this.getDoors() + ";";
        return temp;
    }

    public Bus(String name, String color, Fuel fuel, int places, int doors) throws IncorrectIntException{
        super(name, color, fuel);
        if (places < 1 || doors < 1)
            throw new IncorrectIntException();
        this.places = places;
        this.doors = doors;
    }

    public int getPlaces(){
        return places;
    }

    public void setPlaces(int places){
        this.places = places;
    }

    public int getDoors(){
        return doors;
    }

    public void setDoors(int doors){
        this.doors = doors;
    }


}
class IncorrectIntException extends Exception {
    IncorrectIntException() {
        super("You can't enter negative number of doors or seats!");
    }
}