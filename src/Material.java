public enum Material {
    LEATHER,
    COTTON;

    private Material() {
    }

    public String toString() {
        switch (this) {
            case LEATHER:
                return "Leather";
            case COTTON:
                return "Cotton";
            default:
                return "Wrong type";
        }
    }

    public Material getMaterialType(String s) throws EnumIncorrectException {
        Material delta;
        switch (s) {
            case "Cotton":
                delta = COTTON;
                break;
            case "Leather":
                delta = LEATHER;
                break;
            default:
                throw new EnumIncorrectException();
        }

        return delta;
    }
}
class EnumIncorrectException extends Exception {
    public EnumIncorrectException() {
        super("Wrong type");
    }
}
