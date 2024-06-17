package com.caminando.Caminando.config;


import com.caminando.Caminando.businesslayer.services.dto.user.LoginResponseDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisterUserDTO;
import com.caminando.Caminando.businesslayer.services.dto.user.RegisteredUserDTO;
import com.caminando.Caminando.businesslayer.services.interfaces.generic.Mapper;
import com.caminando.Caminando.datalayer.entities.travel.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class BeansConfiguration {

	@Bean
	public Pageable defaultPageable() {
		int page = 0; // Numero di pagina predefinito (prima pagina)
		int size = 10; // Dimensione della pagina predefinita
		return PageRequest.of(page, size);
	}
	@Bean
	@Scope("singleton")
	Mapper<RegisterUserDTO, User> mapRegisterUser2UserEntity() {
		return (input) -> User.builder()
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withUsername(input.getUsername())
				.withEmail(input.getEmail())
				.withPassword(input.getPassword())
				.build();
	}

	@Bean
	@Scope("singleton")
	Mapper<User, RegisteredUserDTO> mapUserEntity2RegisteredUser() {
		return (input) -> RegisteredUserDTO.builder()
				.withId(input.getId())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withUsername(input.getUsername())
				.withEmail(input.getEmail())
				.withRoles(input.getRoles())
				.build();
	}
	
	@Bean
	@Scope("singleton")
	Mapper<User, LoginResponseDTO> mapUserEntity2LoginResponse() {
		return (input) -> LoginResponseDTO.builder()
				.withId(input.getId())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withUsername(input.getUsername())
				.withEmail(input.getEmail())
				.withUsername(input.getUsername())
				.withRoles(input.getRoles())
				.build();
	}


//	@Bean
//	public JavaMailSender getJavaMailSender(@Value("${gmail.mail.transport.protocol}" ) String protocol,
//											@Value("${gmail.mail.smtp.auth}" ) String auth,
//											@Value("${gmail.mail.smtp.starttls.enable}" )String starttls,
//											@Value("${gmail.mail.debug}" )String debug,
//											@Value("${gmail.mail.from}" )String from,
//											@Value("${gmail.mail.from.password}" )String password,
//											@Value("${gmail.smtp.ssl.enable}" )String ssl,
//											@Value("${gmail.smtp.host}" )String host,
//											@Value("${gmail.smtp.port}" )String port){
//
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost(host);
//		mailSender.setPort(Integer.parseInt(port));
//
//		mailSender.setUsername(from);
//		mailSender.setPassword(password);
//
//		Properties props = mailSender.getJavaMailProperties();
//		props.put("mail.transport.protocol", protocol);
//		props.put("mail.smtp.auth", auth);
//		props.put("mail.smtp.starttls.enable", starttls);
//		props.put("mail.debug", debug);
//		props.put("smtp.ssl.enable",ssl);
//
//		return mailSender;
//	}
}
