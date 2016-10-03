package com.en.std.message.factory;

import com.en.std.definition.ProcessingCode;
import com.en.std.exception.TypeNotFoundException;
import com.en.std.field.base.ISOFieldBase;
import com.en.std.message.*;
import org.jpos.iso.ISOMsg;

import com.en.std.message.base.ITransaction;

/**
 * Created by h.mohammadi on 6/15/2016.
 */
public class TransactionFactory {
    public ITransaction getTransaction(String processingCode) {
        ITransaction transaction = null;
        try {
            transaction = getTransaction(ProcessingCode.getInstance(processingCode));
        } catch (TypeNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transaction;
    }

    public ITransaction getTransaction(ProcessingCode processingCode) {
        ITransaction transaction = null;

        switch (processingCode){
            case Purchase:
                transaction = new Purchase();
                break;
            case CashWithdrawal:
                transaction = new CashWithdrawal();
                break;
            case BillPayment:
                transaction=new BillPayment();
                break;
            case BalanceEnquiry:
                transaction = new BalanceEnquiry();
                break;
            case CustomerEnquiry:
                transaction = new CustomerEnquiry();
                break;
            case FundTransfer:
                transaction = new FundTransfer();
                break;
            case FundTransferDr:
                transaction = new FundTransferDr();
                break;
            case FundTransferCr:
                transaction = new FundTransferCr();
                break;
            case PinChange:
                transaction = new PinChange();
                break;
            case MiniStatement:
                transaction = new MiniStatement();
                break;
            case PinVerification:
                transaction = new PinVerification();
                break;
            default:
                break;
        }

        return transaction;
    }

    public ITransaction getTransaction(ISOMsg isoMsg) {
        ITransaction transaction = null;

        String processingCode1 = isoMsg.getString(ISOFieldBase.PROCESSING_CODE);
        String processingCode = null;
        if(processingCode1 != null)
            processingCode = (isoMsg.getString(ISOFieldBase.PROCESSING_CODE)).substring(0, 2);
        if (processingCode!= null)
            transaction = getTransaction(processingCode);

        transaction.setISOMsg(isoMsg);
        return transaction;
    }
}
