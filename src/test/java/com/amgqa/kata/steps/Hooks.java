package com.amgqa.kata.steps;

import com.amgqa.kata.config.BaseApiTest;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setupApi() {
        BaseApiTest.init();
    }
}

