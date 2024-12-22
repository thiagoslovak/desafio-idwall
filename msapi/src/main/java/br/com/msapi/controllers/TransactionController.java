package br.com.msapi.controllers;

import br.com.msapi.application.TransactionUseCaseImpl;
import br.com.msapi.core.TransactionRequest;
import br.com.msapi.core.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionUseCaseImpl transactionUseCase;

    @GetMapping("/consult")
    @Operation(summary = "Consult Transaction History.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success in processing the request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access."),
            @ApiResponse(responseCode = "404", description = "Requested resource not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected system error."),
    })
    public ResponseEntity<TransactionResponse> consultTransactionHistory(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionUseCase.consultTransactionHistory(transactionRequest));
    }

    @GetMapping("/consultLargeVolume")
    @Operation(summary = "Consult Large Volume of Transaction History.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success in processing the request."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access."),
            @ApiResponse(responseCode = "404", description = "Requested resource not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected system error.")
    })
    public ResponseEntity<TransactionResponse> consultLargeVolumeTransactionHistory
            (@RequestBody TransactionRequest transactionsRequest) throws ExecutionException, InterruptedException {

        CompletableFuture<TransactionResponse> transactionHistoryFuture = transactionUseCase.
                consultLargeVolumeTransactionHistory(transactionsRequest);

        return ResponseEntity.status(HttpStatus.OK).body(transactionHistoryFuture.get());
    }
}
