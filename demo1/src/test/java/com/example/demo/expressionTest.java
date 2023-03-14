package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class expressionTest {

    expression exp;
    @BeforeEach
    void setUp() {
        expression exp = new expression();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void rebuild_ex() throws NoSuchFieldException, IllegalAccessException {
        expression mod = new expression();
        mod.parse("X*X");
        mod.X_pre();
        Field field = mod.getClass().getDeclaredField("half_done");
        field.setAccessible(true);
        assertEquals((Boolean)field.get(mod),true );
        mod.rebuild_ex("4*4");
        assertEquals((Boolean)field.get(mod),false);
    }

    @Test
    void get_X() throws IllegalAccessException, NoSuchFieldException {
        expression mod = new expression();
        mod.get_X(4.4);
        Field field = mod.getClass().getDeclaredField("X");
        field.setAccessible(true);
        assertEquals((Double) field.get(mod),4.4 );
    }

    @Test
    void reverse_stack() throws NoSuchFieldException, IllegalAccessException {
        Stack<String> stack = new Stack<>();
        expression mod = new expression();

        stack.push("");
        stack.push("*");
        stack.push("2");
        stack.push("2");
        stack.push("");
        stack.push("*");
        stack.push("2");
        stack.push("2");
        mod.parse("2*2");
        mod.reverse_stack();
        Field field = mod.getClass().getDeclaredField("stack");
        field.setAccessible(true);
        assertEquals((Stack<String>)field.get(mod), stack);

    }

    @Test
    void parse_half() throws NoSuchFieldException, IllegalAccessException {

        expression mod = new expression();
        mod.parse_half("4.4");
        Field field = mod.getClass().getDeclaredField("X");
        field.setAccessible(true);
        assertEquals((Double) field.get(mod),4.4 );
    }

    @Test
    void parse() throws NoSuchFieldException, IllegalAccessException {
        Stack<String> stack = new Stack<>();
        expression mod = new expression();

        stack.push("");
        stack.push("*");
        stack.push("2");
        stack.push("2");
        mod.parse("2*2");
        Field field = mod.getClass().getDeclaredField("stack");
        field.setAccessible(true);
        assertEquals((Stack<String>)field.get(mod), stack);
    }

    @Test
    void x_pre() throws NoSuchFieldException, IllegalAccessException {
        expression mod = new expression();
        mod.parse("X*X");
        mod.X_pre();
        Field field = mod.getClass().getDeclaredField("half_done");
        field.setAccessible(true);
        assertEquals((Boolean)field.get(mod),true );
    }

    @Test
    void calc() {
        expression mod = new expression();
        mod.parse("2*2");
        assertEquals(mod.calc(), 4.0);

    }
}