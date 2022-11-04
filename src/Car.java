public class Car extends Automobile{
    private Material material;

    @Override
    public boolean equals(Object o){
        Car temp = (Car) o;
        return super.equals(o) && material.equals(temp.material);
    }

    public String toString()
    {
        String temp;
        temp = super.toString();
        temp += ", seat material: " + this.getMaterial() + ";";
        return temp;
    }

    public Car(String name, String color, Fuel fuel, Material material){
        //Важно использовать супер
        super(name, color, fuel);
        this.material = material;
    }

    public Material getMaterial(){
        return material;
    }

    public void setMaterial(Material material){
        this.material = material;
    }
}