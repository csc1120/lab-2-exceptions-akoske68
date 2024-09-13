/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Austin Koske
 * Last Updated: 9-12-2024
 */
package koskea;

import java.util.Random;

/**
 * Die --- represents a die with a predefined number of sides
 * @author Austin Koske
 */
public class Die {
    /**
     * The minimum possible number of sides per dice
     */
    public static final int MIN_SIDES = 2;

    /**
     * The maximum possible number of sides per dice
     */
    public static final int MAX_SIDES = 100;

    private int currentValue = 0;
    private final int numSides;
    private final Random random = new Random();

    /**
     * Die class constructor
     * @param nS -- Number of sides for dice
     * @throws IllegalArgumentException -- nS < 2 || nS > 100
     */

    public Die(int nS) throws IllegalArgumentException {
        if (nS < MIN_SIDES || nS > MAX_SIDES) {
            throw new IllegalArgumentException("Illegal number of sides");
        } else {
            this.numSides = nS;
        }
    }

    /**
     * Getter method to return the current value of the die
     * @return Current value of die
     * @throws DieNotRolledException -- roll method has not been called
     */

    public int getCurrentValue() {
        if (this.currentValue == 0) {
            throw new DieNotRolledException("Dice has not been rolled");
        } else {
            int cur = this.currentValue;
            this.currentValue = 0;
            return cur;
        }
    }

    /**
     * Rolls the current dice and generates a random number 1-numSides (inclusive)
     */

    public void roll() {
        this.currentValue = random.nextInt(this.numSides) + 1;
    }
}