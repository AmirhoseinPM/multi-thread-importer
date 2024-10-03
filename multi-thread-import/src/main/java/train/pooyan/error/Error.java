package train.pooyan.error;

import java.time.LocalDate;

public record Error (
    String FILE_NAME,
    long RECORD_NUMBER,
    String ERROR_CODE,
    String ERROR_CLASSIFICATION_NAME,
    String ERROR_DESCRIPTION,
    String ERROR_DATE
) {
}
