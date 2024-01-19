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

package org.example;

import com.google.common.base.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * "SudokuField class represents cell of the board that stores value."
 */
public class SudokuField implements Comparable<SudokuField>, Serializable, Cloneable {

    private static final Logger logger = LogManager.getLogger("model");

    private int value;

    /**
     * "This is SudokuField constructor."
     */
    public SudokuField() {

    }

    /**
     * "getFieldValue method gets a value from value field."
     * @return integer - value of value field.
     */
    public int getFieldValue() {
        return this.value;
    }

    /**
     * "setFieldValue method sets value field by given value and checks if it is range (0, 9)."
     * @param value specifies the value that will be put into cell.
     */

    public void setFieldValue(int value) {
        if (value >= 0 && value < 10) {
            this.value = value;
        } else {
            this.value = 0;
        }
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SudokuField field = (SudokuField) object;
        return value == field.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(SudokuField o) throws NullPointerException {
        if (o == null) {
            throw new NullPointerException();
        } else {
            return this.value - o.value;
        }
    }

    @Override
    public SudokuField clone() {
        try {
            SudokuField clone = (SudokuField) super.clone(); //shallow copy
            return clone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle.getString("fieldClone"));
            throw new SudokuException.SudokuNullException(bundle.getString("fieldClone"));
        }
    }
}
