package com.pitkwiecien.atm_api.services;

import com.pitkwiecien.atm_api.models.dto.AccountDTO;
import com.pitkwiecien.atm_api.models.dto.BlikDTO;
import com.pitkwiecien.atm_api.models.dto.BlikTransactionDTO;
import com.pitkwiecien.atm_api.models.interfaces.ServiceInterface;
import com.pitkwiecien.atm_api.repositories.AccountRepository;
import com.pitkwiecien.atm_api.repositories.BlikRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
public class BlikTransactionService implements ServiceInterface {
    public BlikTransactionDTO blikTransaction;
    private final BlikRepository blikRepository = new BlikRepository();
    private final AccountRepository accountRepository = new AccountRepository();

    @Override
    public int verify(){
        if(!verifyNotNulledParams()){
            return -2;
        }

        BlikDTO blik = blikRepository.getObjectByKey(blikTransaction.getBlikCode());
        if(blik == null)
            return -3;

        AccountDTO account = accountRepository.getObjectByKey(blik.getCode());
        if(account == null){
            return -4;
        }

        BlikService blikService = new BlikService(blik);
        if(!blikService.verifyBlikOwnership())
            return -5;


        if(!verifyPaymentDate(blikTransaction.getDate()))
            return -6;
        if(!verifyMoneyAmount(blikTransaction.getAmount()))
            return -7;
        if(!verifyInitialValues())
            return -8;

        return 1;
    }

    public boolean verifyPaymentDate(Date date){
        BlikDTO blik = blikRepository.getObjectByKey(blikTransaction.getBlikCode());

        return blik.getCreationDate().before(date) && blik.getExpirationDate().after(date);
    }

    public boolean verifyMoneyAmount(BigDecimal amount){
        BlikDTO blik = blikRepository.getObjectByKey(blikTransaction.getBlikCode());
        AccountDTO account = accountRepository.getObjectByKey(blik.getCode());
        return account.getMoney().compareTo(amount) >= 0;
    }

    public boolean verifyInitialValues(){
        return !blikTransaction.isVerified() && !blikTransaction.isExecuted();
    }

    @Override
    public boolean verifyNotNulledParams(){
        return blikTransaction.getBlikCode() != null
                && blikTransaction.getAmount() != null
                && blikTransaction.getDate() != null;
    }
}