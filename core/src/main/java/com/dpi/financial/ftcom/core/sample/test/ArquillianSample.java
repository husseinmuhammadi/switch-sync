package com.dpi.financial.ftcom.core.sample.test;

// TODO: http://arquillian.org/

import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
*/
public class ArquillianSample {
/*
    @Deployment
    public static EnterpriseArchive createArchive() {
        JavaArchive classArchive = ShrinkWrap.create(JavaArchive.class);
        classArchive.as(ExplodedImporter.class).importDirectory("target/classes/");

        GenericArchive testArchive = ShrinkWrap.create(GenericArchive.class);
        testArchive.as(ExplodedImporter.class).importDirectory("target/test-classes/");

        WebArchive testWebArchive = ShrinkWrap.create(WebArchive.class, "test.war");
        testWebArchive.merge(testArchive, "WEB-INF/classes", Filters.includeAll());
        //testWebArchive.merge(classArchive, "WEB-INF/lib", Filters.includeAll());
        testWebArchive.addAsLibraries(classArchive);
//        testWebArchive = testWebArchive.add(EmptyAsset.INSTANCE, "beans.xml");

        EnterpriseArchive enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, "test.ear");

        enterpriseArchive.addAsModule(testWebArchive);
        //enterpriseArchive.addAsModule(classArchive);


        List<String> modules = new ArrayList<>();
//        modules.add("");
        modules.add("ir.team.financial:switch-model");
        modules.add("ir.team.financial:switch-service-api");

        File[] files = Maven.resolver().loadPomFromFile("pom.xml").resolve(modules).withTransitivity().asFile();

        for (File file : files) {

            if (file.getName().equals("ir.team.financial:switch-service")) {
                enterpriseArchive.addAsModule(file);
            } else {
                enterpriseArchive.addAsLibrary(file);
            }
        }

        System.out.println(testArchive.toString(true));

        return enterpriseArchive;
    }

*/
}
