package br.com.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
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
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {	
	
	private static final String USERNAME=""; // o e-mail remetente
	private static final String PASSWORD=""; // senha app - google , sua senha...
	
	private String listaDestinatarios="";
	private String Nomeremetente="";
	private String assuntoEmail="";
	private String textoEmail="";
	
	public ObjetoEnviaEmail(String listaDestinatarios,String Nomeremetente, String assuntoEmail,String textoEmail ) {
		this.listaDestinatarios=listaDestinatarios;
		this.Nomeremetente=Nomeremetente;
		this.assuntoEmail=assuntoEmail;
		this.textoEmail=textoEmail;
	}
	
	public void enviarEmail(boolean enviaEmail) {
		try {
			// olhar as configurações do smtp do seu e-mail
			
			Properties properties=new Properties();
			
			properties.put("mail.smtp.ssl.trust", "*"); // evitar possiveis travamento
			properties.put("mail.smtp.auth", "true"); // autorização
			properties.put("mail.smtp.starttls", "true"); // autenticação
			properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor do Google
			properties.put("mail.smtp.port", "465"); // Porta do servidor
			properties.put("mail.smtp.socketFactory.port", "465"); // expecifica a porta a ser conectada pelo Socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socker de conexão SMTP
			
			Session session=Session.getDefaultInstance(properties, new Authenticator() {
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
			
			//System.out.println(session);
			
			Address[] toUsers=InternetAddress.parse(listaDestinatarios);
			
			Message message=new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME, Nomeremetente)); // quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUsers); // quem está recebendo
			message.setSubject(assuntoEmail); // Assunto do e-mail
			
			if(enviaEmail) {
				message.setContent(textoEmail, "text/html;charset=utf-8");
			}else {
			
				message.setText(textoEmail);
			}
			
			Transport.send(message);
			
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public void enviarEmailComAnexo(boolean enviaEmail) {
		try {
			// olhar as configurações do smtp do seu e-mail

			Properties properties=new Properties();

			properties.put("mail.smtp.ssl.trust", "*"); // evitar possiveis travamento
			properties.put("mail.smtp.auth", "true"); // autorização
			properties.put("mail.smtp.starttls", "true"); // autenticação
			properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor do Google
			properties.put("mail.smtp.port", "465"); // Porta do servidor
			properties.put("mail.smtp.socketFactory.port", "465"); // expecifica a porta a ser conectada pelo Socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socker de conexão SMTP

			Session session=Session.getDefaultInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});

			//System.out.println(session);

			Address[] toUsers=InternetAddress.parse(listaDestinatarios);

			Message message=new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME, Nomeremetente)); // quem está enviando
			message.setRecipients(Message.RecipientType.TO, toUsers); // quem está recebendo
			message.setSubject(assuntoEmail); // Assunto do e-mail

			// PART 1 - QUE É TEXTO E DESCRIÇÃO DO E-MAIL
			MimeBodyPart corpoEmail=new MimeBodyPart();

			if(enviaEmail) {
				corpoEmail.setContent(textoEmail, "text/html;charset=utf-8");
			}else {
				corpoEmail.setText(textoEmail);
			}

			// PART2 - QUE SÃO OS ANEXO EM PDF
			MimeBodyPart anexoEmail=new MimeBodyPart();
			// Onde está sendo passado simuladorDePdf() você pode passar seu arquivo gravado no banco..
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePdf(), "application/pdf")));
			anexoEmail.setFileName("anexo.pdf");
			
			
			// Juntando part1 com part2
			Multipart multipart=new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			multipart.addBodyPart(anexoEmail);
			
			message.setContent(multipart);
			
			Transport.send(message);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*Este método simula pdf ou qualquer arquivo que possa ser enviado por anexo
	 * que pode estar em um banco de dados , pasta..
	 * 
	 * - Retorna um pdf em branco com um texto paragrafo de exemplo*/
	private FileInputStream simuladorDePdf() throws Exception{
		Document document=new Document();
		File file=new File("fileanexo.pdf");
		file.createNewFile();
		
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteúdo do PDF em anexo com Java Mail"));
		document.close();
		
		return new FileInputStream(file);
	}

}
