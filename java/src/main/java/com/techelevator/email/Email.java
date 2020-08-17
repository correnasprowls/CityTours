package com.techelevator.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.techelevator.model.Itinerary;
import com.techelevator.model.ItineraryEmail;
import com.techelevator.model.Landmark;

public class Email {
	   public void sendEmail(ItineraryEmail email) {
	      // Put recipient’s address
	      String to = email.getEmail();

	      // Put sender’s address
	      String from = "city.tours.columbus@gmail.com";
	      final String username = "city.tours.columbus@gmail.com";
	      final String password = "ShirtWithBlueStripes";

	      
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true"); 
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	    }
	         });

	      try {
	    // Create a default MimeMessage object.
	    Message message = new MimeMessage(session);
	 
	    // Set From: header field 
	    message.setFrom(new InternetAddress(from));
	 
	    // Set To: header field
	    message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));
	 
	    // Set Subject: header field
	    message.setSubject("City Tours: " + email.getItinerary().getItineraryName());
	 
	    // Put the content of your message
	    String messageBody = "";
	    for (Landmark landmark : email.getItinerary().getLandmarks()) {
	    	messageBody = messageBody + landmark.getLandmarkName() + "\n" + landmark.getLocation().getAddressLineOne() + " " + 
	    		landmark.getLocation().getCityName() + ", " + landmark.getLocation().getStateCode() + " " + landmark.getLocation().getZipCode() + "\n\n" ;
	    	
	    }
	    messageBody = messageBody + "Thank you for using City Tours!";
	    message.setText(messageBody);

	    // Send message
	    Transport.send(message);

	    System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
	}
