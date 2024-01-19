/**
 * Copyright (C) 2023  Adrian Bień, Wojciech Durys
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */

import org.example.SudokuColumn;
import org.example.SudokuField;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "SudokuFieldTest class tests SudokuField class."
 */
public class SudokuFieldTest {

    /**
     * "This is SudokuFieldTest constructor."
     */

    public SudokuFieldTest() {

    }

    @Test
    void setterTest() {
        SudokuField fieldRight = new SudokuField();
        SudokuField fieldWrong = new SudokuField();

        fieldRight.setFieldValue(9);
        assertEquals(9, fieldRight.getFieldValue());

        fieldWrong.setFieldValue(19);
        assertEquals(0, fieldWrong.getFieldValue());

        SudokuField fieldZero = new SudokuField();
        SudokuField fieldNegative = new SudokuField();

        fieldZero.setFieldValue(0);
        assertEquals(0, fieldZero.getFieldValue());

        fieldNegative.setFieldValue(-2);
        assertEquals(0, fieldNegative.getFieldValue());
    }

    @Test
    void toStringTest() {
        SudokuField field = new SudokuField();
        field.setFieldValue(9);
        final String result = "9";
        assertEquals("9",field.toString());
    }

    @Test
    void equalsTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(1);
        boolean flag1 = field1.equals(field2);

        assertTrue(flag1);


        SudokuField field11 = new SudokuField();
        SudokuField field22 = new SudokuField();
        field11.setFieldValue(1);
        field22.setFieldValue(2);
        boolean flag = field11.equals(field22);

        assertFalse(flag);

        SudokuField field3 = field1;
        boolean flag2 = field3.equals(field1);
        assertTrue(flag2);

        SudokuField field4 = null;
        boolean flag3 = field1.equals(field4);
        assertFalse(flag3);

        SudokuColumn column = new SudokuColumn();
        boolean flag4 = field1.equals(column);
        assertFalse(flag4);
    }

    @Test
    void hashCodeTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(1);
        assertEquals(field1.hashCode(), field2.hashCode());
    }

    @Test
    void compareToTest() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(1);
        assertEquals(0,field1.compareTo(field2));
        field2.setFieldValue(2);
        assertEquals(-1,field1.compareTo(field2));
        field1.setFieldValue(3);
        assertEquals(1,field1.compareTo(field2));
        field2 = null;
        SudokuField finalField = field2;
        Throwable exception = assertThrows(RuntimeException.class, () -> field1.compareTo(finalField));
        assertNull(exception.getMessage());
    }
}

