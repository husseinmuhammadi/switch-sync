package com.dpi.financial.ftcom.model.to.isc.test;

import com.dpi.financial.ftcom.model.base.EntityBase;
import com.dpi.financial.ftcom.model.converter.*;
import com.dpi.financial.ftcom.model.converter.isc.transaction.InteractionPointConverter;
import com.dpi.financial.ftcom.model.type.*;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.model.type.isc.transaction.InteractionPoint;

import javax.persistence.*;

/**
 * http://www.softwaretestingclass.com/what-is-difference-between-test-cases-vs-test-scenarios/
 * Test Scenario: The exhaustive testing is not possible due to large number of data combinations and large number of possible paths in the software. Scenario testing is to make sure that end to end functionality of application under test is working as expected. Also check if the all business flows are working as expected. In scenario testing tester need to put his/her foot in the end users shoes to check and perform the action as how they are using application under test. In scenario testing the preparation of scenarios would be the most important part, to prepare the scenario tester needs to consult or take help from the client, stakeholder or developers.
 * <p>
 * <p>
 * https://blog.testlodge.com/whats-the-difference-between-test-case-and-test-scenario/
 * Test Scenario
 * The purpose of scenario testing is to test the end-to-end functionality of a software application and ensure the business processes and flows are functioning as needed. In scenario testing, the tester puts themselves in the users shoes and determines real world scenarios (use-cases) that can be performed. Once these test scenarios are determined, test cases can be written for each scenario. Test scenarios are the high level concept of what to test.
 * Test Case
 * A test case is a set of steps to be executed by the tester in order to validate the scenario. Whereas test scenarios are derived from use-cases, test cases are derived and written from the test scenarios. A test scenario usually has multiple test cases associated with it, as test cases layout out the low-level details on how to test the scenario.
 * <p>
 * Example
 * Test Scenario: Validate the login page
 * Test Case 1: Enter a valid username and password
 * Test Case 2: Reset your password
 * Test Case 3: Enter invalid credentials
 * <p>
 * <p>
 * http://www.guru99.com/test-scenario.html
 * Test Scenario - A Scenario is any functionality that can be tested. It is also called Test Condition or Test Possibility.
 */

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SWITCH_TEST_SCENARIO_SEQ")
@Table(name = "SWITCH_TEST_SCENARIO")
@NamedQueries({
        @NamedQuery(name = SwitchTestScenario.FIND_ALL, query = "select t from SwitchTestScenario t where t.deleted = false")
})
public class SwitchTestScenario extends EntityBase {

    public static final String FIND_ALL = "SwitchTestScenario.findAll";

    @Column(name = "TEST_CONDITION_TYPE", nullable = true, length = 1)
    @Convert(converter = TestConditionTypeConverter.class)
    private TestConditionType testConditionType;

    @Column(name = "FINANCIAL_SERVICE_PROVIDER", length = 1)
    private String financialServiceProvider;

    @Column(name = "INTERACTION_POINT", nullable = false, length = 1)
    @Convert(converter = InteractionPointConverter.class)
    private InteractionPoint interactionPoint;

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

    @Column(name = "ENVIRONMENT_INFORMATION", nullable = true, length = 200)
    private String environmentInformation;

    @Column(name = "COMMENTS", nullable = true, length = 200)
    private String comments;

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

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
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

    public TestConditionType getTestConditionType() {
        return testConditionType;
    }

    public void setTestConditionType(TestConditionType testConditionType) {
        this.testConditionType = testConditionType;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public InteractionPoint getInteractionPoint() {
        return interactionPoint;
    }

    public void setInteractionPoint(InteractionPoint interactionPoint) {
        this.interactionPoint = interactionPoint;
    }
}