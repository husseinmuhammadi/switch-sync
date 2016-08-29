package com.dpi.financial.ftcom.model.to;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "PRODUCT_MASTER_SEQ")
@Table(name = "PRODUCT_MASTER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"INSTITUTION_CODE", "PRODUCT_CODE"})
})
public class Product extends EntityBase {

    @Column(name = "PRODUCT_CODE", nullable = false, length = 3)
    private String productCode;

    @Column(name = "INSTITUTION_CODE", nullable = false, length = 3)
    private String institutionCode;

    @Column(name = "PRODUCT_TYPE", length = 15)
    private String productType;

    @Column(name = "CARD_LENGTH")
    private int cardLength;

//    SERVICE_CODE              VARCHAR2(10 BYTE),
//    CHECK_DIGIT               VARCHAR2(1 BYTE),
//    PIN_LENGTH                NUMBER(2),
//    CARD_CARRIER              NUMBER(3),
//    WELCOMELETTER             VARCHAR2(1 BYTE),
//    CVC2                      VARCHAR2(1 BYTE),
//    CVV1                      VARCHAR2(1 BYTE),
//    PVV                       VARCHAR2(1 BYTE),
//    DELIVERY_MODE             VARCHAR2(40 BYTE),
//    VALIDITY                  NUMBER(1),
//    ACTIVATION_CRITERIA       VARCHAR2(1 BYTE),
//    RENEWAL_ADVICE            VARCHAR2(1 BYTE),
//    RENEWAL_PIN               VARCHAR2(1 BYTE),
//    ADVANCE_RENEWAL           VARCHAR2(1 BYTE),
//    AUTORENEW_BEFORE          NUMBER(2),
//    TXN_SETID                 NUMBER(3),
//    BILLING_CYCLE             NUMBER(1),
//    LOYALTY_PLANID            NUMBER(3),
//    FEE_PLAN_CODE             NUMBER(3),
//    PINRETRY_LIMIT            NUMBER(2),
//    PINTRANSACTION_FIRST      VARCHAR2(1 BYTE),
//    AUTHORIZATION_LEVEL       VARCHAR2(10 BYTE),
//    PROCESSING_MODE           VARCHAR2(10 BYTE),
//    VERIFY_TRACK              VARCHAR2(1 BYTE),
//    SAFRETRIES                NUMBER(2),
//    REGISTERATIONREQ          VARCHAR2(1 BYTE),
//    MAX_BAL_BEFOR_REGISTER    FLOAT(126),
//    MAX_LOAD_BEFORE_REGISTER  FLOAT(126),
//    TXNSET_BEFORE_REGISTER    NUMBER(3),
//    ALLOW_RENEWAL             VARCHAR2(1 BYTE),
//    AUTORENEWAL               VARCHAR2(1 BYTE),
//    INACTIVE_AFTERMONTHS      NUMBER(3),
//    MAX_DEBIT_LIMIT           FLOAT(126),
//    MAX_CREDIT_LIMIT          FLOAT(126),
//    SUPPL_CARD_ALLOWED        VARCHAR2(1 BYTE),
//    PRI_CARD_PROD_CODE        NUMBER(3),
//    RECORDSTATUS              NUMBER(1),
//    CREATED_BY                VARCHAR2(40 BYTE),
//    CREATED_ON                DATE,
//    MODIFIED_BY               VARCHAR2(40 BYTE),
//    MODIFIED_ON               DATE,
//    COURIER_AGENT             NUMBER(3),
//    KYC_VALIDATION_REQUIRED   NUMBER(1),
//    PIN_REQ                   VARCHAR2(1 BYTE),
//    PRDCT_MSTR_ISN            INTEGER,
//    PRDCT_MSTR_ISDELETED      CHAR(1 BYTE),
//    MAXLOADAPPLICABLE         NUMBER(17,6),
//    ALLOWED_MOVEFUNDS_TYPE    VARCHAR2(10 BYTE),
//    SUPPCARDFEECODE1          INTEGER,
//    SUPPCARDFEECODE2          INTEGER,
//    TEMPLATE_PATH             VARCHAR2(100 BYTE),
//    IS_CARD_DTLS_MODIFY       CHAR(1 BYTE),
//    IS_CUSTM_MSG_MODIFY       CHAR(1 BYTE),
//    IS_CSTM_MSG_PRINT         CHAR(1 BYTE),
//    IS_ISSUER_NAME_PRINT      CHAR(1 BYTE),
//    IS_ISUANCE_CHRG_MODIFY    CHAR(1 BYTE),
//    MAX_AMOUNT                FLOAT(126),
//    MIN_AMOUNT                FLOAT(126),
//    CUSTOM_MSG                VARCHAR2(250 BYTE),
//    IS_TXN_DTLS_MODIFY        CHAR(1 BYTE)        DEFAULT 'Y'                   NOT NULL,
//    MACNA_REQIRED             VARCHAR2(1 BYTE)    DEFAULT 'N',
//    IS_ACTIVE                 VARCHAR2(1 BYTE)    DEFAULT 'Y'


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getCardLength() {
        return cardLength;
    }

    public void setCardLength(int cardLength) {
        this.cardLength = cardLength;
    }
}
