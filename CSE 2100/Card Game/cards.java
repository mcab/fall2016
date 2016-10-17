// Mark Cabanero
// CSE 2100: Assignment 4, 24 Card Game
// October 16, 2016

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;

public class cards {
    private static boolean found = false;
    private static List<String> combinations = new LinkedList<String>();
    private static List<String[]> operatorCombinations = new LinkedList<String[]>();

    // Runs the main method.
    public static void main(String[] args) {
        generateCards();
        generateOperators();
        Iterator<String> combinationsIterator = combinations.iterator();
        while (combinationsIterator.hasNext()) {
            String cardCombination = combinationsIterator.next();
            System.out.println(convertValuesToFace(cardCombination) + ": ");
            testFor24(cardCombination);
        }
    }

    // testFor24() takes the String[] of the card's face values, finds its 
    // permutations, and calculates what each value results in.
    public static void testFor24(String c) {
        String[] cardArray = c.split(" ");
        int[] cardValues = new int[cardArray.length];
        for (int i = 0; i < cardArray.length; i++) {
            cardValues[i] = Integer.parseInt(cardArray[i]);
        }
        found = false;
        permute(cardValues, 0);
    }

    // permute(), calculate(), check(), and evaluatePostfix() all are used
    // in conjunction to evaluate a card set. This runs 24 * 5 * 64 times per
    // card set (each of the 24 unique permutations, has 5 unique strings, which
    // each has 64 unique operator combinations to test.)
    private static void permute(int[] i, int start) {
        // Uses recursion in order to generate the 24 permutations for each
        // unique combination.
        if (i.length == start + 1) {
            if (found == false) {
                calculate(i);
            }
        } else {
            for (int a = start; a < i.length; a++) {
                // Swaps two items in position, and reruns permute() on it.
                int temp = i[a];
                i[a] = i[start];
                i[start] = temp;
                permute(i, start + 1);
                temp = i[a];
                i[a] = i[start];
                i[start] = temp;
            }
        }
    }

    private static void calculate(int[] i) {
        // Creates the five expressions possible from the unique order from
        // the four numbers, as well as the three operators.
        StringBuilder expression = new StringBuilder();
        calculating: for (String[] operators : operatorCombinations) {
            expression.append(i[0] + " " + i[1] + " " + operators[0] + " " + i[2] + " " + i[3] + " " + operators[1] + " " + operators[2]);
            if (check(expression.toString())) { break calculating; }
            expression.setLength(0);

            expression.append(i[0] + " " + i[1] + " " + operators[0] + " " + i[2] + " " + operators[1] + " " + i[3] + " " + operators[2]);
            if (check(expression.toString())) { break calculating; }
            expression.setLength(0);

            expression.append(i[0] + " " + i[1] + " " + i[2] + " " + operators[0] + " " + i[3] + " " + operators[1] + " " + operators[2]);
            if (check(expression.toString())) { break calculating; }
            expression.setLength(0);

            expression.append(i[0] + " " + i[1] + " " + i[2] + " " + operators[0] + " " + operators[1] + " " + i[3] + " " + operators[2]);
            if (check(expression.toString())) { break calculating; }
            expression.setLength(0);

            expression.append(i[0] + " " + i[1] + " " + i[2] + " " + i[3] + " " + operators[0] + " " + operators[1] + " " + operators[2]);
            if (check(expression.toString())) { break calculating; }
            expression.setLength(0);
        }
    }

    private static boolean check(String expression) {
        // If we have an evaluated string equal to 24, then we know that
        // we can break out; we found our unique combination.
        if (evaluatePostfix(expression) == 24.0) {
            System.out.println(expression);
            evaluatePostfix(expression, true);
            found = true;
            return true;
        }
        return false;
    }

    private static double evaluatePostfix(String expression, boolean... s) {
        // boolean... s is an optional parameter which allows the expression
        // to be evaluated or not.
        boolean show = (s.length > 0) ? true : false;
        GenericStack<Double> stack = new ArrayStack<Double>();
        Scanner tokens = new Scanner(expression);
        StringBuilder infix = new StringBuilder();
        int count = 0;
        while (tokens.hasNext()) {
            if (tokens.hasNextInt()) {
                double number = (double) tokens.nextInt();
                stack.push(number);
            } else { 
                count++;
                double number2 = stack.pop();
                double number1 = stack.pop();
                String operator = tokens.next();

                switch (operator) {
                    case "+":   stack.push(number1 + number2);
                                if (show) { infix.append(number1 + " " + operator + " " + number2 + " = " + (number1 + number2)); }
                                break;
                    case "-":   stack.push(number1 - number2);
                                if (show) { infix.append(number1 + " " + operator + " " + number2 + " = " + (number1 - number2)); }
                                break;
                    case "*":   stack.push(number1 * number2);
                                if (show) { infix.append(number1 + " " + operator + " " + number2 + " = " + (number1 * number2)); }
                                break;
                    case "/":   stack.push(number1 / number2);
                                if (show) { infix.append(number1 + " " + operator + " " + number2 + " = " + (number1 / number2)); }
                                break;
                    default:    break;
                }
                if (count != 3) {
                    infix.append(", ");
                }
            }
        }
        if (show) { System.out.println(infix.toString()); }
        return stack.pop();
    }

    // generateOperators() and convertNumbersToOperators() generate the 64
    // operator combinations.
    private static void generateOperators() {
        for (int m = 0; m < 4; m++) {
            for (int n = 0; n < 4; n++) {
                for (int o = 0; o < 4; o++) {
                    // Get a list of 3 operators from the helper function.
                    String[] operators = convertNumbersToOperators(m, n, o);
                    // Add them to a list containing all the various combinations of operators.
                    operatorCombinations.add(operators);
                }
            }
        }
    }

    private static String[] convertNumbersToOperators(int a, int b, int c) {
        // Given 3 integers between 0 and 3, assign various operators for them.
        String[] operators = new String[3];
        int[] numbers = {a, b, c};
        for (int i = 0; i < 3; i++) {
            if (numbers[i] == 0) { operators[i] = "+"; }
            else if (numbers[i] == 1) { operators[i] = "-"; }
            else if (numbers[i] == 2) { operators[i] = "*"; }
            else if (numbers[i] == 3) { operators[i] = "/"; }
            else { }
        }
        return operators;
    }

    // generateCards(), convertValuesToFace(), and transformValues() creates
    // the unique 1,820 combinations for cards, and provides helper methods 
    // for other methods.
    private static void generateCards() {
        // Uses 4 for loops to create the 1,820 combinations.
        for (int a = 1; a < 14; a++) {
            for (int b = a; b < 14; b++) {
                for (int c = b; c < 14; c++) {
                    for (int d = c; d < 14; d++) {
                        // Convert integer arrays to face values.
                        int[] cardArray = {a, b, c, d};
                        StringBuilder cardString = new StringBuilder();
                        for (int x : cardArray) {
                            cardString.append(x + " ");
                        }
                        combinations.add(cardString.toString());
                    }
                }
            }
        }
    }

    private static String convertValuesToFace(String c) {
        // Passes each integer value to transformValues() to get the face card.
        String[] cardArray = c.split(" ");
        StringBuilder cardConverted = new StringBuilder();
        for (int i = 0; i < cardArray.length; i++) {
            if (i == (cardArray.length - 1)) {
                cardConverted.append(transformValues(cardArray[i]));
            } else {
                cardConverted.append(transformValues(cardArray[i]) + " ");               
            }
        }
        return cardConverted.toString();
    }
    
    private static String transformValues(String i) {
        // If values[1] = "A", then passing the integer value gives the proper
        // face value.
        String[] values = {"", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        return values[Integer.parseInt(i)];
    }
}