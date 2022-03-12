package com.example.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

	@Autowired
	private JavaMailSender javaMailSender; //framework provide a reference straight away
	
	
	@GetMapping("/sendAsPlain")
	public String sendEmailAsPlain() throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setTo("rrimt2019@gmail.com");
        helper.setSubject("Testing from Spring Boot");
        helper.setText("<h1>Hello from spring boot app</h1>");
        // default = text/plain
        javaMailSender.send(msg);
        return "success";
    }	
	
	@GetMapping("/sendAsHtml")
	public String sendEmailAsHtml() throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("rrimt2019@gmail.com");
        helper.setSubject("Testing from Spring Boot");
        helper.setText("<h1>Hello from spring boot app</h1>", true);
        // true = text/html
         javaMailSender.send(msg);
        return "success";
    }	
	
	
	@GetMapping("/mailWithAttachment")
	public String sendEmailWithAttachment() throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("rrimt2019@gmail.com");
        helper.setSubject("Testing from Spring Boot");
        helper.setText("<h1>Pls find an attachment</h1>", true);
        // default = text/plain
        // true = text/html
        helper.addAttachment("myappconfig.properties", new ClassPathResource("application.properties"));
        //test.txt should be in the resourses folder
        javaMailSender.send(msg);
        return "success";
    }	
	
	@GetMapping("mailwithSpecicMailAccount")
	public String sendMailwithSpecicMailAccount(){
		JavaMailSenderImpl javaMailSender = getJavaMailSender();	
		MimeMessage message = javaMailSender.createMimeMessage(); 
		try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setText(getHtmlBody(), true);
	        helper.setSubject(getSubject());
	        helper.setTo("rrimt2019@gmail.com");
	        javaMailSender.send(message);
	        System.out.println("mail sent");
		}
		catch(Exception ex) {
			System.out.println("exception while sending mail" + ex);
		}
		return "success";		
	}
	
	private JavaMailSenderImpl getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setUsername("vijendrakumar252@gmail.com");
	    mailSender.setPassword("maa@Basanti1965");
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");	    
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.starttls.required", true);
	    props.put("mail.smtp.connectiontimeout", 5000);
	    props.put("mail.smtp.timeout", 5000);
	    props.put("mail.smtp.writetimeout", 5000);
	    return mailSender;
	}
	private String getSubject() {
		return "subject - content ";
	}
	private String getHtmlBody() {
		String str = "<h2>I am in the body </h2>";
		return str;
	}	
}

