package com.company;

import java.util.Stack;


public class Calculator {

    public String evaluate(String statement){

        statement = statement.replaceAll(" ", "");

        try {
            errorCheck(statement);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }


        try {
            double ans = reverceExpression(changeExpression(statement));

            //check if the answer has a fractional part
            if (Double.isInfinite(ans)) return null;
            if ((10.0 / (int) ans) == (10 / ans)) {
                int a = (int) ans;
                return (Integer.toString(a));
            } else return (Double.toString(ans));
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Illegal input");
        }
        return null;

    }


    //transformation to Reverse Polish notation
    protected static String changeExpression(String input){
        String output = "";
        Stack<Character> stack = new Stack<Character>();

        for(int i=0; i < input.length(); i++){
            if (Character.isDigit(input.toCharArray()[i])){
                while (!isOperator(input.toCharArray()[i])){
                    output += input.toCharArray()[i];
                    i++;
                    if (i == input.length()) break;
                }
                output += " ";
                i--;
            }

            if(isOperator(input.toCharArray()[i])){
                if (input.toCharArray()[i] == '('){
                    stack.push(input.toCharArray()[i]);
                }
                else if(input.toCharArray()[i] == ')'){
                    char c = stack.pop();
                    while (c != '('){
                        output += c + " ";
                        c = stack.pop();
                    }
                }
                else {
                    if(!stack.empty()) {
                        if (priority(input.toCharArray()[i]) <= priority(stack.peek())) {
                            output += stack.pop() + " ";
                        }
                    }
                    stack.push(input.toCharArray()[i]);
                }
            }

        }
        while (!stack.empty()){
            output += stack.pop() + " ";
        }
        return output;
    }

    //revere transformation
    protected static double reverceExpression(String input){
        double result = 0;
        Stack<Double> stack = new Stack<>();

        for(String token : input.split(" ")){
            if(isOperator(token.toCharArray()[0])){
                double a = stack.pop();
                double b = stack.pop();
                switch (token.toCharArray()[0]){
                    case '+':
                        result = b + a;
                        break;
                    case '-':
                        result = b - a;
                        break;
                    case  '*':
                        result = b * a;
                        break;
                    case '/':
                        result = b / a;
                        break;
                }
                stack.push(result);
            }
            else {
                stack.push(Double.parseDouble(token));
            }
        }
        return stack.peek();
    }

    //Check input string
    protected static boolean errorCheck(String input) throws IllegalArgumentException {

        IllegalArgumentException illegalArgumentException;

        if (input == null) {
            throw new IllegalArgumentException("input is Empty");
        }

        if (input.equals("")) throw  new IllegalArgumentException("input is Empty");


        for (int i = 0; i < input.length() - 1; i++){

            //Проверка на 2 подряд идущих "оператора"
            if (isOperator_forErrors(input.toCharArray()[i]) && (isOperator_forErrors(input.toCharArray()[i + 1]))){
                throw  new IllegalArgumentException("check the string, should not go 2 operations(such as (+-*/.)) in a row");
            }

            //Проверка, что нет ситуциий подряд идущей открывающей скобки и оператора
            if ((input.toCharArray()[i] == '(') && (isOperator_forErrors(input.toCharArray()[i + 1]))){
                throw  new IllegalArgumentException("check the string, should not go \"(\" and operator in a row");
            }

            //Проверка что нет ситуаций подряд удщих числа и открывающей скобки
            if ((input.toCharArray()[i+1] == '(') && (Character.isDigit(input.toCharArray()[i]))){
                throw  new IllegalArgumentException("check the string, should not go \"(\" and number in a row");
            }

            //Проверка, что нет ситуциий подряд идущей закрывающей скобки и числа
            if ((input.toCharArray()[i] == ')') && (Character.isDigit(input.toCharArray()[i + 1]))){

                throw  new IllegalArgumentException("check the string, should not go \")\" and number in a row");
            }

            //Проверка, что нет ситуциий подряд идущего оператора и закрывающей строчки
            if ((input.toCharArray()[i+1] == ')') && (isOperator_forErrors(input.toCharArray()[i]))){
                throw  new IllegalArgumentException("check the string, should not go \")\" and operator in a row");
            }

            //Проверка на идущие подряд закрывающая скобка и открывающая
            if ((input.toCharArray()[i] == ')') && (input.toCharArray()[i+1] == '(')){
                throw  new IllegalArgumentException("check the string, should not go \")\" and \"(\"  in a row");
            }
        }

        //Проверка есть ли в записи не допустимый символ
        for (Character c : input.toCharArray()){
            if ("+-*/().1234567890".indexOf(c) == -1) throw new IllegalArgumentException("not allowed characters");
        }

        //Проверка сбалансированности строки скобками
        Stack<Character> stack = new Stack<>();
        for(char c : input.toCharArray()){
            if (c == '('){
                stack.push(c);
            }
            else if (c == ')'){
                if (stack.empty()) throw new IllegalArgumentException("unbalanced brackets");
                else {
                    stack.pop();
                }
            }
        }
        if (!stack.empty()) throw new IllegalArgumentException("unbalanced brackets");;

        return false;
    }

    //Function to define "operator" or not "operator" to convert to  RPN
    private static boolean isOperator(char c)
    {
        if ("+-*/()".indexOf(c) != -1) {
            return true;
        }
        return false;
    }

    //Function for determining "operator" or not "operator" (those that cannot go in a row will be checked)
    private static boolean isOperator_forErrors(char c)
    {
        if ("+-*/.".indexOf(c) != -1) {
            return true;
        }
        return false;
    }

    //operators priority to convert to RPN
    private static Integer priority(char c){
        switch (c){
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 2;
            case '*':
                return 3;
            case '/':
                return 3;
            default: return 6;
        }
    }

}
