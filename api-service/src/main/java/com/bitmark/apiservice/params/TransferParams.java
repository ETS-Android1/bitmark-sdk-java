/**
 * SPDX-License-Identifier: ISC
 * Copyright © 2014-2019 Bitmark. All rights reserved.
 * Use of this source code is governed by an ISC
 * license that can be found in the LICENSE file.
 */
package com.bitmark.apiservice.params;

import com.bitmark.apiservice.utils.Address;
import com.bitmark.apiservice.utils.ArrayUtil;
import com.bitmark.apiservice.utils.BinaryPacking;
import com.bitmark.cryptography.crypto.Sha3256;
import com.bitmark.cryptography.crypto.encoder.VarInt;
import com.bitmark.cryptography.crypto.key.KeyPair;

import static com.bitmark.cryptography.crypto.encoder.Hex.HEX;
import static com.bitmark.cryptography.utils.Validator.checkValid;

public class TransferParams extends AbsSingleParams {

    private Address receiver;

    private String link;

    public TransferParams(Address receiver) {
        checkValid(() -> receiver != null, "invalid receiver address");
        this.receiver = receiver;
    }

    public TransferParams(Address receiver, String link) {
        this(receiver);
        setLink(link);
    }

    public Address getReceiver() {
        return receiver;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        checkValidLink(link);
        this.link = link;
    }

    @Override
    public String toJson() {
        checkSigned();
        return "{\"transfer\":{\"link\":\"" + link + "\",\"owner\":\"" + receiver
                .getAddress() +
                "\",\"signature\":\"" + HEX.encode(signature) + "\"}}";
    }

    @Override
    public byte[] sign(KeyPair key) {
        checkValidLink(link);
        return super.sign(key);
    }

    @Override
    byte[] pack() {
        byte[] data = VarInt.writeUnsignedVarInt(0x04);
        data = BinaryPacking.concat(HEX.decode(link), data);
        data = ArrayUtil.concat(data, new byte[]{0x00});
        data = BinaryPacking.concat(receiver.pack(), data);
        return data;
    }

    private void checkValidLink(String link) {
        checkValid(
                () -> link != null && HEX.decode(link).length == Sha3256.HASH_BYTE_LENGTH,
                "invalid link"
        );
    }
}
