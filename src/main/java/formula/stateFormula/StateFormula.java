package formula.stateFormula;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract void checker(StringBuilder buffer);

//    @Override
//    public String toString() {
//        StringBuilder buffer = new StringBuilder();
//        writeToBuffer(buffer);
//        return buffer.toString();
//    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        checker(buffer);
        return buffer.toString();
    }
}
