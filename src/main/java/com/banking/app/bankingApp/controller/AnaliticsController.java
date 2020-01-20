package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.response.analitics.Analitics;
import com.banking.app.bankingApp.service.analitics.AnaliticsManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnaliticsController {

    private AnaliticsManagementService analiticsManagementService;

    public AnaliticsController() {
        analiticsManagementService = AnaliticsManagementService.getInstance();
    }

    @GetMapping(value = "/analitics/{accountId}")
    public ResponseEntity<List<Analitics>> getAnalitics(
            @PathVariable("accountId") String accountId,
            @RequestParam(name = "startDate", required = true) String startDate,
            @RequestParam(name = "endDate", required = true) String endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(analiticsManagementService.getAnalitics(accountId, startDate, endDate));

    }
}
