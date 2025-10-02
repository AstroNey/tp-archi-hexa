package lni.archi.hexa.hexarest;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.model.ExceptionResponseML;
import lni.archi.hexa.hexarest.controllers.ControllerTools;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobException.class)
    public ResponseEntity<IJobErrorMessage> handleException(JobException e) {
        ExceptionResponseML exceptionResponseML = new ExceptionResponseML(e.getErrorMessage(), e.getMessage());
        return ResponseEntity.status(ControllerTools.FindHttpStatus(e))
                .body(exceptionResponseML.jobErrorMessage());
    }
}
