package br.com.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@org.junit.Test
	public void testeEmail(){
		StringBuilder stringBuilderTextoEmail=new StringBuilder();
		stringBuilderTextoEmail.append("Olá , <br/><br/>");
		stringBuilderTextoEmail.append("E-mail informativo para teste, dê um Ok <br/><br/>");
		stringBuilderTextoEmail.append("<b>Login:</b> alex@jsjsjsj.com<br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> sdss8s98s<br/><br/>");
		
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"http://projetojavaweb.com/certificado-aluno/login\" style=\"color:#2525a7; padding: 14px 25px; text-align:center; text-decoration: none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border : 3px solid green;background-color:#99DA39;\">Acessar Portal do Aluno</a><br/><br/>");

		
		
		ObjetoEnviaEmail objetoEnviaEmail=new ObjetoEnviaEmail(
				"joaodevelopment7@gmail.com", "João Queiroz", 
				"Teste de Envio", 
				stringBuilderTextoEmail.toString());
		
		objetoEnviaEmail.enviarEmailComAnexo(true);
		//objetoEnviaEmail.enviarEmail(true);
	}
}
