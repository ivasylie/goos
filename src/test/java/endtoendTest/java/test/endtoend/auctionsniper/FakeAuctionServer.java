package endtoendTest.java.test.endtoend.auctionsniper;

import auctionsniper.Main;
import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FakeAuctionServer{
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "127.0.0.1";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;

    public FakeAuctionServer(String itemId){
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    private final SingleMessageListener messageListener = new SingleMessageListener();

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    public void chatCreated(Chat chat, boolean createdLocally){
                        currentChat = chat;
                        chat.addMessageListener(messageListener);
                    }
                });
    }

    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(format("SOLVersion: 1.1; Event: PRICE; " +
                "CurrentPrice: %d; Increment: %d; Bidder: %s;",
                price, increment, bidder));
    }

    public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        messageListener.receivesMessage();
    }

    public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {

        receivesAMessageMatching(sniperId, equalTo(format(Main.BID_COMMAND_FORMAT, bid)));
        assertThat(currentChat.getParticipant(), equalTo(sniperId));
        messageListener.receivesAMessage(equalTo(
                String.format("SOLVersion: 1.1; Command: BID; Price: %d", bid)));
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage("SOLVersiom: 1.1; Event: CLOSE;");
    }

    public void stop() {
        connection.disconnect();
    }

    public String getItemId(){
        return itemId;
    }

    private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException {
        messageListener.receivesAMessage(messageMatcher);
        assertThat(currentChat.getParticipant(), equalTo(sniperId));
    }

    public void hasRceivedBid(int i, String sniperXmppId) {
    }
}