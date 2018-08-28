package config;

import static crypto.encoder.Hex.HEX;

/**
 * @author Hieu Pham
 * @since 8/27/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public class SdkConfig {

    public static final int KEY_TYPE = 0x01; // Ed25519

    public static final int CHECKSUM_LENGTH = 4;

    public static final class Seed {

        public static final int ENCODED_LENGTH = 48;

        public static final int LENGTH = 32;

        public static final int VERSION = 0x01;

        public static final byte[] MAGIC_NUMBER = HEX.decode("5AFE");

        public static final int NETWORK_LENGTH = 1;
    }

    public enum KeyPart {

        PRIVATE_KEY(0x00), PUBLIC_KEY(0x01);

        private int value;

        KeyPart(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
}
