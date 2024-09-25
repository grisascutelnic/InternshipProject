package com.example.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatAiService {

    private final String ollamaApiUrl = "http://localhost:11434/api/chat";

    public String getBotReply(String userMessage) {
        // Răspunsuri predefinite pentru diferite mesaje
        switch (userMessage.toLowerCase()) {
            case "what is the platform?":
                return "The platform allows users to buy and sell recyclable products.";
            case "what products can i sell?":
                return "You can sell recyclable items such as plastic, paper, metal, and electronics.";
            case "where? why?":
                return "The platform is available for users globally to promote environmental sustainability.";
            default:
                // În cazurile neacoperite, apelăm modelul AI
                return getAiGeneratedResponse(userMessage);
        }
    }

    // Metodă care apelează AI-ul folosind metoda callAiModel
    private String getAiGeneratedResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        // Răspunsuri predefinite pentru diferite mesaje
        if (userMessage.toLowerCase().contains("schimb parola") || userMessage.toLowerCase().contains("cum să îmi schimb parola")) {
            return "Pentru a-ți schimba parola, apasă pe 'Setări', apoi pe 'Schimbă parola'. Urmează pașii indicați.";
        } else if (userMessage.toLowerCase().contains("reparatii") || userMessage.toLowerCase().contains("cum să repar")) {
            return "Pentru reparații, te rugăm să ne contactezi la numărul de telefon disponibil pe site.";
        } else if (userMessage.toLowerCase().contains("programare") || userMessage.toLowerCase().contains("cum pot să mă programez")) {
            return "Pentru a programa o întâlnire cu un specialist, accesați pagina anunțului dorit și apăsați pe butonul „Programează-te”. Completați formularul afișat pentru a finaliza procesul.";
        } else if (userMessage.toLowerCase().contains("incredere") || userMessage.toLowerCase().contains("cum pot avea incredere")) {
            return "Încrederea se construiește pe baza evaluărilor și recenziilor lăsate de alți utilizatori. Verificați ratingul și feedback-ul pentru a lua o decizie informată.";
        } else if (userMessage.toLowerCase().contains("posta anunt") || userMessage.toLowerCase().contains("cum pot sa postez un anunt")) {
            return "În primul rând, creați un cont și completați profilul cu informații reale. Apoi, apăsați pe „Creare anunț nou” și urmați instrucțiunile pentru a completa formularul cu detalii despre serviciul oferit.";
        } else if (userMessage.toLowerCase().contains("resetare rating") || userMessage.toLowerCase().contains("cum pot sa imi resetez ratingul")) {
            return "Din păcate, nu aveți opțiunea de a reseta ratingul. Acesta reflectă feedbackul pe care l-ați primit de la utilizatori.";
        } else if (userMessage.toLowerCase().contains("recenzie") || userMessage.toLowerCase().contains("cum pot scrie o recenzie")) {
            return "Puteți lăsa o recenzie pentru orice anunț accesând pagina respectivă și dând clic pe butonul „Recenzii”. Acolo veți putea scrie impresiile dumneavoastră.";
        } else if (userMessage.toLowerCase().contains("plata") || userMessage.toLowerCase().contains("cum pot achita prestatarului")) {
            return "Plata se poate face direct către prestator la finalizarea serviciului, conform înțelegerii convenite între părți.";
        } else {
            // Generăm cererea JSON pentru alte întrebări
            String jsonRequest = String.format(
                    "{ \"model\": \"llama3.1:8b\", \"messages\": [ {\"role\": \"user\", \"content\"" +
                            ": \"Generează un răspuns doar pentru această întrebare: '%s'. " +
                            "Toate răspunsurile trebuie să fie legate de tema prestării serviciilor de reparare la domiciliu." +
                            "Dacă întrebarea este cum se achita serviciul, asigură-te că răspunsul este ca se achita direct prestatarului. " +
                            "Dacă utilizatorul deviază de la subiectul serviciilor de reparații la domiciliu, ADU-L ÎNAPOI la subiect. Păstrează răspunsurile în limita a 25 de cuvinte.\" } ], \"stream\": false }",
                    userMessage
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

            ResponseEntity<String> responseEntity;
            try {
                responseEntity = restTemplate.postForEntity(ollamaApiUrl, requestEntity, String.class);
            } catch (Exception e) {
                return "Error calling AI model: " + e.getMessage();
            }

            // Extract the response body
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                try {
                    JsonNode jsonResponse = objectMapper.readTree(responseEntity.getBody());
                    return jsonResponse.path("message").path("content").asText().trim();
                } catch (Exception e) {
                    return "Error parsing AI model response: " + e.getMessage();
                }
            } else {
                return "Error from AI model: " + responseEntity.getStatusCode();
            }
        }
    }

}