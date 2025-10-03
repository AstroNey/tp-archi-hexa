package lni.archi.hexa.core.model;

import lni.archi.hexa.core.exceptions.job.JobException;

import java.util.List;

public record ExceptionResponseML(List<JobException> exceptions, String details) {
}
