package com.company;

import java.util.ArrayList;

public class RomanCalculator extends Calculator {

    public String evaluate(String statement) {
            //to avoid problem with spaces
            //you can use "a + b" and "a+b" or "a+ b"
            //add more spaces before and after each operator
            ArrayList<String> arrayList = new ArrayList<>();
            for(Character c : statement.toCharArray()){
                if(c.equals('.')) throw new IllegalArgumentException("roman numbers can not be fractional");
                if("IVX".indexOf(c) == -1) arrayList.add(" " + c.toString() + " ");
                else arrayList.add(c.toString());
            }
            //tmp string for convert from array list
            String tmpString = "";
            for(String s : arrayList){
                tmpString += s;
            }
            //now split each numbers and operators in to different strings
            String[] strings = tmpString.split(" ");
            String ss = "";
            for (String s : strings){
                try {
                    //if s != operator or 0 add to result string
                    if(Converter.romanToArabic(s) != 0) ss += Converter.romanToArabic(s);
                }
                catch (IllegalArgumentException e)
                {
                    //if can not convert it means its operator or illegal char (not important now)
                    ss += s;
                }
            }
            statement = ss;
            //hear we have string contains only arab numbers, operators and probably illegal characters
            Integer i = null;
            try {
                i = Integer.parseInt(super.evaluate(statement));
            }
            catch (Exception e)
            {
                System.out.println("answer roman number can not be fractional");
                return null;
            }

        return Converter.arabicToRoman(i);
    }
}
