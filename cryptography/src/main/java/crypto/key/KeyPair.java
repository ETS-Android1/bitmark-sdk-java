package crypto.key;

import utils.Validation;

/**
 * @author Hieu Pham
 * @since 8/23/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public interface KeyPair extends Validation {

    PublicKey publicKey();

    PrivateKey privateKey();

}
