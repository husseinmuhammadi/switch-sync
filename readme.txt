Switch Project Installation

PREREQUISITES
 1- Install JDK 1.8 x64 
	Download JDK 1.8 from http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jdk-8u102-windows-x64.exe
	
 2- Install git
	Download git from https://git-scm.com/downloads and Install it.

 3- Install Apache Maven 3.3.9 
	Download Apache Maven 3.3.9 from http://apache.mirrors.ionfish.org/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip
	Extract Apache Maven 3.3.9 to [M2_HOME] folder (C:\Apache Software Foundation\apache-maven-3.3.9)
	Set environment variables:
	M2_HOME=C:\Apache Software Foundation\apache-maven-3.3.9
	Path=%Path%;%M2_HOME%\bin

APPLICATION SERVER
 1- Install Wildfly 10.1.0.Final
	Download Wildfly 10.1.0.Final from http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip
	Extract wildfly-10.1.0.Final.zip to [WILDFLY_HOME] for example (C:\wildfly-10.1.0.Final)

DATABASE SETUP
 1- Install Oracle Database and Create a Database
	windows: http://www.oracle.com/webfolder/technetwork/tutorials/obe/db/11g/r1/prod/install/dbinst/windbinst2.htm
	linux: https://oracle-base.com/articles/11g/oracle-db-11gr2-installation-on-oracle-linux-6
 
 2- Create a user in database (Oracle 11g) and grant permissions
	CREATE USER [SWITCH] IDENTIFIED BY [PASSWORD];
	ALTER USER [SWITCH] IDENTIFIED BY [PASSWORD] ACCOUNT UNLOCK;
	GRANT CONNECT, RESOURCE, CREATE SESSION TO [SWITCH];	

 3- Create JDBC Connection
	For create JDBC connection it is need to install oracle jdbc-driver on wildfly / jboss (for more information see ADAM BIEN'S WEBLOG)
 
INSTALLING ORACLE JDBC-DRIVER ON WILDFLY / JBOSS
 1- Download the driver: ojdbc[VERSION].jar 
	Download ojdbc14.jar from http://www.oracle.com/technetwork/apps-tech/jdbc-10201-088211.html
 2- Create subfolders [WILDFLY_HOME]/modules/system/layers/base/com/oracle/ojdbc14/main/
 3- Copy the downloaded ojdbc[VERSION].jar into the freshly created folder
 4- Create a file module.xml, in the same folder as above, with the contents:
		<module xmlns="urn:jboss:module:1.1" name="com.oracle.ojdbc14">
		  <resources>
			<resource-root path="ojdbc[VERSION].jar"/>
		  </resources>
		  <dependencies>
			<module name="javax.api"/>
			<module name="javax.transaction.api"/>
		  </dependencies>
		</module>
 5- In the configuration file standalone.xml add the entry:
		<driver name="oracle" module="com.oracle.ojdbc14">
		 <driver-class>oracle.jdbc.driver.OracleDriver</driver-class>
		</driver>	
	within the <drivers> tag.
 6- Add a datasource definition within the <datasources> tag (next to ExampleDS):
		<datasource jndi-name="java:/[jdbc/SwitchDS]" pool-name="SwitchDS" enabled="true">
		 <connection-url>jdbc:oracle:thin:@[HOST_NAME]:1521:[SID]</connection-url>
		  <driver>oracle[has to match the driver name]</driver>
		  <pool>
		   <min-pool-size>1</min-pool-size>
		   <max-pool-size>5</max-pool-size>
		   <prefill>true</prefill>
		  </pool>
		  <security>
		   <user-name>[USER]</user-name>
		   <password>[PWD]</password>
		  </security>
		</datasource>

CHECKOUT PROJECT		
 1- For checkout project: 
	Open git bash (or windows command prompt)
	Change directory where you want to get project source (D:\Java-Projects)
	Checkout project to [switch] folder:
		git clone https://bitbucket.org/eidp/switch.git [switch]
	Alternatively you can pass username while checkout project:
		git clone https://[user_name]@bitbucket.org/eidp/switch.git [switch]

BUILD & DEPLOY		
 1- Build project
	Change directory to switch/parent
	Build project using command below:
		mvn clean install
	After sucessful building the project we can get switch.ear in folder [switch]/ear/target/switch-ear.ear
	Rigth now building the project deploy it on wildfly application server, 
	so it is required to start wildfly application server before you start building the project

 2- Deploy project
	Change directory to [WILDFLY_HOME]/bin
	Start wildfly
		standalone.bat --debug
	Deployment & Undeployment 
		Deployment & Undeployment is possible through wildfly admin console http://localhost:9990
		
	Wildfly comes with a Command Line Interface (CLI) with access to administrative tasks.
	The CLI is also usable in "non-interactive" mode from scripts. 
	The following command deploys an application:

	$WILDFLY_HOME/bin/jboss-cli.sh --connect --command="deployment-info"
	$WILDFLY_HOME/bin/jboss-cli.sh --connect --command="deploy --force [PATH_TO_WAR]"
	$WILDFLY_HOME/bin/jboss-cli.sh --connect --command="undeploy [NAME]"		
	
 3- Start project
	Open internet explorer http://localhost:8080/switch/index.xhtml

Additional Tips:
	Configuring Git Http Proxy
		git config --global http.proxy http://[proxy_user]:[proxy_pwd]@[proxy.server.com:8080]
		git config --global http.proxy http://127.0.0.1:8080

	Removing Git Http Proxy
		git config --global --unset http.proxy
		
	Deploy to WildFly using jboss-cli (Tech Tip #11)
	
	WildFly provides multiple ways to deploy to your applications.
	This tip will cover the most likely way to deploy deploy applications to WildFly, i.e. jboss-cli.
	jboss-cli is Command Line Interface management tool for a standalone server or a managed domain. It is available in the “bin” directory of unzipped WildFly distribution and allows a user to connect to a standalone server or domain controller and execute management operations.
	“jboss-cli” can be used to deploy applications using the interactive console or in a non-interactive manner.
	Lets look at interactive first:
	Use jboss-cli to connect with the existing standalone instance by giving the following command:
		jboss-cli.sh -c
	The -c switch connects using the default host (‘localhost’) and management port (‘9990’). These values are specified in ‘bin/jboss-cli.xml’ and can be updated.
	This opens up the “jboss-cli” interactive console and shows the following prompt:
		[standalone@localhost:9990 /]
	The prompt indicates that ‘jboss-cli’ is connected to a standalone instance’s management port.
	Deploy the application by giving the following command in console:
		deploy target/javaee7-1.0-SNAPSHOT.war
	The directory name of the war file in the command may be different depending upon how ‘jboss-cli’ was invoked. Verify the server log to ensure that the application was redeployed. Look for specific timestamp in the log entries.
	--force switch can be included in the command to replace the existing application.
	Verify the deployment status by typing the following command deployment-info in the console:
		deployment-info
	and see the output as:
		NAME                     RUNTIME-NAME             PERSISTENT ENABLED STATUS
		javaee7-1.0-SNAPSHOT.war javaee7-1.0-SNAPSHOT.war true       true    OK
	Verify the server log to ensure that the application was deployed. Look for specific timestamp in the log entries.
	Undeploy the application by giving the following command:
		undeploy javaee7-1.0-SNAPSHOT.war
	Type “exit” or “quit” to exit the interactive console.
	Now lets look at how these commands can be issued non-interactively very easily.
	Deploy the application as:
		jboss-cli.sh --connect --command="deploy target/javaee7-1.0-SNAPSHOT.war --force"
	Verify the deployment status as:
		./bin/jboss-cli.sh --connect --command=deployment-info
	Undeploy the application as:
		./bin/jboss-cli.sh --connect --command="undeploy javaee7-1.0-SNAPSHOT.war"
	
	reference: http://blog.arungupta.me/deploy-to-wildfly-using-jboss-cli-tech-tip-11/

	




Running Project
 1- Create a folder for JRN files and copy all ATM journal files 

 2- Change the path to specified journal path in atm.properties
 3- Upload all physical information of journal files to database 
    Open http://localhost:8080/switch/admin/meb/atm/journal/file/index.xhtml
	Press reload button to update all file physical information in MEB_JOURNAL_FILE
	
	Prepare ATM transactions from journal files	
	The prepration will start from last_journal_date and last_journal_line_number
	select last_journal_date, last_journal_line_number from ATM_TERMINAL_MASTER where terminal_id = '01001';
	For start from the begining of the file set last_journal_line_number to 0
	
	
	
	
	
	
	All missing journal 
	
	select to_char(rdt, 'YYYY-MM-DD', 'nls_calendar=gregorian') rdt, cal_date, atm_date, cnt
	from 
	(
	  with swipe as (
		select a.swipe_date, count(*) cnt
		from meb_atm_swipe_card a 
		where a.luno = '01001' 
		group by a.swipe_date)
	  select c.rdt, to_char(c.rdt, 'YYYY-MM-DD') cal_date, to_char(swipe_date, 'YYYY-MM-DD', 'nls_calendar=gregorian') atm_date, cnt 
	  from swipe s 
		right join (select date '2014-1-1' + level - 1 rdt from dual connect by level <= 5000) c
		  on s.swipe_date = c.rdt
	  where c.rdt >= date '2014-9-12'
	  -- order by c.rdt
	) missing 
	where atm_date is null
	order by rdt;	
	

	


View syncronization statistics

select * from 
(
  select luno, case when remain_no > 0 then 'P' when remain_no = 0 then 'C' when remain_no is null then 'W' when remain_no = -1 then 'E' else 'X' end stat
  from meb_synchronize_statistics
  where length(luno) = 5
)
pivot
(
  count(stat) for stat in ('P' as PENDING, 'C' as COMPLETE, 'W' as WAIT, 'E' as ERROR, 'X' as UNKNOWN)
)
order by luno;
