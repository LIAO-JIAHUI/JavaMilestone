package com.example.mils.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@Configuration
public class FirebaseConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        FirebaseApp firebaseApp;
        List<FirebaseApp> apps = FirebaseApp.getApps();
        if (apps.size() == 0) {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions
                    .builder()
                    .setCredentials(googleCredentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        } else {
            firebaseApp = apps.get(0);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}