package consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class ConsumerApi {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return null;
    }
}
