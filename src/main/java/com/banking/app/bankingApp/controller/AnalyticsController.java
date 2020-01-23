package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.response.analytics.Analytics;
import com.banking.app.bankingApp.service.analytics.AnalyticsManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
public class AnalyticsController {

    private AnalyticsManagementService analyticsManagementService;

    public AnalyticsController() {
        analyticsManagementService = AnalyticsManagementService.getInstance();
    }

    @GetMapping(value = "/analytics/{accountId}")
    public ResponseEntity<List<Analytics>> getAnalitics(
            @PathVariable("accountId") String accountId,
            @RequestParam(name = "startDate", required = true) String startDate,
            @RequestParam(name = "endDate", required = true) String endDate) {
      try {
          return ResponseEntity.status(HttpStatus.OK).body(analyticsManagementService.getAnalitics(accountId, startDate, endDate));
      }catch (NoResultException e){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }catch (IllegalStateException e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }catch(RuntimeException e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
}
