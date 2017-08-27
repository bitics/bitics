package sink.bitics;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.utils.BriefLogFormatter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Sink {
    public static void main(String[] args) throws Exception {
        BriefLogFormatter.init();

        // Connect to the main bitcoin network
        final NetworkParameters params = MainNetParams.get();

        // This connects to the test bitcoin network instead
        // final NetworkParameters params = TestNet3Params.get();

        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, blockStore);

        // Connect to a group of peers
        PeerGroup peerGroup = new PeerGroup(params, chain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(params));
        peerGroup.start();
        peerGroup.waitForPeers(1).get();

        // Add a listener for every transaction
        TransactionListener listener = new TransactionListener();
        peerGroup.addOnTransactionBroadcastListener(listener);

        // Expose JVM metrics with prometheus
        DefaultExports.initialize();

        // Expose prometheus metrics over HTTP using Jetty and servlets
        Server server = new Server(1234);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");

        server.start();
        server.join();
    }
}
