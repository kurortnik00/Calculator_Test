package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            String s = reader.readLine();
            if(s.equals("q")) break;

            boolean roman = true;
            for (Character c : s.toCharArray()){
                if (Character.isDigit(c)) roman = false;
            }

            Calculator calculator;
            if(roman) calculator = new RomanCalculator();
            else calculator = new ArabCalculator();
            String answer  = calculator.evaluate(s);
            if(answer != null)   System.out.println(answer);

        }
    }
}
