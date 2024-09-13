/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * DieNotRolledException class
 * Name: Austin Koske
 * Last Updated: 9-12-2024
 */
package koskea;

/**
 * DieNotRolledException -- Represents an exception thrown when a die's getCurrentValue
 * method is called without previously calling the roll method
 * @author Austin Koske
 */
public class DieNotRolledException extends RuntimeException {
    /**
     * DieNotRolledException class constructor
     * @param message -- Error message
     */
    public DieNotRolledException(String message) {
        super(message);
    }

    /**
     * Getter method to retrieve the error message
     * @return Error message
     */

    public String getMessage() {
        return super.getMessage();
    }
}
