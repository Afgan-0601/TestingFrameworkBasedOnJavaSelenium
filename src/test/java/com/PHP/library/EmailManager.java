package com.PHP.library;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailManager {
	final static Logger logger = Logger.getLogger(EmailManager.class);

	public static void main(String[] args) {
		// EmailManager sender = new EmailManager();
		// sender.toAddress = "shahguliyev@gmail.com";
		// List<String> screenshots = new ArrayList<>();
		// sender.sendEmail(screenshots);

		EmailManager sender = new EmailManager();
		sender.toAddress = "shahguliyev@gmail.com";
		sender.ccAddress = "tmq-0601@mail.ru";

		List<String> screenshots = new ArrayList<>();
		screenshots.add("target/logs/log4j-selenium.log");
		screenshots.add("target/logs/Selenium-Report.html");
		screenshots.add("target/screenshots/go_to_companyLogo20191008003543921.png");
		screenshots.add("target/screenshots/PHPTravelHomePage20191005161627558.png");

		sender.sendEmail(screenshots);
	}

	private String toAddress = "";
	private String ccAddress = "";
	private String bccAddress = "";

	public void sendEmail(List<String> attachments) {
		String emailBody = "Test email by JavaMail API example." + "<br><br> Regards, <br>Test Automation Team<br>";

		sendEmail("imap.mail.ru", "993", "tmq-0601@mail.ru", "Shahguliyev-0601",
				"Congratulation you get new Offer!", emailBody, attachments);
	}

	public void sendEmail(String host, String port, final String emailUserID, final String emailUserPassword,
			String subject, String emailBody, List<String> attachments) {
		try {
			// sets SMTP server properties
			Properties prop = new Properties();
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.user", emailUserID);
			prop.put("mail.password", emailUserPassword);
			logger.info("Step1> preparing email configuration...");

			// creates a new session with an authenticator
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailUserID, emailUserPassword);
				}
			};

			Session session = Session.getInstance(prop, auth);
			// Creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(emailUserID));

			msg.addRecipients(Message.RecipientType.TO, setMultipleEmails(toAddress));
			if (!ccAddress.isEmpty() && !ccAddress.equals(null)) {
				msg.addRecipients(Message.RecipientType.CC, setMultipleEmails(ccAddress));
			}
			if (!bccAddress.isEmpty() && !bccAddress.equals(null)) {
				msg.addRecipients(Message.RecipientType.BCC, setMultipleEmails(bccAddress));
			}

			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// Creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emailBody, "text/html");
			// Creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			// Adds Attachments
			if (attachments.size() > 0) {
				for (String singleAttachment : attachments) {
					MimeBodyPart attachPart = new MimeBodyPart();
					try {
						attachPart.attachFile(singleAttachment);
					} catch (Exception e) {
						logger.error("Attaching files to email failed ...");
						logger.error(e.getMessage());
					}
					multipart.addBodyPart(attachPart);
				}
			}
			logger.info("Step2> Attaching report files & error screenshots ...");

			msg.setContent(multipart);
			// sends email
			logger.info("Step3> Sending email in progress...");
			Transport.send(msg);
			logger.info("Step4> Sending email complete...");
		} catch (Exception e) {
			logger.error("Sending email failed...");
			logger.error("Error: ", e);
		}

	}

	private Address[] setMultipleEmails(String emailAddress) {
		String multipleEmails[] = emailAddress.split(";");
		InternetAddress[] addresses = new InternetAddress[multipleEmails.length];
		try {
			for (int i = 0; i < multipleEmails.length; i++) {
				addresses[i] = new InternetAddress(multipleEmails[i]);
			}
		} catch (Exception e) {
			logger.error("Adding multiple email addreses error!", e);
		}
		return addresses;
	}

}
