package security;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: security.proto")
public final class SecurityServiceGrpc {

  private SecurityServiceGrpc() {}

  public static final String SERVICE_NAME = "security.SecurityService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<security.Security.ToggleAlarmRequest,
      security.Security.ToggleAlarmResponse> getToggleAlarmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ToggleAlarm",
      requestType = security.Security.ToggleAlarmRequest.class,
      responseType = security.Security.ToggleAlarmResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<security.Security.ToggleAlarmRequest,
      security.Security.ToggleAlarmResponse> getToggleAlarmMethod() {
    io.grpc.MethodDescriptor<security.Security.ToggleAlarmRequest, security.Security.ToggleAlarmResponse> getToggleAlarmMethod;
    if ((getToggleAlarmMethod = SecurityServiceGrpc.getToggleAlarmMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getToggleAlarmMethod = SecurityServiceGrpc.getToggleAlarmMethod) == null) {
          SecurityServiceGrpc.getToggleAlarmMethod = getToggleAlarmMethod = 
              io.grpc.MethodDescriptor.<security.Security.ToggleAlarmRequest, security.Security.ToggleAlarmResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "security.SecurityService", "ToggleAlarm"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.ToggleAlarmRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.ToggleAlarmResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SecurityServiceMethodDescriptorSupplier("ToggleAlarm"))
                  .build();
          }
        }
     }
     return getToggleAlarmMethod;
  }

  private static volatile io.grpc.MethodDescriptor<security.Security.AlarmStatusRequest,
      security.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorAlarmStatus",
      requestType = security.Security.AlarmStatusRequest.class,
      responseType = security.Security.AlarmStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<security.Security.AlarmStatusRequest,
      security.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod() {
    io.grpc.MethodDescriptor<security.Security.AlarmStatusRequest, security.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod;
    if ((getMonitorAlarmStatusMethod = SecurityServiceGrpc.getMonitorAlarmStatusMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getMonitorAlarmStatusMethod = SecurityServiceGrpc.getMonitorAlarmStatusMethod) == null) {
          SecurityServiceGrpc.getMonitorAlarmStatusMethod = getMonitorAlarmStatusMethod = 
              io.grpc.MethodDescriptor.<security.Security.AlarmStatusRequest, security.Security.AlarmStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "security.SecurityService", "MonitorAlarmStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.AlarmStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.AlarmStatusResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SecurityServiceMethodDescriptorSupplier("MonitorAlarmStatus"))
                  .build();
          }
        }
     }
     return getMonitorAlarmStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<security.Security.SecurityEvent,
      security.Security.SecurityAlert> getLiveSecurityFeedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LiveSecurityFeed",
      requestType = security.Security.SecurityEvent.class,
      responseType = security.Security.SecurityAlert.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<security.Security.SecurityEvent,
      security.Security.SecurityAlert> getLiveSecurityFeedMethod() {
    io.grpc.MethodDescriptor<security.Security.SecurityEvent, security.Security.SecurityAlert> getLiveSecurityFeedMethod;
    if ((getLiveSecurityFeedMethod = SecurityServiceGrpc.getLiveSecurityFeedMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getLiveSecurityFeedMethod = SecurityServiceGrpc.getLiveSecurityFeedMethod) == null) {
          SecurityServiceGrpc.getLiveSecurityFeedMethod = getLiveSecurityFeedMethod = 
              io.grpc.MethodDescriptor.<security.Security.SecurityEvent, security.Security.SecurityAlert>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "security.SecurityService", "LiveSecurityFeed"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.SecurityEvent.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  security.Security.SecurityAlert.getDefaultInstance()))
                  .setSchemaDescriptor(new SecurityServiceMethodDescriptorSupplier("LiveSecurityFeed"))
                  .build();
          }
        }
     }
     return getLiveSecurityFeedMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SecurityServiceStub newStub(io.grpc.Channel channel) {
    return new SecurityServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SecurityServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SecurityServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SecurityServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SecurityServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SecurityServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void toggleAlarm(security.Security.ToggleAlarmRequest request,
        io.grpc.stub.StreamObserver<security.Security.ToggleAlarmResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getToggleAlarmMethod(), responseObserver);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    public void monitorAlarmStatus(security.Security.AlarmStatusRequest request,
        io.grpc.stub.StreamObserver<security.Security.AlarmStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMonitorAlarmStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional transmission: Continuous communication between client and server for security monitoring
     * </pre>
     */
    public io.grpc.stub.StreamObserver<security.Security.SecurityEvent> liveSecurityFeed(
        io.grpc.stub.StreamObserver<security.Security.SecurityAlert> responseObserver) {
      return asyncUnimplementedStreamingCall(getLiveSecurityFeedMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getToggleAlarmMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                security.Security.ToggleAlarmRequest,
                security.Security.ToggleAlarmResponse>(
                  this, METHODID_TOGGLE_ALARM)))
          .addMethod(
            getMonitorAlarmStatusMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                security.Security.AlarmStatusRequest,
                security.Security.AlarmStatusResponse>(
                  this, METHODID_MONITOR_ALARM_STATUS)))
          .addMethod(
            getLiveSecurityFeedMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                security.Security.SecurityEvent,
                security.Security.SecurityAlert>(
                  this, METHODID_LIVE_SECURITY_FEED)))
          .build();
    }
  }

  /**
   */
  public static final class SecurityServiceStub extends io.grpc.stub.AbstractStub<SecurityServiceStub> {
    private SecurityServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecurityServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecurityServiceStub(channel, callOptions);
    }

    /**
     */
    public void toggleAlarm(security.Security.ToggleAlarmRequest request,
        io.grpc.stub.StreamObserver<security.Security.ToggleAlarmResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getToggleAlarmMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    public void monitorAlarmStatus(security.Security.AlarmStatusRequest request,
        io.grpc.stub.StreamObserver<security.Security.AlarmStatusResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getMonitorAlarmStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Bidirectional transmission: Continuous communication between client and server for security monitoring
     * </pre>
     */
    public io.grpc.stub.StreamObserver<security.Security.SecurityEvent> liveSecurityFeed(
        io.grpc.stub.StreamObserver<security.Security.SecurityAlert> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getLiveSecurityFeedMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SecurityServiceBlockingStub extends io.grpc.stub.AbstractStub<SecurityServiceBlockingStub> {
    private SecurityServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecurityServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecurityServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public security.Security.ToggleAlarmResponse toggleAlarm(security.Security.ToggleAlarmRequest request) {
      return blockingUnaryCall(
          getChannel(), getToggleAlarmMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    public java.util.Iterator<security.Security.AlarmStatusResponse> monitorAlarmStatus(
        security.Security.AlarmStatusRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getMonitorAlarmStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SecurityServiceFutureStub extends io.grpc.stub.AbstractStub<SecurityServiceFutureStub> {
    private SecurityServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SecurityServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SecurityServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<security.Security.ToggleAlarmResponse> toggleAlarm(
        security.Security.ToggleAlarmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getToggleAlarmMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TOGGLE_ALARM = 0;
  private static final int METHODID_MONITOR_ALARM_STATUS = 1;
  private static final int METHODID_LIVE_SECURITY_FEED = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SecurityServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SecurityServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TOGGLE_ALARM:
          serviceImpl.toggleAlarm((security.Security.ToggleAlarmRequest) request,
              (io.grpc.stub.StreamObserver<security.Security.ToggleAlarmResponse>) responseObserver);
          break;
        case METHODID_MONITOR_ALARM_STATUS:
          serviceImpl.monitorAlarmStatus((security.Security.AlarmStatusRequest) request,
              (io.grpc.stub.StreamObserver<security.Security.AlarmStatusResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIVE_SECURITY_FEED:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.liveSecurityFeed(
              (io.grpc.stub.StreamObserver<security.Security.SecurityAlert>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SecurityServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SecurityServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return security.Security.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SecurityService");
    }
  }

  private static final class SecurityServiceFileDescriptorSupplier
      extends SecurityServiceBaseDescriptorSupplier {
    SecurityServiceFileDescriptorSupplier() {}
  }

  private static final class SecurityServiceMethodDescriptorSupplier
      extends SecurityServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SecurityServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SecurityServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SecurityServiceFileDescriptorSupplier())
              .addMethod(getToggleAlarmMethod())
              .addMethod(getMonitorAlarmStatusMethod())
              .addMethod(getLiveSecurityFeedMethod())
              .build();
        }
      }
    }
    return result;
  }
}
