package automation;

import io.grpc.*;

// Interceptor para tratamento global de erros
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
                        if (status.getCode() == Status.Code.UNKNOWN) {
                            status = Status.INTERNAL.withDescription("Internal server error");
                        }
                        super.close(status, trailers);
                    }
                }, headers)) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    call.close(Status.INTERNAL
                                    .withDescription("Error processing request")
                                    .withCause(e),
                            new Metadata());
                }
            }
        };
    }
}