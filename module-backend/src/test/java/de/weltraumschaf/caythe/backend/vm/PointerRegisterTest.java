package de.weltraumschaf.caythe.backend.vm;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PointerRegisterTest {

    private final PointerRegister sut = new PointerRegister();

    @Test
    public void currentIsZeroByDefault() {
        assertThat(sut.current(), is(0));
    }

    @Test
    public void increment() {
        sut.increment();
        assertThat(sut.current(), is(1));

        sut.increment();
        assertThat(sut.current(), is(2));

        sut.increment();
        assertThat(sut.current(), is(3));

        sut.increment();
        assertThat(sut.current(), is(4));
    }

    @Test
    public void setTo() {
        sut.setTo(23);

        assertThat(sut.current(), is(23));
    }

    @Test
    public void decrement() {
        sut.setTo(5);

        sut.decrement();
        assertThat(sut.current(), is(4));

        sut.decrement();
        assertThat(sut.current(), is(3));

        sut.decrement();
        assertThat(sut.current(), is(2));

        sut.decrement();
        assertThat(sut.current(), is(1));

        sut.decrement();
        assertThat(sut.current(), is(0));

        sut.decrement();
        assertThat(sut.current(), is(-1));
    }
}