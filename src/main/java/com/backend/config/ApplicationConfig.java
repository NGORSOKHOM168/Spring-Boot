package com.backend.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserService userService; // Inject UserService dependency via constructor

    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;

    /**
     * Configures a UserDetailsService that uses the injected UserService to find a user by email.
     *
     * @return UserDetailsService instance
     */
    @Bean
    UserDetailsService userDetailsService() {
        return userService;
    }

    /**
     * Configures an AuthenticationProvider that uses the userDetailsService and passwordEncoder to authenticate users.
     *
     * @return AuthenticationProvider instance
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures an AuthenticationManager using the AuthenticationConfiguration instance.
     *
     * @param configure the AuthenticationConfiguration instance
     * @return AuthenticationManager instance
     * @throws Exception if there is an error configuring the authentication manager
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures a BCryptPasswordEncoder instance to encode passwords.
     * This method is static to avoid the 'Relying upon circular' problem.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a JavaMailSender instance to send email.
     *
     * @return JavaMailSender instance
     */
    @Bean
    JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.debug", "true");										  		

        return mailSender;
    }
}
