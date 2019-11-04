/**
 * SPDX-License-Identifier: ISC
 * Copyright © 2014-2019 Bitmark. All rights reserved.
 * Use of this source code is governed by an ISC
 * license that can be found in the LICENSE file.
 */
package com.bitmark.cryptography.error;

public class UnexpectedException extends RuntimeException {

    public UnexpectedException(String message){
        super(message);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
