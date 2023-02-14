package com.example.demo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class History implements Serializable {

    History() {
        size = 0;
    }
    private int size;
    private ArrayList<String> operations = new ArrayList<>();
    private ArrayList<String> results = new ArrayList<>();
    public void add_op(String op) {
        operations.add(op);
        size++;
    }
    public void add_res(String res) {
        results.add(operations.get(this.size - 1) + "=" + res);
    }
    public void clear(){
        operations.clear();
        results.clear();
    }
    public String[] result_array() {
        return (results.toArray(new String[0]));
    }
    int size()
    {
        return size;
    }
}
