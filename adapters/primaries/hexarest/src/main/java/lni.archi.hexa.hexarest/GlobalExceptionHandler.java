package lni.archi.hexa.hexarest;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.job.JobListException;
import lni.archi.hexa.core.model.ExceptionResponseML;
import lni.archi.hexa.hexarest.controllers.ControllerTools;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobListException.class)
    public ResponseEntity<Map<String, Object>> handleException(JobListException e) {
        ExceptionResponseML exceptionResponseML = new ExceptionResponseML(e.getExceptions(), e.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("message", exceptionResponseML.details());
        body.put("exceptions", exceptionResponseML.exceptions());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(JobException.class)
    public ResponseEntity<Map<String, Object>> handleException(JobException e) {
        List<JobException> exceptions = new ArrayList<>();
        exceptions.add(e);

        ExceptionResponseML exceptionResponseML = new ExceptionResponseML(exceptions, e.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("message", exceptionResponseML.details());
        body.put("exceptions", exceptionResponseML.exceptions());
        return ResponseEntity.badRequest().body(body);
    }
}
