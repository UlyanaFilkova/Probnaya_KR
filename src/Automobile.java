public abstract class Automobile implements Comparable<Automobile>{
    private String name;
    private String color;
    private Fuel fuel;

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Automobile that = (Automobile) o;
        return name.equals(that.name) && color.equals(that.color) &&fuel == that.fuel;
    }



    @Override
    public String toString(){
        String temp = new String();
        temp = this.getClass().getName() + ": " + this.getName() +
                ", color: " + this.getColor() + ", fuel: " + this.getFuel();
        return temp;
    }



    @Override
    public int compareTo(Automobile o){
        if (!name.equals(o.name))
            return name.compareTo(o.name);
        return color.compareTo(o.color);
    }

    public Automobile(String name, String color, Fuel fuel){
        this.name = name;
        this.color = color;
        this.fuel = fuel;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public Fuel getFuel(){
        return fuel;
    }

    public void setFuel(Fuel fuel){
        this.fuel = fuel;
    }
}
