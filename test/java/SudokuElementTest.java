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
import org.example.SudokuRow;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * "SudokuElementTest class tests SudokuElement class."
 */
public class SudokuElementTest {

    /**
     * "This is SudokuElementTest constructor."
     */


    public SudokuElementTest() {

    }

    @Test
    void verifyTest() {
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[9]);


        for (int i = 0; i < 9; i++) {
            fields1.set(i,new SudokuField());
            fields1.get(i).setFieldValue(1);
        }

        List<SudokuField> fields2 = Arrays.asList(new SudokuField[9]);
        SudokuRow row2 = new SudokuRow();
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            fields2.set(i,new SudokuField());
            fields2.get(i).setFieldValue(0);
        }

        row2.setElement(fields2);
        row.setElement(fields1);
        assertFalse(row.verify());
        assertFalse(row2.verify());

        List<SudokuField> fields3 = Arrays.asList(new SudokuField[9]);
        SudokuColumn col = new SudokuColumn();

        for (int i = 0; i < 9; i++) {
            fields3.set(i,new SudokuField());
            fields3.get(i).setFieldValue(i + 1);
        }


        col.setElement(fields3);
        assertTrue(col.verify());
    }

    @Test
    void toStringTest() {
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[9]);
        SudokuRow row = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            fields1.set(i,new SudokuField());
            fields1.get(i).setFieldValue(i + 1);
        }
        row.setElement(fields1);
        final String result = "element=[1, 2, 3, 4, 5, 6, 7, 8, 9]";
        final String failure = "1, 2, 3, 4, 5, 6, 7, 8, 9";
        assertEquals(row.toString(),result);
        assertNotEquals(row.toString(),failure);

    }

    @Test
    void equalsTest() {
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[9]);
        List<SudokuField> fields2 = Arrays.asList(new SudokuField[9]);
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            fields1.set(i,new SudokuField());
            fields1.get(i).setFieldValue(i + 1);
            fields2.set(i,new SudokuField());
            fields2.get(i).setFieldValue(i + 1);
        }
        row1.setElement(fields1);
        row2.setElement(fields2);

        boolean flag1 = row1.equals(row2);
        assertTrue(flag1);

        SudokuRow row3 = null;
        boolean flag2 = row1.equals(row3);
        assertFalse(flag2);

        SudokuRow row4 = row1;
        boolean flag3 = row4.equals(row1);
        assertTrue(flag3);

        SudokuField field = new SudokuField();
        boolean flag4 = row1.equals(field);
        assertFalse(flag4);
    }

    @Test
    void hashCodeTest() {
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[9]);
        List<SudokuField> fields2 = Arrays.asList(new SudokuField[9]);
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            fields1.set(i,new SudokuField());
            fields1.get(i).setFieldValue(i + 1);
            fields2.set(i,new SudokuField());
            fields2.get(i).setFieldValue(i + 1);
        }
        row1.setElement(fields1);
        row2.setElement(fields2);

        assertEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    void cloneTest() {
        SudokuRow row1 = new SudokuRow();
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i,new SudokuField());
            fields.get(i).setFieldValue(1);
        }
        row1.setElement(fields);
        SudokuRow row2 = (SudokuRow) row1.clone();
        assertEquals(row1,row2);
    }
}

