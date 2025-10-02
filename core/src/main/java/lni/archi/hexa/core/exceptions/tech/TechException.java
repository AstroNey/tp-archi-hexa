package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.techException.ITechErrorMessage;
import lombok.Getter;

@Getter
public class TechException extends RuntimeException {

    private final ITechErrorMessage errorMessage;

    public TechException(String message, ITechErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }
}
