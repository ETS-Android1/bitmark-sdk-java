package features;

import service.ApiService;
import service.params.query.TransactionQueryBuilder;
import service.response.GetTransactionResponse;
import service.response.GetTransactionsResponse;
import utils.callback.Callback1;

/**
 * @author Hieu Pham
 * @since 8/31/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */
public class Transaction {

    public static void get(String txId, Callback1<GetTransactionResponse> callback) {
        ApiService.getInstance().getTransaction(txId, callback);
    }

    public static void list(TransactionQueryBuilder builder,
                            Callback1<GetTransactionsResponse> callback) {
        ApiService.getInstance().listTransactions(builder.build(), callback);
    }
}
