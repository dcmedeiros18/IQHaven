package automation;

import io.grpc.*;

/**
 * Interceptor for global error handling in gRPC server calls.
 * This class wraps incoming calls and ensures that unhandled exceptions
 * are caught and converted into proper gRPC error responses.
 * @author dcmed
 */
public class ErrorHandlingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {

                    @Override
                    public void close(Status status, Metadata trailers) {
                        // If the status code is UNKNOWN, convert it to INTERNAL with a generic description
                        if (status.getCode() == Status.Code.UNKNOWN) {
                            status = Status.INTERNAL.withDescription("Internal server error");
                        }
                        super.close(status, trailers);
                    }

                }, headers)) {

            @Override
            public void onHalfClose() {
                try {
                    // Attempt to continue processing the call
                    super.onHalfClose();
                } catch (Exception e) {
                    // Catch any exception and close the call with INTERNAL status
                    call.close(Status.INTERNAL
                                    .withDescription("Error processing request")
                                    .withCause(e),
                            new Metadata());
                }
            }
        };
    }
}
