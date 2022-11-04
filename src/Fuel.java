public enum Fuel {
    DIZEL, BENZIN;

    @Override
    public String toString(){
        switch (this){
            case DIZEL:
                return "dizel";
            case BENZIN:
                return "benzin";
            default:
                return "how?????";
        }
    }

    public Fuel getFlueType(String s) throws EnumIncorrectException{
        Fuel delta;
        switch (s){
            case "benzin":
                delta = BENZIN;
                break;
            case "dizel":
                delta = DIZEL;
                break;
            default:
                throw new EnumIncorrectException();
        }
        return delta;
    }
}
