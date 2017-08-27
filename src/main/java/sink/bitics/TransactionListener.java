package sink.bitics;

import io.prometheus.client.Counter;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.listeners.OnTransactionBroadcastListener;

public class TransactionListener implements OnTransactionBroadcastListener {
    static final Counter transactions = Counter.build()
            .name("transactions_total").help("Total transactions.").register();
    static final Counter bitcoins = Counter.build()
            .name("bitcoins_total").help("Total bitcoins in transactions.").register();

    public void onTransaction(Peer peer, Transaction t) {
        // Increase the number of transactions by one
        transactions.inc();

        // Increase the number of bitcoins
        // The value comes in "satoshis", which are 0.00000001 BTCs
        bitcoins.inc(t.getOutputSum().getValue() * 0.00000001);
    }
}
