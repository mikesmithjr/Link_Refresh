package org.texashealth.linkrefresh;

/**
 * Created by SmithM04 on 5/19/2015.
 */

import android.util.Log;

import java.util.Date;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.util.Properties;

import java.security.Security;
public class GMailSender extends javax.mail.Authenticator {

    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private Multipart _multipart;


    static {
        Security.addProvider(new org.texashealth.linkrefresh.JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Log.i("GMailSender", "Building Gmail");

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mailhost);
        props.put("mail.smtp.authrue", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        props.setProperty("mail.smtps.quitwait", "false");

       session = Session.getDefaultInstance(props, this);


        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        Log.i("GMailSender", "Starting Send");
        try{
          /*  MimeMessage message = new MimeMessage(session);
            Log.i("GMailSender", "Creating MIME Message");
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            Log.i("GMailSender", "DataHandler for Body text: " + body);
            message.setSender(new InternetAddress(sender));
            Log.i("GMailSender", "Setting the sender address on message: " + sender);
            message.setSubject(subject);
            Log.i("GMailSender", "Setting Subject of message: " + subject);
            message.setDataHandler(handler);
            Log.i("GMailSender", "Setting Recipients of message: " + recipients);
            if (recipients.indexOf(',') > 0) {
                Log.i("GMailSender", "Checking for multiple recipients.");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            }else if (recipients.indexOf(',') <= 0){
            Log.i("GMailSender", "Single Recipient");
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
                Log.i("GMailSender", "message: " + message);
                Log.i("GMailSender", "Message content: " + message.toString());

                Transport.send(message);

                Log.i("GMailSender", "Message Sent");*/
                _multipart = new MimeMultipart();
            if(!user.equals("") && !password.equals("") && recipients.isEmpty() && !sender.equals("") && !subject.equals("") && !body.equals("")) {

                MimeMessage msg = new MimeMessage(session);

                msg.setFrom(new InternetAddress(sender));

                InternetAddress addressTo = new InternetAddress();

                    addressTo = new InternetAddress(recipients);


                msg.setRecipient(MimeMessage.RecipientType.TO, addressTo);

                msg.setSubject(subject);
                msg.setSentDate(new Date());

                // setup message body
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);
                _multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                msg.setContent(_multipart);

                // send email
                Transport.send(msg);

               // return true;
            } else {
                //return false;
            }



        }catch(Exception e){

        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }


}
