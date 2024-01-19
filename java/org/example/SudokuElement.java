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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * "SudokuElement class is parent class of SudokuRow, SudokuColumn and SudokuBox classes."
 */

public abstract class SudokuElement implements Serializable, Cloneable {

    private static final Logger logger = LogManager.getLogger("model");

    private List<SudokuField> element = Arrays.asList(new SudokuField[9]);

    /**
     * "This is SudokuElement constructor that initializes element field."
     */

    public SudokuElement() {
        for (int i = 0; i < 9; i++) {
            element.set(i,new SudokuField());
        }
    }

    /**
     * "setElement method sets element field."
     * @param fields fields that will be passed to element field.
     */
    public void setElement(List<SudokuField> fields) {
        for (int i = 0; i < 9; i++) {
            element.set(i,fields.get(i));
        }
    }

    /**
     * "verify method checks if set numbers in certain row, column or box repeats."
     * @return true if every value is unique within certain row, column or box.
     */
    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (element.get(i).getFieldValue() == element.get(j).getFieldValue()
                        || element.get(i).getFieldValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) { // sprawdzamy czy referencja odnosi sie do tego samego obiektu
            return true;
        }
        if (object == null || getClass() != object.getClass()) { //sprawdzamy czy jest nullem lub czy porownywane
            return false;                                        //obiekty sa instancjami roznych klas.
        }                                                       //jesli nie to rzutujemy argument i porownujemy metoda
        SudokuElement element1 = (SudokuElement) object; //equals jednakze inna( equals jest przeciazone )
        return Objects.equal(element, element1.element); // ktora patrzy poprzez ==, sprawdza nulla i
    }               //sprawdza equals juz ta w postaci a.equals(b) -czyli ta pierwotna

    @Override
    public int hashCode() {
        return Objects.hashCode(element);
    }

    @Override
    public String toString() {
        return "element=" + element;
    }

    @Override
    public SudokuElement clone() {
        try {
            SudokuElement clone = (SudokuElement) super.clone();
            for (int i = 0; i < 9; i++) {
                clone.element.set(i, new SudokuField().clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle.getString("elementClone"));
            throw new SudokuException.SudokuCloneNotSupportedException(bundle.getString("elementClone"));
        }
    }
}
