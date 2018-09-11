package service;

import service.params.IssuanceParams;
import service.params.RegistrationParams;
import service.response.RegistrationResponse;
import utils.callback.Callback1;

import java.util.List;

/**
 * @author Hieu Pham
 * @since 8/29/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public interface BitmarkApi {

    void issueBitmark(IssuanceParams params, Callback1<List<String>> callback);

    void registerAsset(RegistrationParams params, Callback1<RegistrationResponse> callback);

}