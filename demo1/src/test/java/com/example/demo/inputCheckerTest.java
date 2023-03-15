package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class inputCheckerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void scopeCheck() {
        assertTrue(inputChecker.scopeCheck("()"));
        assertFalse(inputChecker.scopeCheck(")("));
    }

    @Test
    void check() {
    }

    @Test
    void halfCheck() {
        assertTrue(inputChecker.halfCheck("enter x: 5.5"));
        assertTrue(inputChecker.halfCheck("input error\n try other x: 5.5"));
        assertFalse(inputChecker.halfCheck("5.5"));
    }
}