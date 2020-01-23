package com.banking.app.bankingApp.service.analytics;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.response.analytics.Analytics;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsManagementService {
    private static final AnalyticsManagementService analiticsManagementService = new AnalyticsManagementService();
    private BalanceManagementService balanceManagementService;
    private TransactionsDatabaseService transactionsDatabaseService;
    private AccountManagementService accountManagementService;

    private AnalyticsManagementService() {
        accountManagementService = AccountManagementService.getInstance();
        transactionsDatabaseService = TransactionsDatabaseService.getInstance();
        balanceManagementService = BalanceManagementService.getInstance();
    }

    public static AnalyticsManagementService getInstance() {
        return analiticsManagementService;
    }

    public List<Analytics> getAnalitics(String accountId, String startDate, String endDate) {
        validateAnaliticsAccountId(accountId);
        DateTimeFormatter formatterJodaLocalDateTime = DateTimeFormat.forPattern("yyyy-MM");
        LocalDateTime startingDate = new LocalDateTime(formatterJodaLocalDateTime.parseDateTime(startDate));
        LocalDateTime endingDate = new LocalDateTime(formatterJodaLocalDateTime.parseDateTime(endDate));
        validateAnaliticsDate(startingDate, endingDate);
        LocalDateTime currentDate = startingDate;
        LocalDateTime nextDate = currentDate.plusMonths(1);
        List<Analytics> analyticsList = new ArrayList<>();
        while (currentDate.isBefore(endingDate)) {
            String currentDateString = currentDate.toString("yyyy-MM");
            java.time.format.DateTimeFormatter formatterJavaLocalDateTime = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM");
            java.time.LocalDateTime currentDateJavaType = java.time.LocalDateTime.of(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth(), 00, 00);

            String nextDateString = nextDate.toString("yyyy-MM");
            java.time.LocalDateTime nextDateJavaType = java.time.LocalDateTime.of(nextDate.getYear(), nextDate.getMonthOfYear(), nextDate.getDayOfMonth(), 00, 00);

            Analytics analytics = new Analytics();
            List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllTransactionsByDateForId(accountId,currentDateJavaType, nextDateJavaType);

            analytics.setDate(currentDateJavaType);
            Double expenses = 0.0;
            Double income = 0.0;

            for (int i = 0; i < dbTransactions.size(); i++) {
                if (dbTransactions.get(i).getSourceAccountId().getId().equals(accountId)) {
                    expenses = expenses + dbTransactions.get(i).getAmount();
                }
                if (dbTransactions.get(i).getDestinationAccountId().getId().equals(accountId)) {
                    income = income + dbTransactions.get(i).getAmount();
                }

            }
            analytics.setExpenses(expenses);
            analytics.setIncome(income);

            analyticsList.add(analytics);
            currentDate = nextDate;
            nextDate = nextDate.plusMonths(1);
            currentDateString = currentDate.toString("yyyy-MM");
            currentDateJavaType = java.time.LocalDateTime.of(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfWeek(), currentDate.getHourOfDay(), currentDate.getMinuteOfHour());
            nextDateString = nextDate.toString("yyyy-MM");
            nextDateJavaType = java.time.LocalDateTime.of(nextDate.getYear(), nextDate.getMonthOfYear(), nextDate.getDayOfWeek(), nextDate.getHourOfDay(), nextDate.getMinuteOfHour());

        }
        return analyticsList;
    }

    private void validateAnaliticsDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (!(startDate.isBefore(endDate))|| startDate.equals(endDate)) {
            throw new IllegalStateException();
        }
    }

    private void validateAnaliticsAccountId(String id) {
        accountManagementService.getAccountById(id);
    }
}
