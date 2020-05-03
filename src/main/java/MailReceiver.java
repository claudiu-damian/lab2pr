import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.*;
import java.util.Properties;


public class MailReceiver {
    private static Logger LOG = LogManager.getLogger(MailReceiver.class);

    public static void main(String[] args) {
        MailReceiver.collectMessages();
    }

    private static void collectMessages() {
        Message[] messages = getMessageFolder();
        try {
            LOG.info("Getting messages from the mailbox\n");
            for (int i = 0; i < messages.length; ++i) {
                Message message = messages[i];
                String from = "unknown";
                if (message.getReplyTo().length >= 1) {
                    from = message.getFrom()[0].toString();
                } else if (message.getFrom().length >= 1) {
                    from = message.getFrom()[0].toString();
                }
                String subject = message.getSubject();
                LOG.info("Saving message [" + i + "] with subject: [" + subject + "] from: [" + from + "]\n");
            }
        } catch (MessagingException e) {
            LOG.error("Failed to read message: " + e + "\n");
        }
    }

    private static Message[] getMessageFolder() {
        Folder folder;
        Store store;
        Message [] messages = new Message[0];
        try {
            Session session = Session.getDefaultInstance(getProperties());
            store = session.getStore();
            LOG.info("Getting the number of the messages from the mailbox: [" + MailConstants.FIRST_MAIL + "]\n");
            store.connect(MailConstants.MAIL_HOST, MailConstants.FIRST_MAIL, MailConstants.PASSWORD);
            folder = store.getDefaultFolder().getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            messages = folder.getMessages();
            LOG.info("Number of Message: " + folder.getMessageCount() + "\n");
            LOG.info("Number of Unread Messages: " + folder.getUnreadMessageCount() + "\n");
        } catch (MessagingException e) {
            LOG.error("Failed to open the mail folder: " + e);
        }
        return messages;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "pop3s");
        return props;
    }
}

