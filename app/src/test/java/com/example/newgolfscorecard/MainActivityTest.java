package com.example.newgolfscorecard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

class MainActivityTest {

    MainActivity mainActivity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mainActivity = new MainActivity();
    }

    @Test
    void saveData() {
    }

    @Test
    void emailData() {
    }

    @Test
    void printHeaderRow() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);

        mainActivity.printHeaderRow(pw);

        String s = out.toString();
        assertThat(s, is("test"));

    }
}