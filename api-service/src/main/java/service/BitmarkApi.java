package service;

import service.params.*;
import service.params.query.QueryParams;
import service.response.*;
import utils.callback.Callback1;
import utils.record.AssetRecord;

import java.util.List;

/**
 * @author Hieu Pham
 * @since 8/29/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public interface BitmarkApi {

    void issueBitmark(IssuanceParams params, Callback1<IssueResponse> callback);

    void registerAsset(RegistrationParams params, Callback1<RegistrationResponse> callback);

    void transferBitmark(TransferParams params, Callback1<String> callback);

    void offerBitmark(TransferOfferParams params, Callback1<String> callback);

    void respondBitmarkOffer(TransferResponseParams params, Callback1<String> callback);

    void getBitmark(String bitmarkId, boolean includeAsset, Callback1<GetBitmarkResponse> callback);

    void listBitmarks(QueryParams params, Callback1<GetBitmarksResponse> callback);

    void getAsset(String assetId, Callback1<AssetRecord> callback);

    void listAssets(QueryParams params, Callback1<List<AssetRecord>> callback);

    void getTransaction(String txId, Callback1<GetTransactionResponse> callback);

    void listTransactions(QueryParams params, Callback1<GetTransactionsResponse> callback);

}
