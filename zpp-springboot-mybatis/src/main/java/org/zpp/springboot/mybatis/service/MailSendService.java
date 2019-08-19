package org.zpp.springboot.mybatis.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 

@Service
public class MailSendService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private String sendUser="test";
	
	public void sendEmail(String toMail, String title, String content) throws MessagingException {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		message.setFrom(sendUser);
		message.setTo(toMail);
		message.setSubject(title);
		message.setText(content);

		javaMailSender.send(mimeMessage);
	}
	

}