import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ATMTransactionInputTest {
    @Test
    @DisplayName("Only input between 1-1000 or 1.00-1000.00 should work.")
    public void testTransactionInput() {
        // Less than 1
        assertFalse(ATM.isValidTransactionAmount("-10"));
        assertFalse(ATM.isValidTransactionAmount("-1"));
        assertFalse(ATM.isValidTransactionAmount("0"));
        assertFalse(ATM.isValidTransactionAmount("0.00"));
        assertFalse(ATM.isValidTransactionAmount("0.50"));
        assertFalse(ATM.isValidTransactionAmount("0.99"));
        assertFalse(ATM.isValidTransactionAmount(".99"));

        // Random (invalid) values from 1-1000, including numbers with more than 3 digits after the decimal
        assertFalse(ATM.isValidTransactionAmount("1.000"));
        assertFalse(ATM.isValidTransactionAmount("1.001"));
        assertFalse(ATM.isValidTransactionAmount("1.999"));
        assertFalse(ATM.isValidTransactionAmount("99.999"));
        assertFalse(ATM.isValidTransactionAmount("1000.1000"));

        // Non-digit input
        assertFalse(ATM.isValidTransactionAmount("testing non-digit input"));
        assertFalse(ATM.isValidTransactionAmount("TESTING NON-DIGIT INPUT"));
        assertFalse(ATM.isValidTransactionAmount("!"));
        assertFalse(ATM.isValidTransactionAmount("     "));
        assertFalse(ATM.isValidTransactionAmount(""));

        // Valid input
        assertTrue(ATM.isValidTransactionAmount("1"));
        assertTrue(ATM.isValidTransactionAmount("1.00"));
        assertTrue(ATM.isValidTransactionAmount("1.01"));
        assertTrue(ATM.isValidTransactionAmount("1000"));
        assertTrue(ATM.isValidTransactionAmount("999"));
        assertTrue(ATM.isValidTransactionAmount("999.99"));
        assertTrue(ATM.isValidTransactionAmount("999.25"));
        assertTrue(ATM.isValidTransactionAmount("500"));
        assertTrue(ATM.isValidTransactionAmount("1000.00"));
    }
}