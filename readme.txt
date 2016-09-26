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
	ALTER USER SWITCH IDENTIFIED BY [PASSWORD] ACCOUNT UNLOCK;
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
	
 3- Start project
	Open internet explorer http://localhost:8080/switch/index.xhtml

Additional Tips:
	Configuring Git Http Proxy
		git config --global http.proxy http://[proxy_user]:[proxy_pwd]@[proxy.server.com:8080]
		git config --global http.proxy http://127.0.0.1:8080

	Removing Git Http Proxy
		git config --global --unset http.proxy