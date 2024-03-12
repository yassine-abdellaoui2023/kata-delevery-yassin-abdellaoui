package fr.carrefour.delivery.exceptions;



import fr.carrefour.delivery.exceptions.abstracts.AbstractEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;

import java.util.List;

public class ErrorOccurredException extends AbstractEntityException {

    public ErrorOccurredException(String message , ErrorCodes errorCodes , List<String> errors){
        super(message,errorCodes,errors);
    }

    public ErrorOccurredException(String message , ErrorCodes errorCodes){
        super(message,errorCodes);
    }
}
