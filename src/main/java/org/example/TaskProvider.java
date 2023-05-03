package org.example;

import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskProvider implements IResourceProvider {

    @Operation(
            name = "$test",
            type = Task.class,
            idempotent = true
    )
    public Task test(@OperationParam(name = "startDate") DateType startDate) {

        if (null != startDate.getHour()) {
            throw new IllegalArgumentException("start date must not contain time");
        }

        Task task = new Task();
        task.setAuthoredOn(startDate.getValue());
        return task;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Task.class;
    }
}
