package sdk.test.unittest.utils;

import apiservice.configuration.Network;
import apiservice.utils.ArrayUtil;
import sdk.test.unittest.BaseTest;
import sdk.utils.Seed;
import cryptography.error.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static apiservice.configuration.Network.LIVE_NET;
import static apiservice.configuration.Network.TEST_NET;
import static cryptography.crypto.encoder.Hex.HEX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hieu Pham
 * @since 9/2/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public class SeedTest extends BaseTest {

    @DisplayName("Verify function new Seed(byte[]) works well with happy condition")
    @ParameterizedTest
    @MethodSource("createValidByteArray")
    public void testConstructSeedFromByteArray_ValidSeedBytes_NewSeedInstanceIsReturn(byte[] seedBytes) {
        final Seed seed = new Seed(seedBytes);
        Assertions.assertTrue(ArrayUtil.equals(seedBytes, seed.getSeed()));
        assertEquals(seed.getVersion(), Seed.VERSION);
        assertEquals(seed.getNetwork(), TEST_NET);
    }

    @DisplayName("Verify function new Seed(byte[]) throws exception with invalid byte array")
    @ParameterizedTest
    @MethodSource("createInvalidByteArray")
    public void testConstructSeedFromByteArray_NullSeedBytes_ErrorIsThrow(byte[] seedBytes) {
        assertThrows(ValidateException.class, () -> new Seed(seedBytes));
    }

    @DisplayName("Verify function new Seed(byte[], Network) works well with happy condition")
    @ParameterizedTest
    @MethodSource("createValidByteArrayNetwork")
    public void testConstructSeedFromByteArrayNetwork_ValidSeedBytesAndNetwork_NewSeedInstanceIsReturn(byte[] seedBytes, Network network) {
        final Seed seed = new Seed(seedBytes, network);
        assertEquals(seedBytes, seed.getSeed());
        assertEquals(network, seed.getNetwork());
        assertEquals(seed.getVersion(), Seed.VERSION);
    }

    @DisplayName("Verify function new Seed(byte[], Network) throws exception with invalid network")
    @ParameterizedTest
    @MethodSource("createByteArrayInvalidNetwork")
    public void testConstructSeedFromByteArrayNetwork_InvalidNetwork_ErrorIsThrow(byte[] seedBytes, Network network) {
        assertThrows(Exception.class, () -> new Seed(seedBytes, network));
    }

    @DisplayName("Verify function new Seed(byte[], Network, int) works well with happy condition")
    @ParameterizedTest
    @MethodSource("createValidByteArrayNetworkVersion")
    public void testConstructSeedFromByteArrayNetworkVersion_ValidByteArrayNetworkAndVersion_NewSeedInstanceIsReturn(byte[] seedBytes, Network network, int version) {
        final Seed seed = new Seed(seedBytes, network, version);
        assertEquals(seedBytes, seed.getSeed());
        assertEquals(network, seed.getNetwork());
        assertEquals(seed.getVersion(), version);
    }

    @DisplayName("Verify function new Seed(byte[], Network, int) throws exception with invalid " +
                         "version")
    @ParameterizedTest
    @MethodSource("createValidByteArrayNetworkInvalidVersion")
    public void testConstructSeedFromByteArrayNetworkVersion_InvalidVersion_ErrorIsThrow(byte[] seedBytes, Network network, int version) {
        assertThrows(ValidateException.class, () -> new Seed(seedBytes, network, version));
    }

    @DisplayName("Verify function Seed.fromEncodedSeed(String) works well with happy condition")
    @ParameterizedTest
    @MethodSource("createValidEncodedSeedByteArrayNetworkVersion")
    public void testConstructSeedFromEncodedSeed_ValidEncodedSeed_NewSeedInstanceIsReturn(String encodedSeed, byte[] seedBytes, Network network, int version) {
        final Seed seed = Seed.fromEncodedSeed(encodedSeed);
        assertTrue(ArrayUtil.equals(seedBytes, seed.getSeed()));
        assertEquals(network, seed.getNetwork());
        assertEquals(seed.getVersion(), version);
    }

    @DisplayName("Verify function Seed.fromEncodedSeed(String) throws exception with invalid " +
                         "network")
    @ParameterizedTest
    @ValueSource(strings = {"5XEECt18HGBGNET1PpxLhy5CsCLG9jnmM6Q8QU2yGb1DABXZsVeD",
            "5XEECt18HGBGNET1PpxLhy5CsCLG2yGb1DABXZsVeD"})
    public void testConstructSeedFromEncodedSeed_InvalidEncodedSeed_ErrorIsThrow(String encodedSeed) {
        assertThrows(ValidateException.class, () -> Seed.fromEncodedSeed(encodedSeed));
    }

    private static Stream<byte[]> createValidByteArray() {
        return Stream.of(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"), HEX.decode(
                "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"));
    }

    private static Stream<byte[]> createInvalidByteArray() {
        return Stream.of(null, new byte[]{}, new byte[]{13, 45, 123, -23, 29, 98, -23, -34});
    }

    private static Stream<Arguments> createValidByteArrayNetwork() {
        return Stream.of(Arguments.of(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"), TEST_NET),
                Arguments.of(HEX.decode(
                        "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                        LIVE_NET));
    }

    private static Stream<Arguments> createByteArrayInvalidNetwork() {
        return Stream.of(Arguments.of(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"), null),
                Arguments.of(HEX.decode(
                        "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                        null));
    }

    private static Stream<Arguments> createValidByteArrayNetworkVersion() {
        return Stream.of(Arguments.of(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"), TEST_NET, 1),
                Arguments.of(HEX.decode(
                        "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                        LIVE_NET, 3));
    }

    private static Stream<Arguments> createValidByteArrayNetworkInvalidVersion() {
        return Stream.of(Arguments.of(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"), TEST_NET, -1),
                Arguments.of(HEX.decode(
                        "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                        LIVE_NET, 0));
    }

    private static Stream<Arguments> createValidEncodedSeedByteArrayNetworkVersion() {
        return Stream.of(Arguments.of("5XEECt18HGBGNET1PpxLhy5CsCLG9jnmM6Q8QGF4U2yGb1DABXZsVeD",
                HEX.decode(
                        "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"),
                TEST_NET, 1),
                Arguments.of("5XEECqWqA47qWg86DR5HJ29HhbVqwigHUAhgiBMqFSBycbiwnbY639s", HEX.decode(
                        "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                        LIVE_NET, 1));
    }


}
