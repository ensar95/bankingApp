package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.response.analytics.Analytics;
import com.banking.app.bankingApp.service.analytics.AnalyticsManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
public class AnalyticsController {

    private AnalyticsManagementService analyticsManagementService;
    private TokenUtil tokenUtil;
    public AnalyticsController() {
        tokenUtil=TokenUtil.getInstance();
        analyticsManagementService = AnalyticsManagementService.getInstance();
    }

    @GetMapping(value = "/analytics/{accountId}")
    public ResponseEntity<List<Analytics>> getAnalitics(
            @PathVariable("accountId") String accountId,
            @RequestParam(name = "startDate", required = true) String startDate,
            @RequestParam(name = "endDate", required = true) String endDate,
            @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId=tokenUtil.getIdFromToken(authorization);
            return ResponseEntity.status(HttpStatus.OK).body(analyticsManagementService.getAnalytics(accountId, startDate, endDate, userId));
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
