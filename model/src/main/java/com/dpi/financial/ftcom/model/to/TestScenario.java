package com.dpi.financial.ftcom.model.to;

import com.dpi.financial.ftcom.model.base.EntityBase;

import javax.persistence.*;

/**
 *
 * http://www.softwaretestingclass.com/what-is-difference-between-test-cases-vs-test-scenarios/
 * Test Scenario: The exhaustive testing is not possible due to large number of data combinations and large number of possible paths in the software. Scenario testing is to make sure that end to end functionality of application under test is working as expected. Also check if the all business flows are working as expected. In scenario testing tester need to put his/her foot in the end users shoes to check and perform the action as how they are using application under test. In scenario testing the preparation of scenarios would be the most important part, to prepare the scenario tester needs to consult or take help from the client, stakeholder or developers.
 *
 *
 * https://blog.testlodge.com/whats-the-difference-between-test-case-and-test-scenario/
 * Test Scenario
 * The purpose of scenario testing is to test the end-to-end functionality of a software application and ensure the business processes and flows are functioning as needed. In scenario testing, the tester puts themselves in the users shoes and determines real world scenarios (use-cases) that can be performed. Once these test scenarios are determined, test cases can be written for each scenario. Test scenarios are the high level concept of what to test.
 * Test Case
 * A test case is a set of steps to be executed by the tester in order to validate the scenario. Whereas test scenarios are derived from use-cases, test cases are derived and written from the test scenarios. A test scenario usually has multiple test cases associated with it, as test cases layout out the low-level details on how to test the scenario.
 *
 * Example
 * Test Scenario: Validate the login page
 * Test Case 1: Enter a valid username and password
 * Test Case 2: Reset your password
 * Test Case 3: Enter invalid credentials
 *
 *
 * http://www.guru99.com/test-scenario.html
 * Test Scenario - A Scenario is any functionality that can be tested. It is also called Test Condition or Test Possibility.
 *
 */

@Entity
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "TEST_SCENARIO_SEQ")
@Table(name = "TEST_SCENARIO")
@NamedQueries({
        @NamedQuery(name = TestScenario.FIND_ALL, query = "select s from TestScenario s")
})
public class TestScenario extends EntityBase {

    public static final String FIND_ALL = "TestScenario.findAll";

    @Column(name = "SCENARIO", nullable = false, length = 200)
    private String scenario;

    @Column(name = "ENVIRONMENT_INFORMATION", nullable = true, length = 200)
    private String environmentInformation;

    @Column(name = "COMMENTS", nullable = true, length = 200)
    private String comments;

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
}