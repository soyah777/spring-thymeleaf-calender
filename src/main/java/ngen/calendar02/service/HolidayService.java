package ngen.calendar02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    private final WebClient webClient;

    public HolidayService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.national-holidays.jp/").build();
    }

    public Map<String, String> getHoliday(int year) {
        try{
            List<Map<String, String>> holidays =  webClient.get()
                    .uri("https://api.national-holidays.jp/{year}",year)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Map<String, String>>>() {})
                    .block();

            if(holidays == null){
                return Collections.emptyMap();
            }

            return holidays.stream()
                    .collect(Collectors.toMap(
                            holiday -> holiday.get("date"),
                            holiday -> holiday.get("name")
                    ));
        }catch (WebClientResponseException e){
            return Collections.emptyMap();
        }
    }
}