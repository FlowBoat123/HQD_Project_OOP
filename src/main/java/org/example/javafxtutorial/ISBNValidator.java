package org.example.javafxtutorial;

import java.util.regex.Pattern;

public class ISBNValidator {
    private static final String ISBN_10_REGEX = "^(\\d{9}[\\dX])$";
    private static final String ISBN_13_REGEX = "^(\\d{13})$";
    public static boolean isValidISBN(String isbn) {
        if (Pattern.matches(ISBN_10_REGEX, isbn)) {
            return isValidISBN10(isbn);
        } else if (Pattern.matches(ISBN_13_REGEX, isbn)) {
            return isValidISBN13(isbn);
        }

        return false;
    }

    // Validate ISBN-10 checksum
    public static boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
            sum += (i + 1) * Character.getNumericValue(isbn.charAt(i));
        }
        char lastChar = isbn.charAt(9);
        if (lastChar == 'X') {
            sum += 10 * 10;
        } else if (Character.isDigit(lastChar)) {
            sum += 10 * Character.getNumericValue(lastChar);
        } else {
            return false;
        }

        return sum % 11 == 0;
    }
    public static boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += 3 * digit;
            }
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }
}
