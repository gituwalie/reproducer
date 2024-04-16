
package exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<RuntimeException> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException exception) {
        log.error(exception.toString());
        if (exception instanceof ApplicationException e) {
            log.error(exception.toString());
            return Response.status(e.getStatus()).
                    entity(
                            new ApplicationErrorResponseBody(e.getMessage(), StatusCodes.RMIAC_4000))
                    .build();

        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(
                            new ApplicationErrorResponseBody(exception.getMessage(), StatusCodes.RMIAC_4000))
                    .build();
        }
    }
}
