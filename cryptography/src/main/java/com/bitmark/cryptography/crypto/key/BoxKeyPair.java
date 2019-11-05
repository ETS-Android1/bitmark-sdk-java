/**
 * SPDX-License-Identifier: ISC
 * Copyright © 2014-2019 Bitmark. All rights reserved.
 * Use of this source code is governed by an ISC
 * license that can be found in the LICENSE file.
 */
package com.bitmark.cryptography.crypto.key;

import com.bitmark.cryptography.crypto.Box;

import java.util.Objects;

import static com.bitmark.cryptography.crypto.Box.PRIVATE_KEY_BYTE_LENGTH;
import static com.bitmark.cryptography.crypto.Box.PUB_KEY_BYTE_LENGTH;

public class BoxKeyPair implements KeyPair {

    private final byte[] publicKey;

    private final byte[] privateKey;

    public static BoxKeyPair from(byte[] publicKey, byte[] privateKey) {
        return new BoxKeyPair(publicKey, privateKey);
    }

    protected BoxKeyPair(byte[] publicKey, byte[] privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public PublicKey publicKey() {
        return PublicKey.from(publicKey);
    }

    @Override
    public PrivateKey privateKey() {
        return PrivateKey.from(privateKey);
    }

    @Override
    public boolean isValid() {
        boolean isValidLength = publicKey.length == PUB_KEY_BYTE_LENGTH &&
                privateKey.length == PRIVATE_KEY_BYTE_LENGTH;
        if (!isValidLength) {
            return false;
        }
        final KeyPair receiver = Box.generateKeyPair();
        final byte[] messageSent = new byte[]{0x7F};
        final byte[] nonce = new byte[Box.NONCE_BYTE_LENGTH];
        final byte[] cipher =
                Box.box(
                        messageSent,
                        nonce,
                        receiver.publicKey().toBytes(),
                        privateKey
                );
        final byte[] messageReceived =
                Box.unbox(
                        cipher,
                        nonce,
                        publicKey,
                        receiver.privateKey().toBytes()
                );
        return Objects.deepEquals(messageSent, messageReceived);
    }
}
