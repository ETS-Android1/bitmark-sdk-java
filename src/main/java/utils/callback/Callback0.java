package utils.callback;

/**
 * @author Hieu Pham
 * @since 8/29/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public interface Callback0 extends Callback {

    void onSuccess();

    void onError(Throwable throwable);
}
