package com.example.view;

import javafx.util.converter.IntegerStringConverter;

public class ZeroToStringConverter extends IntegerStringConverter {

    @Override
    public Integer fromString(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String toString(Integer integer) {
        if (integer == 0) {
            return " ";
        }
        return super.toString(integer);
    }
}
