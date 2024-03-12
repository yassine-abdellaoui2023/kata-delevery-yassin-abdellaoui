package fr.carrefour.delivery.exceptions;



import fr.carrefour.delivery.exceptions.abstracts.AbstractEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;

import java.util.List;

public class InvalidEntityException extends AbstractEntityException {

    public InvalidEntityException(String message , ErrorCodes errorCodes , List<String> errors){
        super(message, errorCodes, errors);
    }

    public InvalidEntityException(String message , ErrorCodes errorCodes){
        super(message,errorCodes);
    }
}
