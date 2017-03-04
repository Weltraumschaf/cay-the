package de.weltraumschaf.caythe.backend.vm;

public final class FunctionMetaData {
    private final String name;
    private final int numberOfArguments;
    private final int numberOfLocals;
    /**
     * bytecode address
     */
    private final int address;

    FunctionMetaData(final String name, final int numberOfArguments, final int numberOfLocals, final int address) {
        super();
        this.name = name;
        this.numberOfArguments = numberOfArguments;
        this.numberOfLocals = numberOfLocals;
        this.address = address;
    }

    String name() {
        return name;
    }

    int nargs() {
        return numberOfArguments;
    }

    int nlocals() {
        return numberOfLocals;
    }

    int address() {
        return address;
    }

    @Override
    public String toString() {
        return "FunctionMetaData{" +
            "name='" + name + '\'' +
            ", numberOfArguments=" + numberOfArguments +
            ", numberOfLocals=" + numberOfLocals +
            ", address=" + address +
            '}';
    }
}
