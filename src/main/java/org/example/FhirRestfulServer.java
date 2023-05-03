package org.example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebServlet("/*")
public class FhirRestfulServer extends RestfulServer {

    private final ApplicationContext applicationContext;

    FhirRestfulServer(ApplicationContext context) {
        this.applicationContext = context;
    }

    @Override
    protected void initialize() throws ServletException {
        super.initialize();
        setFhirContext(FhirContext.forR4());
        setResourceProviders(List.of(
                applicationContext.getBean(TaskProvider.class))
        );
    }
}