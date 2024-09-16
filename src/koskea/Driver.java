/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Austin Koske
 * Last Updated: 9-12-2024
 */
package koskea;

import java.util.InputMismatchException;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Driver --- program to request a user to input dice count, sides per dice, and total roll count,
 * and then create a histogram based off of the results of the rolls.
 * @author    Austin Koske
 */
public class Driver {
    /**
     * The minimum possible number of dice able to be simulated
     */
    public static final int MIN_DICE = 2;

    /**
     * The maximum possible number of dice able to be simulated
     */
    public static final int MAX_DICE = 10;

    /**
     * Ask the user for their inputs for dice count, sides per die, and amount of rolls
     * @return An int array of configuration values
     * @throws InputMismatchException -- Invalid input, message provided
     */
    private static int[] getInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the number of dice to roll, "
                + "how many sides the dice have, and how many rolls to complete,"
                + "separating the values by a space.");
        System.out.println("Example: \"2 6 1000\"");
        System.out.println();
        System.out.print("Enter configuration: ");

        String[] stringInput = scan.nextLine().split(" ");

        if (stringInput.length != 3) {
            throw new InputMismatchException("Invalid input: Expected 3 values but only received "
                    + stringInput.length + ".");
        }

        int[] config = new int[stringInput.length];

        for (int i = 0; i < config.length; i++) {
            try {
                config[i] = Integer.parseInt(stringInput[i]);
            } catch (NumberFormatException e) {
                throw new InputMismatchException("Invalid input: "
                        + "All values must be whole numbers.");
            }
        }

        if (config[1] < Die.MIN_SIDES || config[1] > Die.MAX_SIDES) {
            throw new InputMismatchException("Bad die creation: "
                    + "Illegal number of sides: " + config[1] + ".");
        }

        if (config[0] < MIN_DICE || config[0] > MAX_DICE) {
            throw new InputMismatchException("Bad dice creation: "
                    + "Illegal number of dice: " + config[0] + ".");
        }

        return config;
    }

    /**
     * Creates a Die array with the given configuration values
     * @return Die array
     */
    private static Die[] createDice(int numDice, int numSides) {
        Die[] dice = new Die[numDice];

        for (int i = 0; i < numDice; i++) {
            dice[i] = new Die(numSides);
        }

        return dice;
    }

    /**
     * Roll the provided Die array
     * @return An int array of frequencies
     */
    private static int[] rollDice(Die[] dice, int numSides, int numRolls) {
        int[] results = new int[(dice.length * numSides) - dice.length + 1];

        for (int i = 0; i < numRolls; i++) {
            int total = 0;

            for (Die die : dice) {
                die.roll();
                total += die.getCurrentValue();
            }

            results[total - dice.length]++;
        }

        return results;
    }

    /**
     * Finds the max value in the provided array
     * @return Max value
     * @throws NoSuchElementException -- Array is empty
     */

    private static int findMax(int[] rolls) {
        OptionalInt maxRoll = Arrays.stream(rolls).max();
        if (maxRoll.isPresent()) {
            return maxRoll.getAsInt();
        } else {
            throw new NoSuchElementException("Array is empty");
        }
    }

    /**
     * Print the results of the rolled dice in a histogram format, with each asterisk (*)
     * being 10% of the total frequency
     */

    private static void report(int numDice, int[] rolls, int max) {
        final int totalRolls = IntStream.of(rolls).sum();
        final int scaleMultiplier = 10;
        final double scale = max < scaleMultiplier ?
                (max / (double) totalRolls) :
                (max / (double) scaleMultiplier);
        int maxPaddingLength = (int) Math.floor(Math.log10(rolls.length-1)) + 1;
        int maxResultPaddingLength = (int) Math.floor(Math.log10(totalRolls)) + 1;

        for (int i = 0; i < rolls.length; i++) {
            int numLength = (int) Math.floor(Math.log10(i+numDice)) + 1;
            String padding = "";
            String resultPadding = "";

            if (numLength < maxPaddingLength) {
                padding = " ".repeat(maxPaddingLength - numLength);
            }

            int resultNumLength = rolls[i] != 0 ? (int) Math.floor(Math.log10(rolls[i])) + 1 : 1;
            if (resultNumLength < maxResultPaddingLength) {
                resultPadding = " ".repeat(maxResultPaddingLength-resultNumLength);
            }

            int rollNumber = i + numDice;
            int starCount = (int) ((rolls[i] / scale));
            String stars = "*".repeat(starCount);

            System.out.printf("%d%s: %d %s%s\n",
                    rollNumber, padding, rolls[i], resultPadding, stars);
        }
    }

    public static void main(String[] args) {
        int[] config = new int[0];

        while (config.length == 0) {
            try {
                config = getInput();
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        int numDice = config[0];
        int numSides = config[1];
        int numRolls = config[2];


        Die[] dice = createDice(numDice, numSides);
        int[] results = rollDice(dice, numSides, numRolls);

        report(numDice, results, findMax(results));
    }
}