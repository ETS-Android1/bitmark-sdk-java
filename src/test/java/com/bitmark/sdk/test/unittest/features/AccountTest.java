package com.bitmark.sdk.test.unittest.features;

import com.bitmark.sdk.service.configuration.Network;
import com.bitmark.sdk.crypto.Ed25519;
import com.bitmark.sdk.error.ValidateException;
import com.bitmark.sdk.features.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import com.bitmark.sdk.test.unittest.BaseTest;
import com.bitmark.sdk.utils.AccountNumberData;
import com.bitmark.sdk.utils.Seed;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.bitmark.sdk.crypto.encoder.Hex.HEX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Hieu Pham
 * @since 8/31/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public class AccountTest extends BaseTest {

    @DisplayName("Verify function new Account() works well")
    @Test
    public void testNewAccount_NoCondition_ValidAccountIsCreated() {
        final Account account = new Account();
        assertEquals(account.getKey().privateKey().size(), Ed25519.PRIVATE_KEY_LENGTH);
        assertEquals(account.getKey().publicKey().size(), Ed25519.PUBLIC_KEY_LENGTH);
        assertNotNull(account.getAccountNumber());
        assertTrue(Account.isValidAccountNumber(account.getAccountNumber()));
    }

    @DisplayName("Verify function Account.fromSeed(Seed) works well with valid Seed")
    @ParameterizedTest
    @CsvSource({"5XEECt18HGBGNET1PpxLhy5CsCLG9jnmM6Q8QGF4U2yGb1DABXZsVeD, " +
            "ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54NXva, " +
            "58760a01edf5ed4f95bfe977d77a27627cd57a25df5dea885972212c2b1c0e2f",
                       "5XEECsXPYA9wDVXMtRMAVrtaWx7WSc5tG2hqj6b8iiz9rARjg2BgA9w, " +
                               "eujeF5ZBDV3qJyKeHxNqnmJsrc9iN7eHJGECsRuSXvLmnNjsWX, " +
                               "807f4d123c944e0c3ecc95d9bde89916ced6341a8c8cedeb8caafef8f35654e7"})
    public void testNewAccountFromSeed_ValidSeed_ValidAccountIsCreated(String encodedSeed,
                                                                       String accountNumber,
                                                                       String publicKey) {
        final Seed seed = Seed.fromEncodedSeed(encodedSeed);
        final Account account = Account.fromSeed(seed);
        assertEquals(account.getAccountNumber(), accountNumber);
        assertEquals(HEX.encode(account.getKey().publicKey().toBytes()), publicKey);
    }

    @DisplayName("Verify function Account.fromSeed(Seed) throws exception with invalid Seed")
    @ParameterizedTest
    @MethodSource("createInvalidSeed")
    public void testNewAccountFromSeed_InvalidSeed_ErrorIsThrow(Seed seed) {
        assertThrows(ValidateException.class, () -> Account.fromSeed(seed));
    }

    @DisplayName("Verify function Account.fromRecoveryPhrase(String...) works well with valid " +
                         "recovery phrase")
    @ParameterizedTest
    @MethodSource("createRecoveryPhraseAccountNumberPublicKey")
    public void testNewAccountFromRecoveryPhrase_ValidRecoveryPhrase_ValidAccountIsCreated(String recoveryPhrase,
                                                                                           String accountNumber,
                                                                                           String publicKey) {
        final Account account = Account.fromRecoveryPhrase(recoveryPhrase.split(" "));
        assertEquals(accountNumber, account.getAccountNumber());
        assertEquals(publicKey, HEX.encode(account.getKey().publicKey().toBytes()));
    }

    @DisplayName("Verify function Account.fromRecoveryPhrase(String...) throws error with " +
                         "invalid recovery phrase")
    @ParameterizedTest
    @ValueSource(strings = {"this is the recovery phrase that has 24 words but not contains the " +
            "words from bip39 list so it will throw exception $ ^", "ability panel leave spike " +
            "mixture token voice certain today market", ""})
    public void testNewAccountFromRecoveryPhrase_InvalidRecoveryPhrase_ErrorIsThrow(String recoveryPhrase) {
        assertThrows(ValidateException.class,
                () -> Account.fromRecoveryPhrase(recoveryPhrase.split(" ")));
    }

    @DisplayName("Verify function Account.getSeed() works well")
    @ParameterizedTest
    @CsvSource({"accident syrup inquiry you clutch liquid fame upset joke glow best " +
            "school repeat birth library combine access camera organ trial crazy jeans lizard " +
            "science, 5XEECt18HGBGNET1PpxLhy5CsCLG9jnmM6Q8QGF4U2yGb1DABXZsVeD", "abuse tooth riot" +
            " whale dance dawn armor patch tube sugar edit clean guilt person lake height tilt " +
            "wall prosper episode produce spy artist account, " +
            "5XEECsXPYA9wDVXMtRMAVrtaWx7WSc5tG2hqj6b8iiz9rARjg2BgA9w"})
    public void testGetSeedFromExistedAccount_ValidAccount_CorrectSeedIsReturn(String recoveryPhrase, String expectedSeed) {
        final Account account = Account.fromRecoveryPhrase(recoveryPhrase.split(" "));
        final String encodedSeed = account.getSeed().getEncodedSeed();
        assertEquals(expectedSeed, encodedSeed);
    }

    @DisplayName("Verify function Account.getRecoveryPhrase works well")
    @ParameterizedTest
    @CsvSource({"5XEECt18HGBGNET1PpxLhy5CsCLG9jnmM6Q8QGF4U2yGb1DABXZsVeD, accident syrup inquiry " +
            "you clutch liquid fame upset joke glow best school repeat birth library " +
            "combine access camera organ trial crazy jeans lizard science",
                       "5XEECsXPYA9wDVXMtRMAVrtaWx7WSc5tG2hqj6b8iiz9rARjg2BgA9w, abuse tooth riot" +
                               " whale dance dawn armor patch tube sugar edit clean guilt person " +
                               "lake height tilt wall prosper episode produce spy artist account"})
    public void testGetRecoveryPhrase_NoCondition_CorrectRecoveryPhraseIsReturn(String encodedSeed, String expectedRecoveryPhrase) {
        final Seed seed = Seed.fromEncodedSeed(encodedSeed);
        final Account account = Account.fromSeed(seed);
        assertTrue(Arrays.equals(expectedRecoveryPhrase.split(" "),
                account.getRecoveryPhrase().getMnemonicWords()));
    }

    @DisplayName("Verify function Account.isValidAccountNumber(String)")
    @ParameterizedTest
    @CsvSource({"ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54NXva, true",
                       "ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54NXva, true",
                       "ec6yMcJATX6gjNwvqpBNbc4jNEasoUgbfBBGGMM5NvoJ54NXva, false"})
    public void testCheckValidAccountNumber_NoCondition_CorrectResultIsReturn(String accountNumber, boolean expectedResult) {
        assertEquals(Account.isValidAccountNumber(accountNumber), expectedResult);
    }

    @DisplayName("Verify function Account.parseAccountNumber(String) works well with valid " +
                         "account number")
    @ParameterizedTest
    @MethodSource("createAccountNumberPublicKeyNetwork")
    public void testParseAccountNumber_ValidAccountNumber_CorrectResultIsReturn(String accountNumber, String publicKey, Network network) {
        final AccountNumberData data = Account.parseAccountNumber(accountNumber);
        assertEquals(data.getNetwork(), network);
        assertEquals(HEX.encode(data.getPublicKey().toBytes()), publicKey);
    }

    @DisplayName("Verify function Account.parseAccountNumber(String) throws an exception when " +
                         "account number is invalid")
    @ParameterizedTest
    @ValueSource(strings = {"ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54N",
            "58760a01edf5ed4f95bfe977d77a27627cd57a25df5dea885972212c2b1c0e2f", ""})
    public void testParseAccountNumber_InvalidAccountNumber_ErrorIsThrow(String accountNumber) {
        assertThrows(ValidateException.class, () -> Account.parseAccountNumber(accountNumber));
    }

    private static Stream<Arguments> createRecoveryPhraseAccountNumberPublicKey() {
        return Stream.of(Arguments.of("accident syrup inquiry you clutch liquid fame upset joke " +
                        "glow best school repeat birth library combine access camera organ trial " +
                        "crazy jeans lizard science",
                "ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54NXva",
                "58760a01edf5ed4f95bfe977d77a27627cd57a25df5dea885972212c2b1c0e2f"),
                Arguments.of("abuse tooth riot whale dance dawn armor patch tube sugar edit clean" +
                                " guilt person lake height tilt wall prosper episode produce spy " +
                                "artist account",
                        "eujeF5ZBDV3qJyKeHxNqnmJsrc9iN7eHJGECsRuSXvLmnNjsWX",
                        "807f4d123c944e0c3ecc95d9bde89916ced6341a8c8cedeb8caafef8f35654e7"));
    }

    private static Stream<Arguments> createAccountNumberPublicKeyNetwork() {
        return Stream.of(Arguments.of("ec6yMcJATX6gjNwvqp8rbc4jNEasoUgbfBBGGyV5NvoJ54NXva",
                "58760a01edf5ed4f95bfe977d77a27627cd57a25df5dea885972212c2b1c0e2f",
                Network.TEST_NET));
    }

    private static Stream<Seed> createInvalidSeed() {
        return Stream.of(new Seed(HEX.decode(
                "7b95d37f92c904949f79784c7855606b6a2d60416f01441671f4132cef60b607"),
                Network.LIVE_NET), new Seed(HEX.decode(
                "33f1fbe8e8e5c7fd592de351059a19434b99082cfaf9f71f6cbe216173690317"),
                Network.LIVE_NET));
    }

}