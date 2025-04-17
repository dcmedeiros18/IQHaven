package IQHaven;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.1)",
    comments = "Source: security.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SecurityServiceGrpc {

  private SecurityServiceGrpc() {}

  public static final String SERVICE_NAME = "IQHaven.SecurityService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<IQHaven.Security.ToggleAlarmRequest,
      IQHaven.Security.ToggleAlarmResponse> getToggleAlarmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ToggleAlarm",
      requestType = IQHaven.Security.ToggleAlarmRequest.class,
      responseType = IQHaven.Security.ToggleAlarmResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<IQHaven.Security.ToggleAlarmRequest,
      IQHaven.Security.ToggleAlarmResponse> getToggleAlarmMethod() {
    io.grpc.MethodDescriptor<IQHaven.Security.ToggleAlarmRequest, IQHaven.Security.ToggleAlarmResponse> getToggleAlarmMethod;
    if ((getToggleAlarmMethod = SecurityServiceGrpc.getToggleAlarmMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getToggleAlarmMethod = SecurityServiceGrpc.getToggleAlarmMethod) == null) {
          SecurityServiceGrpc.getToggleAlarmMethod = getToggleAlarmMethod =
              io.grpc.MethodDescriptor.<IQHaven.Security.ToggleAlarmRequest, IQHaven.Security.ToggleAlarmResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ToggleAlarm"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.ToggleAlarmRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.ToggleAlarmResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SecurityServiceMethodDescriptorSupplier("ToggleAlarm"))
              .build();
        }
      }
    }
    return getToggleAlarmMethod;
  }

  private static volatile io.grpc.MethodDescriptor<IQHaven.Security.AlarmStatusRequest,
      IQHaven.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorAlarmStatus",
      requestType = IQHaven.Security.AlarmStatusRequest.class,
      responseType = IQHaven.Security.AlarmStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<IQHaven.Security.AlarmStatusRequest,
      IQHaven.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod() {
    io.grpc.MethodDescriptor<IQHaven.Security.AlarmStatusRequest, IQHaven.Security.AlarmStatusResponse> getMonitorAlarmStatusMethod;
    if ((getMonitorAlarmStatusMethod = SecurityServiceGrpc.getMonitorAlarmStatusMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getMonitorAlarmStatusMethod = SecurityServiceGrpc.getMonitorAlarmStatusMethod) == null) {
          SecurityServiceGrpc.getMonitorAlarmStatusMethod = getMonitorAlarmStatusMethod =
              io.grpc.MethodDescriptor.<IQHaven.Security.AlarmStatusRequest, IQHaven.Security.AlarmStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MonitorAlarmStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.AlarmStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.AlarmStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SecurityServiceMethodDescriptorSupplier("MonitorAlarmStatus"))
              .build();
        }
      }
    }
    return getMonitorAlarmStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<IQHaven.Security.SecurityEvent,
      IQHaven.Security.SecurityAlert> getLiveSecurityFeedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LiveSecurityFeed",
      requestType = IQHaven.Security.SecurityEvent.class,
      responseType = IQHaven.Security.SecurityAlert.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<IQHaven.Security.SecurityEvent,
      IQHaven.Security.SecurityAlert> getLiveSecurityFeedMethod() {
    io.grpc.MethodDescriptor<IQHaven.Security.SecurityEvent, IQHaven.Security.SecurityAlert> getLiveSecurityFeedMethod;
    if ((getLiveSecurityFeedMethod = SecurityServiceGrpc.getLiveSecurityFeedMethod) == null) {
      synchronized (SecurityServiceGrpc.class) {
        if ((getLiveSecurityFeedMethod = SecurityServiceGrpc.getLiveSecurityFeedMethod) == null) {
          SecurityServiceGrpc.getLiveSecurityFeedMethod = getLiveSecurityFeedMethod =
              io.grpc.MethodDescriptor.<IQHaven.Security.SecurityEvent, IQHaven.Security.SecurityAlert>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LiveSecurityFeed"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.SecurityEvent.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IQHaven.Security.SecurityAlert.getDefaultInstance()))
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
    io.grpc.stub.AbstractStub.StubFactory<SecurityServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecurityServiceStub>() {
        @java.lang.Override
        public SecurityServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecurityServiceStub(channel, callOptions);
        }
      };
    return SecurityServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SecurityServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecurityServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecurityServiceBlockingStub>() {
        @java.lang.Override
        public SecurityServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecurityServiceBlockingStub(channel, callOptions);
        }
      };
    return SecurityServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SecurityServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SecurityServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SecurityServiceFutureStub>() {
        @java.lang.Override
        public SecurityServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SecurityServiceFutureStub(channel, callOptions);
        }
      };
    return SecurityServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void toggleAlarm(IQHaven.Security.ToggleAlarmRequest request,
        io.grpc.stub.StreamObserver<IQHaven.Security.ToggleAlarmResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getToggleAlarmMethod(), responseObserver);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    default void monitorAlarmStatus(IQHaven.Security.AlarmStatusRequest request,
        io.grpc.stub.StreamObserver<IQHaven.Security.AlarmStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMonitorAlarmStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional transmission: Continuous communication between client and server for security monitoring
     * </pre>
     */
    default io.grpc.stub.StreamObserver<IQHaven.Security.SecurityEvent> liveSecurityFeed(
        io.grpc.stub.StreamObserver<IQHaven.Security.SecurityAlert> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getLiveSecurityFeedMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SecurityService.
   */
  public static abstract class SecurityServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SecurityServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SecurityService.
   */
  public static final class SecurityServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SecurityServiceStub> {
    private SecurityServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecurityServiceStub(channel, callOptions);
    }

    /**
     */
    public void toggleAlarm(IQHaven.Security.ToggleAlarmRequest request,
        io.grpc.stub.StreamObserver<IQHaven.Security.ToggleAlarmResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getToggleAlarmMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    public void monitorAlarmStatus(IQHaven.Security.AlarmStatusRequest request,
        io.grpc.stub.StreamObserver<IQHaven.Security.AlarmStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getMonitorAlarmStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Bidirectional transmission: Continuous communication between client and server for security monitoring
     * </pre>
     */
    public io.grpc.stub.StreamObserver<IQHaven.Security.SecurityEvent> liveSecurityFeed(
        io.grpc.stub.StreamObserver<IQHaven.Security.SecurityAlert> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getLiveSecurityFeedMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SecurityService.
   */
  public static final class SecurityServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SecurityServiceBlockingStub> {
    private SecurityServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecurityServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public IQHaven.Security.ToggleAlarmResponse toggleAlarm(IQHaven.Security.ToggleAlarmRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getToggleAlarmMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Data flow from the server: The server sends continuous updates on the alarm status
     * </pre>
     */
    public java.util.Iterator<IQHaven.Security.AlarmStatusResponse> monitorAlarmStatus(
        IQHaven.Security.AlarmStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getMonitorAlarmStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SecurityService.
   */
  public static final class SecurityServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SecurityServiceFutureStub> {
    private SecurityServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SecurityServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SecurityServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IQHaven.Security.ToggleAlarmResponse> toggleAlarm(
        IQHaven.Security.ToggleAlarmRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
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
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TOGGLE_ALARM:
          serviceImpl.toggleAlarm((IQHaven.Security.ToggleAlarmRequest) request,
              (io.grpc.stub.StreamObserver<IQHaven.Security.ToggleAlarmResponse>) responseObserver);
          break;
        case METHODID_MONITOR_ALARM_STATUS:
          serviceImpl.monitorAlarmStatus((IQHaven.Security.AlarmStatusRequest) request,
              (io.grpc.stub.StreamObserver<IQHaven.Security.AlarmStatusResponse>) responseObserver);
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
              (io.grpc.stub.StreamObserver<IQHaven.Security.SecurityAlert>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getToggleAlarmMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              IQHaven.Security.ToggleAlarmRequest,
              IQHaven.Security.ToggleAlarmResponse>(
                service, METHODID_TOGGLE_ALARM)))
        .addMethod(
          getMonitorAlarmStatusMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              IQHaven.Security.AlarmStatusRequest,
              IQHaven.Security.AlarmStatusResponse>(
                service, METHODID_MONITOR_ALARM_STATUS)))
        .addMethod(
          getLiveSecurityFeedMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              IQHaven.Security.SecurityEvent,
              IQHaven.Security.SecurityAlert>(
                service, METHODID_LIVE_SECURITY_FEED)))
        .build();
  }

  private static abstract class SecurityServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SecurityServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return IQHaven.Security.getDescriptor();
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
