package de.weltraumschaf.caythe.backend.vm;

import de.weltraumschaf.commons.validate.Validate;

import java.io.PrintStream;

import static de.weltraumschaf.caythe.backend.vm.ByteCode.*;

public final class VirtualMachine {
    private static final long FALSE = 0L;
    private static final long TRUE = 1L;

    private final ByteFormatter formatter = new ByteFormatter();

    private final PointerRegister ip = new PointerRegister();
    private final PointerRegister sp = new PointerRegister(-1);
    private final PointerRegister fp = new PointerRegister();

    /**
     * global variable space
     */
    private final Memory globals = new Memory();
    private final Stack stack = new Stack();
    private final byte[] code;
    private final FunctionMetaData[] metadata;
    private final PrintStream out;

    public VirtualMachine(final byte[] code, final FunctionMetaData[] metadata, final PrintStream out) {
        super();
        this.code = Validate.notNull(code, "code");
        this.metadata = Validate.notNull(metadata, "");
        this.out = Validate.notNull(out, "out");
    }

    public void run(int startip) {
        ip.setTo(startip);
        // the active context
        Context ctx = new Context(metadata[0]);
        ByteCode opcode = ByteCode.decode(fetchAndIncrement());

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
                    push(ctx.getLocal(fetchInt()));
                    break;
                }
                case GLOAD: {
                    // load from global memory
                    final int addr = fetchInt();
                    push(globals.get(addr));
                    break;
                }
                case STORE: {
                    ctx.setLocal(fetchInt(), pop());
                    break;
                }
                case GSTORE: {
                    final int addr = fetchInt();
                    globals.set(addr, pop());
                    break;
                }
                case PRINT:
                    out.print(pop());
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
                        ctx.setLocal(i, stack.get(sp.down(i)));
                    }

                    sp.setTo(sp.down(nargs).current());
                    ip.setTo(metadata[findex].address()); // jump to function
                    break;
                }
                case RET:
                    ip.setTo(ctx.getReturnPointer());
                    ctx = ctx.getInvokingContext(); // pop
                    break;
                default:
                    throw new IllegalStateException();
            }

            opcode = ByteCode.decode(fetchAndIncrement());
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
