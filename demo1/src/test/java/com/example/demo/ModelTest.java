package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Object getPrivate(Object obj, String name) throws IllegalAccessException, NoSuchFieldException {
        Field field = mod.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }


    Model mod;

    @BeforeEach
    void setUp() {
        mod = new Model();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void edit() throws NoSuchFieldException, IllegalAccessException {
        mod.edit("sin");
        Field field = mod.getClass().getDeclaredField("text");
        field.setAccessible(true);
        assertEquals((String) field.get(mod), "sin(");
    }

    @Test
    void clean() throws NoSuchFieldException, IllegalAccessException {
        mod.edit("sin");
        Field field = mod.getClass().getDeclaredField("text");
        field.setAccessible(true);
        mod.clean();
        assertEquals((String) field.get(mod), "");
    }

    @Test
    void unknown_with_checking() throws NoSuchFieldException, IllegalAccessException {
        mod.edit("blabla");
        Field field = mod.getClass().getDeclaredField("text");
        field.setAccessible(true);
        mod.unknown_with_checking();
        assertEquals((String) field.get(mod), "input error ");
    }

    @Test
    void enter() throws NoSuchFieldException, IllegalAccessException {
        mod.edit("blabla");
        Field field = mod.getClass().getDeclaredField("text");
        field.setAccessible(true);
        mod.enter();
        assertEquals((String) field.get(mod), "input error ");
    }

    @Test
    void y_graf_func() {
        mod.parse("X*X");
        assertEquals(mod.y_graf_func(4), 16);

    }

    @Test
    void isHistEmpty() throws NoSuchFieldException, IllegalAccessException {
        boolean empty;

        Field field = mod.getClass().getDeclaredField("history");
        field.setAccessible(true);
        final History history = (History) field.get(mod);
        if (history.result_array().length == 0)
            empty = true;
        else
            empty = false;
        assertEquals(mod.isHistEmpty(), empty);
    }

    @Test
    void save_hist() throws NoSuchFieldException, IllegalAccessException {

        Model model1 = new Model();
        Field field = mod.getClass().getDeclaredField("history");
        field.setAccessible(true);
        final History history = (History) field.get(mod);

        model1.parse("2*2");
        model1.calc();
        model1.save_hist();
        Model model2 = new Model();
        History history1 = (History) getPrivate(model1, "history");
        History history2 = (History) getPrivate(model1, "history");
        assertEquals(history1.size(), history2.size());
        String[] str1 = history1.result_array();
        String[] str2 = history2.result_array();
        for (int i = 0; i < history2.size(); i++)
            assertEquals(str1[i], str2[i]);
        assertNotEquals(history.result_array(), history2.result_array());
        mod.save_hist();
    }

    @Test
    void takeHistoryUp() throws NoSuchFieldException, IllegalAccessException {

        int index = (int)getPrivate(mod, "histIndex");
        mod.takeHistoryUp();
        History hist = (History) getPrivate(mod, "history");
        if (hist.size() == 0)
            assertEquals(index, (int) getPrivate(mod, "histIndex"));
        else
            assertNotEquals(index, (int) getPrivate(mod, "histIndex"));
    }

    @Test
    void takeHistoryDown() throws NoSuchFieldException, IllegalAccessException {

        int index = (int)getPrivate(mod, "histIndex");
        mod.takeHistoryDown();
        History hist = (History)getPrivate(mod, "history");
        if (hist.size() == 0)
            assertEquals(index, (int) getPrivate(mod, "histIndex"));
        else
            assertNotEquals(index, (int) getPrivate(mod, "histIndex"));
    }
    @Test
    void HistoryClear() throws NoSuchFieldException, IllegalAccessException {

        Model model1 = new Model();
        Field field = mod.getClass().getDeclaredField("history");
        field.setAccessible(true);
        final History history = (History) field.get(mod);

        model1.parse("2*2");
        model1.calc();
        final History history1 = (History) field.get(model1);
        int  index = history1.result_array().length;
        assertNotEquals(index, 0);
        model1.HistoryClear();
        index = history1.result_array().length;
        assertEquals(index, 0);
        mod.save_hist();

    }
}


