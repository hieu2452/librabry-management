package com.demo.book.UnitTest.service;

import com.demo.book.utils.MathUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class MathUtilTest {
    @Test
    public void divide_SixDividedByTwo_ReturnThree() {
        final int expected = 3;

        final int actual = MathUtil.divide(6, 2);

        assertEquals(expected, actual);
    }
    @Test
    public void divide_OneDividedByTwo_ReturnZero() {
        final int expected = 0;

        final int actual = MathUtil.divide(1, 2);

        assertEquals(expected, actual);
    }
    @Test
    public void divide_OneDividedByZero_ThrowsIllegalArgumentException() {

        assertThrowsExactly(IllegalArgumentException.class,() -> MathUtil.divide(1, 0));
    }
    @Test
    public void add_SixAddedByTwo_ReturnEight() {
        final int expected = 8;

        final int actual = MathUtil.add(6, 2);

        assertEquals(expected, actual);
    }
}
