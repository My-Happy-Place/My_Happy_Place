package consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ConsumerApi {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE).build();
    }
}
