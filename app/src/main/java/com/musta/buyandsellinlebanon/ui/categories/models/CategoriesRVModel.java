package com.musta.buyandsellinlebanon.ui.categories.models;

public class CategoriesRVModel {

    private String text;
    private String value;

    public CategoriesRVModel() {
    }

    public CategoriesRVModel(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
