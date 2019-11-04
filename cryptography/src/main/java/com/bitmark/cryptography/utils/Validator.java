/**
 * SPDX-License-Identifier: ISC
 * Copyright © 2014-2019 Bitmark. All rights reserved.
 * Use of this source code is governed by an ISC
 * license that can be found in the LICENSE file.
 */
package com.bitmark.cryptography.utils;

import com.bitmark.cryptography.error.ValidateException;

public class Validator {

    public static <T> void checkNonNull(T input) {
        if (input == null) {
            throw new ValidateException.NullValueError();
        }
    }

    public static void checkValidString(String input) {
        if (input == null || input.isEmpty()) {
            throw new ValidateException.InvalidString();
        }
    }

    public static void checkValidHex(String input) {
        checkNonNull(input);
        if (input.length() % 2 == 1) {
            input = "0" + input;
        }
        if (!Matcher.isHex(input)) {
            throw new ValidateException.InvalidHex();
        }
    }

    public static void checkValidLength(byte[] data, int length) {
        if (data == null || data.length != length) {
            throw new ValidateException.InvalidLength(
                    length,
                    data == null ? 0 : data.length
            );
        }
    }

    public static void checkValidLength(String[] data, int length) {
        if (data == null || data.length != length) {
            throw new ValidateException.InvalidLength(
                    length,
                    data == null ? 0 : data.length
            );
        }
    }

    public static void checkValid(Specification specification) {
        checkValid(specification, "Specification is not match");
    }

    public static void checkValid(Specification specification, String message) {
        if (!specification.isSatisfied()) {
            throw new ValidateException(message);
        }
    }

    public static void checkValid(
            Specification specification,
            ValidateException exception
    ) {
        if (!specification.isSatisfied()) {
            throw exception;
        }
    }

    public static <T> void checkValid(T data, Specification1<T> specification) {
        checkValid(data, specification, "Specification is not match");
    }

    public static <T> void checkValid(
            T data,
            Specification1<T> specification,
            String message
    ) {
        if (!specification.isSatisfied(data)) {
            throw new ValidateException(message);
        }
    }

    public static <T> void checkValid(
            T data, Specification1<T> specification,
            ValidateException exception
    ) {
        if (!specification.isSatisfied(data)) {
            throw exception;
        }
    }

    public interface Specification1<T> {
        boolean isSatisfied(T t);
    }

    public interface Specification {
        boolean isSatisfied();
    }
}
