package br.com.msapi.application;

import br.com.msapi.core.ExpensesResponse;
import br.com.msapi.core.TransactionRequest;
import br.com.msapi.core.TransactionResponse;
import br.com.msapi.core.entities.Expenses;

import br.com.msapi.core.entities.Transaction;
import br.com.msapi.core.usecases.TransactionUseCase;
import br.com.msapi.infra.TransactionClient;
import br.com.msapi.infra.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TransactionUseCaseImpl implements TransactionUseCase {

    final static Long MAXIMUM_TIME = 30L;
    final static Long MINIMUN_TIME = 31L;
    final static Long DEADLINE_EXTERNAL_CONSULT = 1L;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionClient transactionClient;

    @Transactional
    public TransactionResponse consultTransactionHistory(TransactionRequest transactionRequest) {
        log.info("Start consultTransactionHistory");

        idValidation(transactionRequest.getId());
        initialDateValidation(transactionRequest.getInitialDateAsLocalDate());
        finalDateValidation(transactionRequest.getFinalDateAsLocalDate());
        validateConsultationTime(transactionRequest.getInitialDateAsLocalDate(),
                transactionRequest.getFinalDateAsLocalDate(), MAXIMUM_TIME);
        validateMinimumDeadlineExternalConsultation(transactionRequest.getInitialDateAsLocalDate(),
                transactionRequest.getFinalDateAsLocalDate(), DEADLINE_EXTERNAL_CONSULT);

        var responseBank = transactionClient.consultTransactionHistory(transactionRequest);
        var transactionResponse = classifierSimulator(responseBank);

        var transactionMapper = toTransaction(transactionResponse);

        log.info("Finish consultTransactionHistory");
        return toTransactionResponse(saveTransaction(transactionMapper));
    }

    @Async
    @Transactional
    public CompletableFuture<TransactionResponse> consultLargeVolumeTransactionHistory(TransactionRequest transactionRequest) {
        log.info("Start consultLargeVolumeTransactionHistory");

        idValidation(transactionRequest.getId());
        initialDateValidation(transactionRequest.getInitialDateAsLocalDate());
        finalDateValidation(transactionRequest.getFinalDateAsLocalDate());
        validateConsultationTime(transactionRequest.getInitialDateAsLocalDate(),
                transactionRequest.getFinalDateAsLocalDate(), MINIMUN_TIME);
        validateMinimumDeadlineExternalConsultation(transactionRequest.getInitialDateAsLocalDate(),
                transactionRequest.getFinalDateAsLocalDate(), DEADLINE_EXTERNAL_CONSULT);

        var responseBank = transactionClient.consultTransactionHistory(transactionRequest);
        var transactionResponse = classifierSimulator(responseBank);

        var transactionMapper = toTransaction(transactionResponse);
        var response = toTransactionResponse(saveTransaction(transactionMapper));

        log.info("Finish consultLargeVolumeTransactionHistory");
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public boolean idValidation(Long id) {
        return true;
    }

    @Override
    public boolean initialDateValidation(LocalDate initialDate) {
        return true;
    }

    @Override
    public boolean finalDateValidation(LocalDate finalDate) {
        return true;
    }

    @Override
    public boolean validateConsultationTime(LocalDate initialDate, LocalDate finalDate, Long maximumTime) {
        return true;
    }

    @Override
    public boolean validateMinimumDeadlineExternalConsultation(LocalDate initialDate, LocalDate finalDate, Long deadlineExternalConsult) {
        //Validar se a data informada se já existe uma transação no banco de dados igual á solicitada. Com prazo minimo estimado.
        return true;
    }

    private TransactionResponse classifierSimulator(String responseBank) {
        return TransactionResponse.builder()
                .expenses(Collections.singletonList(
                        ExpensesResponse.builder()
                                .categoryId(6L)
                                .categoryName("Name category")
                                .purchaseId(3L)
                                .moneySpent(BigDecimal.TEN)
                                .purchaseDate(Timestamp.valueOf(LocalDateTime.now()))
                                .build()))
                .build();
    }

    private Transaction toTransaction(TransactionResponse transactionResponse) {
        log.info("Start toTransaction");
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        var expenses = transactionResponse.getExpenses().stream()
                .map(ex -> modelMapper.map(ex, Expenses.class))
                .toList();

        var trasaction = modelMapper.map(transactionResponse, Transaction.class);
        trasaction.setExpenses(expenses);

        log.info("Finish toTransaction");
        return trasaction;
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        log.info("Start toTransactionResponse");
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        var expenses = transaction.getExpenses().stream()
                .map(ex -> modelMapper.map(ex, ExpensesResponse.class))
                .toList();

        var trasaction = modelMapper.map(transaction, TransactionResponse.class);
        trasaction.setExpenses(expenses);

        log.info("Finish toTransactionResponse");
        return trasaction;
    }

    private Transaction saveTransaction(Transaction transaction) {
        log.info("Start saveTransaction");
        transaction.setVerison(Timestamp.valueOf(LocalDateTime.now()));
        transaction.getExpenses().forEach(expense -> expense.setVerison(Timestamp.valueOf(LocalDateTime.now())));

        var savedTransaction = transactionRepository.save(transaction);
        transactionRepository.flush();
        //método para confirmar explicitamente a transação e garantir que o registro seja persistido no banco de dados.

        log.info("Finish saveTransaction");
        return savedTransaction;
    }
}
