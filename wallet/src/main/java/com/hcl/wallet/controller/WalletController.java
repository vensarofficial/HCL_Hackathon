package com.hcl.wallet.controller;

import com.hcl.wallet.dto.ApiResponse;
import com.hcl.wallet.dto.WalletSummaryDTO;
import com.hcl.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    @Autowired
    private final WalletService walletService;

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<WalletSummaryDTO>> getWallet(@PathVariable String customerId) {
        WalletSummaryDTO dto = walletService.getWalletSummary(customerId);
        ApiResponse<WalletSummaryDTO> response = ApiResponse.<WalletSummaryDTO>builder()
                .message("Wallet details fetched successfully")
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();
        return ResponseEntity.ok(response);
    }
}
