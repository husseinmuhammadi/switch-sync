package ir.team.insurance.complementary.utility.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.Serializable;

@ApplicationScoped
public class LoggerProvider implements Serializable {

    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) throws Exception {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
