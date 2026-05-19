package org.voldymar.safecrmmodule.feature.shared.exception;

import org.voldymar.safecrmmodule.feature.access.dto.AccessRequest;


/**
 * Описывает исключение некорректного объекта доступа.
 */
public class InvalidAccessObjectException
        extends RuntimeException {

    /**
     * DTO запроса на разрешение доступа.
     */
    private AccessRequest request;


    public InvalidAccessObjectException(
            String message,
            AccessRequest request
    ) {
        super(message);
        this.request = request;
    }


    public AccessRequest getRequest() {
        return request;
    }
}
