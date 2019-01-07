import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        Thread.sleep(2000);
        application.startBiddingIn(auction);
        Thread.sleep(2000);
        auction.hasReceivedJoinRequestFromSniper();
        Thread.sleep(2000);
        auction.announceClosed();
        Thread.sleep(2000);
        application.showSniperHasLostAuction();
        Thread.sleep(2000);
    }

    @After
    public void stopAuction(){
        auction.stop();
    }

    @After
    public void stopApplication(){
        application.stop();
    }
}
