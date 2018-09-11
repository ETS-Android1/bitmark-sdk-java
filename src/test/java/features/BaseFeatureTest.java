package features;

import config.GlobalConfiguration;
import config.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Hieu Pham
 * @since 8/31/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public abstract class BaseFeatureTest {

    @BeforeEach
    public void beforeEach() {
        BitmarkSDK.init(GlobalConfiguration.builder().withApiToken("DummyApiToken").withNetwork(Network.TEST_NET));
    }

    @AfterEach
    public void afterEach() {
        BitmarkSDK.destroy();
    }
}