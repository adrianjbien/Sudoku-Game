package com.example.view;

import java.util.ListResourceBundle;

public class Developers extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {1, "Adrian Bień"},
                {2, "Wojciech Durys"}
        };
    }
}
