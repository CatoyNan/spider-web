package com.catoy;

import org.apache.log4j.Logger;

import java.util.Map;
import java.text.DecimalFormat;

public class Hello {
    private static final Logger logger = Logger.getLogger(Hello.class);
    private final DecimalFormat df = new DecimalFormat("#.#####");

    public void calculate(Map<String, Double> data) {
        int a = 1/0;
    }
}