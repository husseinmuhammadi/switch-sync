package com.dpi.financial.ftcom.model.to;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

/**
 * A test case is a document, which has a set of test data, preconditions,
 * expected results and postconditions, developed for a particular test scenario
 * in order to verify compliance against a specific requirement.
 *
 * Example:
 * Scenario: Verify that the input field that can accept maximum of 10 characters
 * Test Step: Login to application and key in 10 characters
 * Expected Result: Application should be able to accept all 10 characters.
 * Actual Outcome: Application accepts all 10 characters.
 *
 * Scenario: Verify that the input field that can accept maximum of 11 characters
 * Test Step: Login to application and key in 11 characters
 * Expected Result: Application should NOT accept all 11 characters.
 * Actual Outcome: Application accepts all 10 characters.
 *
 */

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "TEST_CASE_SEQ")
@Table(name = "TEST_CASE")
@NamedQueries({
        @NamedQuery(name = TestCase.FIND_ALL, query = "select t from TestCase t where t.deleted = false ")
})
public class TestCase extends EntityBase {

    public static final String FIND_ALL = "TestCase.findAll";

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
}