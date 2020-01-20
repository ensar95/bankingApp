package com.banking.app.bankingApp.service.analitics;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.response.analitics.Analitics;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class AnaliticsManagementService {
    private static final AnaliticsManagementService analiticsManagementService = new AnaliticsManagementService();
    private BalanceManagementService balanceManagementService;
    private TransactionsDatabaseService transactionsDatabaseService;
    public AnaliticsManagementService() {
        transactionsDatabaseService=TransactionsDatabaseService.getInstance();
        balanceManagementService=BalanceManagementService.getInstance();
    }

    public static AnaliticsManagementService getInstance() {
        return analiticsManagementService;
    }

    public List<Analitics> getAnalitics(String accountId, String startDate, String endDate ) {
        DateTimeFormatter formatter= DateTimeFormat.forPattern("yyyy-MM");
        LocalDateTime startingDate = new LocalDateTime(formatter.parseDateTime(startDate));
        LocalDateTime endingDate = new LocalDateTime(formatter.parseDateTime(endDate));
        validateAnalitics(startingDate,endingDate);
        LocalDateTime currentDate = startingDate;
        LocalDateTime nextDate = currentDate.plusMonths(1);
        List<Analitics> analiticsList = new ArrayList<>();
        while (startingDate.isBefore(endingDate)) {
            Analitics analitics = new Analitics();
            List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllTransactionsByDate(currentDate, nextDate);
            analitics.setDate(currentDate);
            Double income = 0.0;
            for (int i = 0; i < dbTransactions.size(); i++) {
                if (dbTransactions.get(i).getSourceAccountId().equals(accountId)) {
                    income = income + dbTransactions.get(i).getAmount();
                }
            }
            analitics.setIncome(income);
            Double expense = 0.0;
            for (int i = 0; i < dbTransactions.size(); i++) {
                if (dbTransactions.get(i).getDestinationAccountId().equals(accountId)) {
                    expense = expense + dbTransactions.get(i).getAmount();
                }
            }
            analitics.setExpenses(expense);
            analiticsList.add(analitics);
            currentDate.plusMonths(1);
            nextDate.plusMonths(1);
        }
        return analiticsList;
    }

    private void validateAnalitics(LocalDateTime startDate, LocalDateTime endDate) {
        if (!(startDate.isBefore(endDate))) {
            throw new IllegalStateException();
        }
    }
}
