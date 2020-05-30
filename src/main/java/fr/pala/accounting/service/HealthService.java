package fr.pala.accounting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;


@Service
public class HealthService {
    private final RestTemplate restTemplate;
    @Value("${ocr.token}")
    private String ocrToken;
    String url = "https://api.ocr.space/parse/image?language=fre";

    public HealthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String sendGetToOCR() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", ocrToken);
        headers.set("filetype", "JPG");

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("lorem-ipsum.jpg"));

        String result_ocr = "";
        try{
            HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {
            });

            Map<String, Object> final_response = response.getBody();
            ArrayList<Map<String, Object>> ParsedResults = (ArrayList) final_response.get("ParsedResults");

            Map<String, Object> parsedResult = ParsedResults.get(0);

            result_ocr = (String) parsedResult.get("ParsedText");
        }
        catch (Exception e){
            throw e;
        }

        return result_ocr;
    }

}
