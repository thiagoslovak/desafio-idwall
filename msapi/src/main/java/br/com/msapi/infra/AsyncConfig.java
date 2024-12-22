package br.com.msapi.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public Executor getAsyncExecutorLargeVolumeTransactionHistory() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //corePoolSize é o número mínimo de trabalhadores a serem mantidos vivos
        executor.setCorePoolSize(1);

        //maxPoolSize define o número máximo de threads que podem ser criados
        executor.setMaxPoolSize(1);

        //O número máximo de tarefas que podem ser enfileiradas para execução quando todos os threads principais no pool de threads estão ocupados
        executor.setQueueCapacity(25);

        executor.setThreadNamePrefix("AsyncTask - LargeVolumeTransactionHistory");
        executor.initialize();
        return executor;
    }
}
