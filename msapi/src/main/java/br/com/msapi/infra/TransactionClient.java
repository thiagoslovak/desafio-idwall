package br.com.msapi.infra;

import br.com.msapi.core.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class TransactionClient {

    @Autowired
    private WebClient webClient;

    public String consultTransactionHistory(TransactionRequest transactionRequest) {
//        return "";
        return webClient
                .get()
                .uri("/bank?id={id}&initialDate={initialDate}&finalDate={finalDate}", transactionRequest.getId(),
                        transactionRequest.getInitialDate(), transactionRequest.getFinalDate())
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Error!")))
                .bodyToMono(String.class)
                .timeout(Duration.of(45000, ChronoUnit.MILLIS))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof RuntimeException))
                .block();
    }
}
