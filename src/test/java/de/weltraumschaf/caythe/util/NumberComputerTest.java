package de.weltraumschaf.caythe.util;

import de.weltraumschaf.caythe.util.NumberComputer.RangeException;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NumberComputerTest {

    @Test public void computeIntegerValue() throws RangeException {
        assertEquals(23, NumberComputer.computeIntegerValue("23"));
        assertEquals(42, NumberComputer.computeIntegerValue("42"));
        assertEquals(0, NumberComputer.computeIntegerValue(null));
        assertEquals(0, NumberComputer.computeIntegerValue("0"));
        assertEquals(0, NumberComputer.computeIntegerValue("000"));
        assertEquals(1, NumberComputer.computeIntegerValue("01"));
        assertEquals(1, NumberComputer.computeIntegerValue("0001"));
        assertEquals(123456789, NumberComputer.computeIntegerValue("123456789"));

        try {
            NumberComputer.computeIntegerValue("-23");
            fail("Should throw range exception.");
        } catch (RangeException ex) {
            assertEquals(RangeException.RANGE_INTEGER, ex.getCode());
            assertEquals("Given integer is out of range: -23", ex.getMessage());
        }

        try {
            NumberComputer.computeIntegerValue("-42");
            fail("Should throw range exception.");
        } catch (RangeException ex) {
            assertEquals(RangeException.RANGE_INTEGER, ex.getCode());
            assertEquals("Given integer is out of range: -42", ex.getMessage());
        }

        try {
            NumberComputer.computeIntegerValue("-0");
            fail("Should throw range exception.");
        } catch (RangeException ex) {
            assertEquals(RangeException.RANGE_INTEGER, ex.getCode());
            assertEquals("Given integer is out of range: -0", ex.getMessage());
        }

        try {
            NumberComputer.computeIntegerValue("-0001");
            fail("Should throw range exception.");
        } catch (RangeException ex) {
            assertEquals(RangeException.RANGE_INTEGER, ex.getCode());
            assertEquals("Given integer is out of range: -0001", ex.getMessage());
        }

        try {
            NumberComputer.computeIntegerValue("123456789123456789123456789123456789");
            fail("Should throw range exception.");
        } catch (RangeException ex) {
            assertEquals(RangeException.RANGE_INTEGER, ex.getCode());
            assertEquals("Given integer is out of range: 123456789123456789123456789123456789", ex.getMessage());
        }

    }

    private static float DELTA = 0.00001f;

    @Test public void computeFloatValue() throws RangeException {
        assertEquals(3.14f, NumberComputer.computeFloatValue("3", "14"), DELTA);
        assertEquals(2.718281828459045235f, NumberComputer.computeFloatValue("2", "718281828459045235"), DELTA);
        assertEquals(1234.5678f, NumberComputer.computeFloatValue("1234", "5678"), DELTA);
        assertEquals(3140.0f, NumberComputer.computeFloatValue("3", "14", "3", '+'), DELTA);
        assertEquals(3.14f, NumberComputer.computeFloatValue("314", "0", "2", '-'), DELTA);
    }
}
