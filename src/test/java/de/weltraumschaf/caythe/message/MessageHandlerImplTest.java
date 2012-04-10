package de.weltraumschaf.caythe.message;

import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MessageHandlerImplTest {

    @Test public void addRemoveAndSendMessages() {
        Message msg = new Message(MessageType.CALL, "msg");
        MessageListener listener1 = mock(MessageListener.class);
        MessageListener listener2 = mock(MessageListener.class);
        MessageListener listener3 = mock(MessageListener.class);

        MessageHandlerImpl sut = new MessageHandlerImpl();
        sut.addListener(listener1);
        sut.sendMessage(msg);
        sut.addListener(listener2);
        sut.sendMessage(msg);
        sut.addListener(listener3);
        sut.sendMessage(msg);
        sut.removeListener(listener3);
        sut.sendMessage(msg);
        sut.removeListener(listener2);
        sut.sendMessage(msg);
        sut.removeListener(listener1);
        sut.sendMessage(msg);

        verify(listener1, times(5)).receiveMessage(msg);
        verify(listener2, times(3)).receiveMessage(msg);
        verify(listener3, times(1)).receiveMessage(msg);
    }
}
