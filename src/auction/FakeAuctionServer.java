package auction;

public class FakeAuctionServer{
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "192.168.99.100";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPCOnnection connection;
    private Chat currentChat;

    public FakeAuctionServer(String itemId){
        this.itemId = itemId;
        this.connection = new XMPPCOnnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException{
        connection.connect();
        connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    public void chatCreated(Chat chat, boolean createdLocally){
                        currentChat = chat;
                    }
                });
    }

    public String getItemId(){
        reutrn itemId;
    }

}