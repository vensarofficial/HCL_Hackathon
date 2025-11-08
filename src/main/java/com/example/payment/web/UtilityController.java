package com.example.payment.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UtilityController {

    @GetMapping("/api/utils/idempotency-key")
    public Map<String, String> generateKey() {
        Map<String, String> response = new HashMap<>();
        response.put("idempotencyKey", UUID.randomUUID().toString());
        return response;
    }
}
