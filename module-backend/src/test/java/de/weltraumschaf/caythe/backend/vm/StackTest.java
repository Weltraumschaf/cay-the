package de.weltraumschaf.caythe.backend.vm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class StackTest {

    private final Stack sut = new Stack();

    @Test
    public void size_byDefaultZero() {
        assertThat(sut.size(), is(0));
    }

    @Test(expected = IllegalStateException.class)
    public void popEmptyStackNotAllowed() {
        sut.pop();
    }

    @Test
    public void pushAndPopOneValue() {
        sut.push(23L);

        assertThat(sut.size(), is(1));

        assertThat(sut.pop(), is(23L));
    }

    @Test
    public void pushAndPopTwoValue() {
        sut.push(23L);
        sut.push(42L);

        assertThat(sut.size(), is(2));

        assertThat(sut.pop(), is(42L));
        assertThat(sut.pop(), is(23L));
    }

    @Test
    public void pushAndPopThreeValue() {
        sut.push(1L);
        sut.push(2L);
        sut.push(3L);

        assertThat(sut.size(), is(3));

        assertThat(sut.pop(), is(3L));
        assertThat(sut.pop(), is(2L));
        assertThat(sut.pop(), is(1L));
    }

    @Test
    public void get() {
        sut.push(1L);
        sut.push(2L);
        sut.push(3L);
        final PointerRegister p = new PointerRegister();

        p.setTo(0);
        assertThat(sut.get(p), is(1L));

        p.setTo(1);
        assertThat(sut.get(p), is(2L));

        p.setTo(2);
        assertThat(sut.get(p), is(3L));
    }

    @Test(expected = IllegalStateException.class)
    public void getBeyondSizeNotAllowed() {
        sut.push(1L);
        sut.push(2L);
        sut.push(3L);
        final PointerRegister p = new PointerRegister();
        p.setTo(3);

        sut.get(p);
    }

    @Test
    public void popDownTo() {
        sut.push(1L);
        sut.push(2L);
        sut.push(3L);
        sut.push(4L);
        sut.push(5L);
        final PointerRegister p = new PointerRegister();
        p.setTo(2);
        sut.popDownTo(p);

        assertThat(sut.pop(), is(3L));
    }

    @Test
    public void dump() {
        assertThat(sut.dump(), is("[]"));

        sut.push(1L);
        assertThat(sut.dump(), is("[1]"));

        sut.push(2L);
        assertThat(sut.dump(), is("[1, 2]"));

        sut.push(3L);
        assertThat(sut.dump(), is("[1, 2, 3]"));

        sut.push(4L);
        assertThat(sut.dump(), is("[1, 2, 3, 4]"));

        sut.push(5L);
        assertThat(sut.dump(), is("[1, 2, 3, 4, 5]"));

        sut.pop();
        sut.pop();
        assertThat(sut.dump(), is("[1, 2, 3]"));
    }
}