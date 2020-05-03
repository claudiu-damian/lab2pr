import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private static Logger LOG = LogManager.getLogger(MailSender.class);

    public static void main(String[] args) {
        sendEmail();
    }

    private static void sendEmail() {
        Session session = Session.getInstance(getProperties(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MailConstants.FIRST_MAIL, MailConstants.PASSWORD);
                    }
                });

        try {
            LOG.info("Sending email from: [" + MailConstants.FIRST_MAIL + "] to [" + MailConstants.SECOND_MAIL + "]\n");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailConstants.SECOND_MAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(MailConstants.SECOND_MAIL)
            );
            message.setSubject("CEL MAI BUN LABORATOR");
            message.setText("SALUT KE FAKI? UNDE ESTI");
            Transport.send(message);
            LOG.info("Email with subject: [" + message.getSubject() + "] sent to [" + MailConstants.SECOND_MAIL + "]\n");
        } catch (MessagingException e) {
            LOG.error("Failed to connect: " + e + "\n");
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }
}
