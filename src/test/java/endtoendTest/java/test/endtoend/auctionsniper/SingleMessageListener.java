package endtoendTest.java.test.endtoend.auctionsniper;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SingleMessageListener implements MessageListener {

    private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

    public void processMessage(Chat chat, Message message){
        messages.add(message);
    }

    public void receivesMessage() throws InterruptedException {
        assertThat("Message", messages.poll(5, TimeUnit.SECONDS), is(notNullValue()));
    }

    public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
        final Message message = messages.poll(5, TimeUnit.MILLISECONDS);
        assertThat("Message", message, is(notNullValue()));
        assertThat(message.getBody(), messageMatcher);
    }

}
