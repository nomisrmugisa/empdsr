package org.pdsr;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.pdsr.master.repo.SyncTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private SyncTableRepository syncRepo;

	public void sendSimpleMessage(String[] to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(syncRepo.findById(CONSTANTS.LICENSE_ID).get().getSync_email());
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);

	}

	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		message.setFrom(syncRepo.findById(CONSTANTS.LICENSE_ID).get().getSync_email());
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new java.io.File(pathToAttachment));
		helper.addAttachment("Invoice", file);

		emailSender.send(message);
	}
}
