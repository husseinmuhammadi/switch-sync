package com.dpi.financial.ftcom.model.to.isc.test;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.converter.isc.transaction.InteractionPointConverter;
import com.dpi.financial.ftcom.model.type.ProcessingCode;
import com.dpi.financial.ftcom.model.type.ProductCode;
import com.dpi.financial.ftcom.model.type.TestConditionType;
import com.dpi.financial.ftcom.model.type.TransactionMode;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.InteractionPoint;

import javax.persistence.*;

/**
 * A test case is a document, which has a set of test data, preconditions,
 * expected results and postconditions, developed for a particular test scenario
 * in order to verify compliance against a specific requirement.
 * <p>
 * Example:
 * Scenario: Verify that the input field that can accept maximum of 10 characters
 * Test Step: Login to application and key in 10 characters
 * Expected Result: Application should be able to accept all 10 characters.
 * Actual Outcome: Application accepts all 10 characters.
 * <p>
 * Scenario: Verify that the input field that can accept maximum of 11 characters
 * Test Step: Login to application and key in 11 characters
 * Expected Result: Application should NOT accept all 11 characters.
 * Actual Outcome: Application accepts all 10 characters.
 */

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SWITCH_TEST_CASE_SEQ")
@Table(name = "SWITCH_TEST_CASE")
@NamedQueries({
        @NamedQuery(name = SwitchTestCase.FIND_ALL, query = "select t from SwitchTestCase t where t.deleted = false ")
})
public class SwitchTestCase extends EntityBase {

    public static final String FIND_ALL = "SwitchTestCase.findAll";

    @Column(name = "TEST_CASE_ID", nullable = true, length = 100)
    private String testCaseID;

    @Column(name = "TEST_SCENARIO", nullable = true, length = 100)
    private String testScenario;

    @Column(name = "TEST_CASE_DESCRIPTION", nullable = true, length = 100)
    private String testCaseDescription;

    @Column(name = "TEST_STEPS", nullable = true, length = 100)
    private String testSteps;

    @Column(name = "PREREQUISITE", nullable = true, length = 100)
    private String prerequisite;

    @Column(name = "TEST_DATA", nullable = true, length = 100)
    private String testData;

    @Column(name = "EXPECTED_RESULT", nullable = true, length = 100)
    private String expectedResult;

    @Column(name = "TEST_PARAMETERS", nullable = true, length = 100)
    private String testParameters;

    @Column(name = "ACTUAL_RESULT", nullable = true, length = 100)
    private String actualResult;

    @Column(name = "ENVIRONMENT_INFORMATION", nullable = true, length = 100)
    private String environmentInformation;

    @Column(name = "COMMENTS", nullable = true, length = 100)
    private String comments;

    @Column(name = "FINANCIAL_SERVICE_PROVIDER", length = 1)
    private String financialServiceProvider;

    @Column(name = "INTERACTION_POINT", length = 1)
    @Convert(converter = InteractionPointConverter.class)
    private InteractionPoint interactionPoint;

    @Column(name = "TEST_CONDITION_TYPE", nullable = true, length = 1)
    @Convert(converter = TestConditionTypeConverter.class)
    private TestConditionType testConditionType;

    @Column(name = "PROCESSING_CODE", nullable = true, length = 2)
    @Convert(converter = ProcessingCodeConverter.class)
    private ProcessingCode processingCode;

    @Column(name = "PRODUCT_CODE", nullable = true, length = 3)
    @Convert(converter = ProductCodeConverter.class)
    private ProductCode productCode;

    @Column(name = "DEVICE_CODE", nullable = true, length = 3)
    @Convert(converter = DeviceCodeConverter.class)
    private DeviceCode deviceCode;

    @Column(name = "TRANSACTION_MODE", nullable = true, length = 8)
    @Convert(converter = TransactionModeConverter.class)
    private TransactionMode transactionMode;

    @Column(name = "REVERSED", nullable = true)
    private boolean reversed;

    @Column(name = "SCENARIO", nullable = false, length = 200)
    private String scenario;

    @Column(name = "PRIMARY_ACCOUNT_NUMBER", nullable = false, length = 19)
    private String primaryAccountNumber;

    @Column(name = "RETRIEVAL_REFERENCE_NUMBER", length = 12)
    private String rrn;

    @Column(name = "SYSTEM_TRACE_AUDIT_NUMBER")
    private Integer stan;

    @Column(name = "RESPONSE_CODE", length = 2)
    private String responseCode;

    public String getTestCaseID() {
        return testCaseID;
    }

    public void setTestCaseID(String testCaseID) {
        this.testCaseID = testCaseID;
    }

    public String getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(String testScenario) {
        this.testScenario = testScenario;
    }

    public String getTestCaseDescription() {
        return testCaseDescription;
    }

    public void setTestCaseDescription(String testCaseDescription) {
        this.testCaseDescription = testCaseDescription;
    }

    public String getTestSteps() {
        return testSteps;
    }

    public void setTestSteps(String testSteps) {
        this.testSteps = testSteps;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getTestParameters() {
        return testParameters;
    }

    public void setTestParameters(String testParameters) {
        this.testParameters = testParameters;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getEnvironmentInformation() {
        return environmentInformation;
    }

    public void setEnvironmentInformation(String environmentInformation) {
        this.environmentInformation = environmentInformation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ProcessingCode getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(ProcessingCode processingCode) {
        this.processingCode = processingCode;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public void setProductCode(ProductCode productCode) {
        this.productCode = productCode;
    }

    public DeviceCode getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(DeviceCode deviceCode) {
        this.deviceCode = deviceCode;
    }

    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public TestConditionType getTestConditionType() {
        return testConditionType;
    }

    public void setTestConditionType(TestConditionType testConditionType) {
        this.testConditionType = testConditionType;
    }

    public String getPrimaryAccountNumber() {
        return primaryAccountNumber;
    }

    public void setPrimaryAccountNumber(String primaryAccountNumber) {
        this.primaryAccountNumber = primaryAccountNumber;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Integer getStan() {
        return stan;
    }

    public void setStan(Integer stan) {
        this.stan = stan;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public InteractionPoint getInteractionPoint() {
        return interactionPoint;
    }

    public void setInteractionPoint(InteractionPoint interactionPoint) {
        this.interactionPoint = interactionPoint;
    }
}