package de.weltraumschaf.caythe.backend.vm;

public final class FuncMetaData {
    private String name;
    private int nargs;
    private int nlocals;
    /**
     * bytecode address
     */
    private int address;

    FuncMetaData(String name, int nargs, int nlocals, int address) {
        super();
        this.name = name;
        this.nargs = nargs;
        this.nlocals = nlocals;
        this.address = address;
    }

    String name() {
        return name;
    }

    int nargs() {
        return nargs;
    }

    int nlocals() {
        return nlocals;
    }

    int address() {
        return address;
    }

    @Override
    public String toString() {
        return "FuncMetaData{" +
            "name='" + name + '\'' +
            ", nargs=" + nargs +
            ", nlocals=" + nlocals +
            ", address=" + address +
            '}';
    }
}
