package de.weltraumschaf.caythe.backend.vm;

import java.io.PrintStream;

import static de.weltraumschaf.caythe.backend.vm.ByteCode.*;

final class VirtualMachine {
    private static final long FALSE = 0L;
    private static final long TRUE = 1L;

    private final ByteFormatter formatter = new ByteFormatter();

    private final PointerRegister ip = new PointerRegister();
    private final PointerRegister sp = new PointerRegister(-1);
    private final PointerRegister fp = new PointerRegister();

    private final Memory data = new Memory();
    private final Stack stack = new Stack();
    private final byte[] code;
    /**
     * global variable space
     */
    private final long[] globals;
    private final FuncMetaData[] metadata;
    private final PrintStream out;

    VirtualMachine(final byte[] code, int nglobals, FuncMetaData[] metadata, final PrintStream out) {
        super();
        this.code = code;
        this.globals = new long[nglobals];
        this.metadata = metadata;
        this.out = out;
    }

    void run(int startip) {
        ip.setTo(startip);
        // the active context
        Context ctx = new Context(null, 0, metadata[0]);
        byte opcode = fetchAndIncrement();

        while (opcode != HALT && ip.current() < code.length) {
            switch (opcode) {
                case IADD: {
                    final long b = pop();
                    final long a = pop();
                    push(a + b);
                    break;
                }
                case ISUB: {
                    final long b = pop();
                    final long a = pop();
                    push(a - b);
                    break;
                }
                case IMUL: {
                    final long b = pop();
                    final long a = pop();
                    push(a * b);
                    break;
                }
                case ILT: {
                    final long b = pop();
                    final long a = pop();
                    push((a < b) ? TRUE : FALSE);
                    break;
                }
                case IEQ: {
                    final long b = pop();
                    final long a = pop();
                    push((a == b) ? TRUE : FALSE);
                    break;
                }
                case BR: {
                    ip.setTo(fetchInt());
                    break;
                }
                case BRT: {
                    final int addr = fetchInt();

                    if (pop() == TRUE) {
                        ip.setTo(addr);
                    }

                    break;
                }
                case BRF: {
                    final int addr = fetchInt();

                    if (pop() == FALSE) {
                        ip.setTo(addr);
                    }

                    break;
                }
                case ICONST:
                    push(fetchLong());
                    break;
                case LOAD: {
                    // load local or arg; 1st local is fp+1, args are fp-3, fp-4, fp-5, ...
                    final int regnum = fetchInt();
                    push(ctx.locals[regnum]);
                    break;
                }
                case GLOAD: {
                    // load from global memory
                    final int addr = fetchInt();
                    push(globals[addr]);
                    break;
                }
                case STORE: {
                    final int regnum = fetchInt();
                    ctx.locals[regnum] = pop();
                    break;
                }
                case GSTORE: {
                    final int addr = fetchInt();
                    globals[addr] = pop();
                    break;
                }
                case PRINT:
                    out.println(pop());
                    break;
                case POP:
                    pop();
                    break;
                case CALL: {
                    // expects all args on stack
                    final int findex = fetchInt();       // index of target function

                    final int nargs = metadata[findex].nargs();    // how many args got pushed
                    ctx = new Context(ctx, ip.current(), metadata[findex]);
                    // copy args into new context

                    for (int i = 0; i < nargs; i++) {
                        ctx.locals[i] = stack.get(sp.down(i));
                    }

                    sp.setTo(sp.down(nargs).current());
                    ip.setTo(metadata[findex].address()); // jump to function
                    break;
                }
                case RET:
                    ip.setTo(ctx.returnip);
                    ctx = ctx.invokingContext; // pop
                    break;
                default:
                    throw new IllegalStateException();
            }

            opcode = fetch();
        }
    }

    private void push(final long value) {
        sp.increment();
        stack.push(value);
    }

    private long pop() {
        sp.decrement();
        return stack.pop();
    }

    private byte fetch() {
        return code[ip.current()];
    }

    private byte fetchAndIncrement() {
        final byte current = fetch();
        ip.increment();
        return current;
    }

    private long fetchLong() {
        final byte[] bytes = new byte[8];
        bytes[0] = fetchAndIncrement();
        bytes[1] = fetchAndIncrement();
        bytes[2] = fetchAndIncrement();
        bytes[3] = fetchAndIncrement();
        bytes[4] = fetchAndIncrement();
        bytes[5] = fetchAndIncrement();
        bytes[6] = fetchAndIncrement();
        bytes[7] = fetchAndIncrement();

        return formatter.toLong(bytes);
    }

    private int fetchInt() {
        final byte[] bytes = new byte[4];
        bytes[0] = fetchAndIncrement();
        bytes[1] = fetchAndIncrement();
        bytes[2] = fetchAndIncrement();
        bytes[3] = fetchAndIncrement();

        return formatter.toInt(bytes);
    }
}
