package com.banking.app.bankingApp.service.email;

import com.banking.app.bankingApp.database.email.DBEmailVerificationCode;
import com.banking.app.bankingApp.database.email.DBEmailVerificationCodeDatabaseService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailVerificationManagementService {
    @Autowired
    DBEmailVerificationCodeDatabaseService dbEmailVerificationCodeDatabaseService;

    public void checkVerificationCode(String code, String userId) {
        DBEmailVerificationCode dbEmailVerificationCode = dbEmailVerificationCodeDatabaseService.getVerificationCode(userId, code);
        List<DBEmailVerificationCode> dbEmailVerificationCodeList = dbEmailVerificationCodeDatabaseService.getPreviousCodes(userId);
        LocalDateTime now = LocalDateTime.now();
        java.time.LocalDateTime mailTime = dbEmailVerificationCode.getCreatedAt();
        LocalDateTime mailTimeJoda = new LocalDateTime(mailTime.getYear(), mailTime.getMonthValue(), mailTime.getDayOfMonth(), mailTime.getHour(), mailTime.getMinute(), mailTime.getSecond());
        LocalDateTime mailTimeJodaExpires = mailTimeJoda.plusMinutes(30);
        if (now.isAfter(mailTimeJodaExpires)) {
            dbEmailVerificationCodeDatabaseService.updateVerificationCodeFlag(dbEmailVerificationCode.getId(), "Expired");
            throw new IllegalStateException();
        } else if (dbEmailVerificationCode.getFlag().equalsIgnoreCase("Expired") || dbEmailVerificationCode.getFlag().equalsIgnoreCase("Used")) {
            throw new IllegalStateException();
        } else if (dbEmailVerificationCodeList.size() != 0) {
            for (int i = 0; i < dbEmailVerificationCodeList.size() - 1; i++) {
                if (dbEmailVerificationCodeList.get(i).getFlag().equalsIgnoreCase("Pending")) {
                    dbEmailVerificationCodeList.get(i).setFlag("Expired");
                }
            }
        } else {
            dbEmailVerificationCodeDatabaseService.updateVerificationCodeFlag(dbEmailVerificationCode.getId(), "Used");
        }
    }
}
