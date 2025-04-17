package automation;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.1)",
    comments = "Source: automation.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AutomationServiceGrpc {

  private AutomationServiceGrpc() {}

  public static final String SERVICE_NAME = "automation.AutomationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<automation.Automation.ToggleDeviceRequest,
      automation.Automation.ToggleDeviceResponse> getToggleDeviceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ToggleDevice",
      requestType = automation.Automation.ToggleDeviceRequest.class,
      responseType = automation.Automation.ToggleDeviceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<automation.Automation.ToggleDeviceRequest,
      automation.Automation.ToggleDeviceResponse> getToggleDeviceMethod() {
    io.grpc.MethodDescriptor<automation.Automation.ToggleDeviceRequest, automation.Automation.ToggleDeviceResponse> getToggleDeviceMethod;
    if ((getToggleDeviceMethod = AutomationServiceGrpc.getToggleDeviceMethod) == null) {
      synchronized (AutomationServiceGrpc.class) {
        if ((getToggleDeviceMethod = AutomationServiceGrpc.getToggleDeviceMethod) == null) {
          AutomationServiceGrpc.getToggleDeviceMethod = getToggleDeviceMethod =
              io.grpc.MethodDescriptor.<automation.Automation.ToggleDeviceRequest, automation.Automation.ToggleDeviceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ToggleDevice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.ToggleDeviceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.ToggleDeviceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AutomationServiceMethodDescriptorSupplier("ToggleDevice"))
              .build();
        }
      }
    }
    return getToggleDeviceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Automation.SetScheduleRequest,
      automation.Automation.SetScheduleResponse> getSetScheduleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetSchedule",
      requestType = automation.Automation.SetScheduleRequest.class,
      responseType = automation.Automation.SetScheduleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<automation.Automation.SetScheduleRequest,
      automation.Automation.SetScheduleResponse> getSetScheduleMethod() {
    io.grpc.MethodDescriptor<automation.Automation.SetScheduleRequest, automation.Automation.SetScheduleResponse> getSetScheduleMethod;
    if ((getSetScheduleMethod = AutomationServiceGrpc.getSetScheduleMethod) == null) {
      synchronized (AutomationServiceGrpc.class) {
        if ((getSetScheduleMethod = AutomationServiceGrpc.getSetScheduleMethod) == null) {
          AutomationServiceGrpc.getSetScheduleMethod = getSetScheduleMethod =
              io.grpc.MethodDescriptor.<automation.Automation.SetScheduleRequest, automation.Automation.SetScheduleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetSchedule"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.SetScheduleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.SetScheduleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AutomationServiceMethodDescriptorSupplier("SetSchedule"))
              .build();
        }
      }
    }
    return getSetScheduleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Automation.StreamDeviceStatusRequest,
      automation.Automation.DeviceStatusResponse> getStreamDeviceStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamDeviceStatus",
      requestType = automation.Automation.StreamDeviceStatusRequest.class,
      responseType = automation.Automation.DeviceStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Automation.StreamDeviceStatusRequest,
      automation.Automation.DeviceStatusResponse> getStreamDeviceStatusMethod() {
    io.grpc.MethodDescriptor<automation.Automation.StreamDeviceStatusRequest, automation.Automation.DeviceStatusResponse> getStreamDeviceStatusMethod;
    if ((getStreamDeviceStatusMethod = AutomationServiceGrpc.getStreamDeviceStatusMethod) == null) {
      synchronized (AutomationServiceGrpc.class) {
        if ((getStreamDeviceStatusMethod = AutomationServiceGrpc.getStreamDeviceStatusMethod) == null) {
          AutomationServiceGrpc.getStreamDeviceStatusMethod = getStreamDeviceStatusMethod =
              io.grpc.MethodDescriptor.<automation.Automation.StreamDeviceStatusRequest, automation.Automation.DeviceStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamDeviceStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.StreamDeviceStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.DeviceStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AutomationServiceMethodDescriptorSupplier("StreamDeviceStatus"))
              .build();
        }
      }
    }
    return getStreamDeviceStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Automation.DeviceCommand,
      automation.Automation.CommandSummaryResponse> getSendDeviceCommandsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendDeviceCommands",
      requestType = automation.Automation.DeviceCommand.class,
      responseType = automation.Automation.CommandSummaryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Automation.DeviceCommand,
      automation.Automation.CommandSummaryResponse> getSendDeviceCommandsMethod() {
    io.grpc.MethodDescriptor<automation.Automation.DeviceCommand, automation.Automation.CommandSummaryResponse> getSendDeviceCommandsMethod;
    if ((getSendDeviceCommandsMethod = AutomationServiceGrpc.getSendDeviceCommandsMethod) == null) {
      synchronized (AutomationServiceGrpc.class) {
        if ((getSendDeviceCommandsMethod = AutomationServiceGrpc.getSendDeviceCommandsMethod) == null) {
          AutomationServiceGrpc.getSendDeviceCommandsMethod = getSendDeviceCommandsMethod =
              io.grpc.MethodDescriptor.<automation.Automation.DeviceCommand, automation.Automation.CommandSummaryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendDeviceCommands"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.DeviceCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.CommandSummaryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AutomationServiceMethodDescriptorSupplier("SendDeviceCommands"))
              .build();
        }
      }
    }
    return getSendDeviceCommandsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Automation.DeviceMessage,
      automation.Automation.DeviceMessage> getCommunicateWithDeviceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CommunicateWithDevice",
      requestType = automation.Automation.DeviceMessage.class,
      responseType = automation.Automation.DeviceMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Automation.DeviceMessage,
      automation.Automation.DeviceMessage> getCommunicateWithDeviceMethod() {
    io.grpc.MethodDescriptor<automation.Automation.DeviceMessage, automation.Automation.DeviceMessage> getCommunicateWithDeviceMethod;
    if ((getCommunicateWithDeviceMethod = AutomationServiceGrpc.getCommunicateWithDeviceMethod) == null) {
      synchronized (AutomationServiceGrpc.class) {
        if ((getCommunicateWithDeviceMethod = AutomationServiceGrpc.getCommunicateWithDeviceMethod) == null) {
          AutomationServiceGrpc.getCommunicateWithDeviceMethod = getCommunicateWithDeviceMethod =
              io.grpc.MethodDescriptor.<automation.Automation.DeviceMessage, automation.Automation.DeviceMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CommunicateWithDevice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.DeviceMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Automation.DeviceMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AutomationServiceMethodDescriptorSupplier("CommunicateWithDevice"))
              .build();
        }
      }
    }
    return getCommunicateWithDeviceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AutomationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AutomationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AutomationServiceStub>() {
        @java.lang.Override
        public AutomationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AutomationServiceStub(channel, callOptions);
        }
      };
    return AutomationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AutomationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AutomationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AutomationServiceBlockingStub>() {
        @java.lang.Override
        public AutomationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AutomationServiceBlockingStub(channel, callOptions);
        }
      };
    return AutomationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AutomationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AutomationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AutomationServiceFutureStub>() {
        @java.lang.Override
        public AutomationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AutomationServiceFutureStub(channel, callOptions);
        }
      };
    return AutomationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    default void toggleDevice(automation.Automation.ToggleDeviceRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.ToggleDeviceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getToggleDeviceMethod(), responseObserver);
    }

    /**
     */
    default void setSchedule(automation.Automation.SetScheduleRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.SetScheduleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetScheduleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC:Server sends continuous updates on device status
     * </pre>
     */
    default void streamDeviceStatus(automation.Automation.StreamDeviceStatusRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.DeviceStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamDeviceStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC:The client sends multiple activation/deactivation requests and receives a single response
     * </pre>
     */
    default io.grpc.stub.StreamObserver<automation.Automation.DeviceCommand> sendDeviceCommands(
        io.grpc.stub.StreamObserver<automation.Automation.CommandSummaryResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSendDeviceCommandsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC:Continuous communication between client and server for automation control
     * </pre>
     */
    default io.grpc.stub.StreamObserver<automation.Automation.DeviceMessage> communicateWithDevice(
        io.grpc.stub.StreamObserver<automation.Automation.DeviceMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCommunicateWithDeviceMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AutomationService.
   */
  public static abstract class AutomationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AutomationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AutomationService.
   */
  public static final class AutomationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AutomationServiceStub> {
    private AutomationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AutomationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AutomationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public void toggleDevice(automation.Automation.ToggleDeviceRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.ToggleDeviceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getToggleDeviceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setSchedule(automation.Automation.SetScheduleRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.SetScheduleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetScheduleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC:Server sends continuous updates on device status
     * </pre>
     */
    public void streamDeviceStatus(automation.Automation.StreamDeviceStatusRequest request,
        io.grpc.stub.StreamObserver<automation.Automation.DeviceStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamDeviceStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC:The client sends multiple activation/deactivation requests and receives a single response
     * </pre>
     */
    public io.grpc.stub.StreamObserver<automation.Automation.DeviceCommand> sendDeviceCommands(
        io.grpc.stub.StreamObserver<automation.Automation.CommandSummaryResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSendDeviceCommandsMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC:Continuous communication between client and server for automation control
     * </pre>
     */
    public io.grpc.stub.StreamObserver<automation.Automation.DeviceMessage> communicateWithDevice(
        io.grpc.stub.StreamObserver<automation.Automation.DeviceMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getCommunicateWithDeviceMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AutomationService.
   */
  public static final class AutomationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AutomationServiceBlockingStub> {
    private AutomationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AutomationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AutomationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public automation.Automation.ToggleDeviceResponse toggleDevice(automation.Automation.ToggleDeviceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getToggleDeviceMethod(), getCallOptions(), request);
    }

    /**
     */
    public automation.Automation.SetScheduleResponse setSchedule(automation.Automation.SetScheduleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetScheduleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server Streaming RPC:Server sends continuous updates on device status
     * </pre>
     */
    public java.util.Iterator<automation.Automation.DeviceStatusResponse> streamDeviceStatus(
        automation.Automation.StreamDeviceStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamDeviceStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AutomationService.
   */
  public static final class AutomationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AutomationServiceFutureStub> {
    private AutomationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AutomationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AutomationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<automation.Automation.ToggleDeviceResponse> toggleDevice(
        automation.Automation.ToggleDeviceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getToggleDeviceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<automation.Automation.SetScheduleResponse> setSchedule(
        automation.Automation.SetScheduleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetScheduleMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TOGGLE_DEVICE = 0;
  private static final int METHODID_SET_SCHEDULE = 1;
  private static final int METHODID_STREAM_DEVICE_STATUS = 2;
  private static final int METHODID_SEND_DEVICE_COMMANDS = 3;
  private static final int METHODID_COMMUNICATE_WITH_DEVICE = 4;

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
        case METHODID_TOGGLE_DEVICE:
          serviceImpl.toggleDevice((automation.Automation.ToggleDeviceRequest) request,
              (io.grpc.stub.StreamObserver<automation.Automation.ToggleDeviceResponse>) responseObserver);
          break;
        case METHODID_SET_SCHEDULE:
          serviceImpl.setSchedule((automation.Automation.SetScheduleRequest) request,
              (io.grpc.stub.StreamObserver<automation.Automation.SetScheduleResponse>) responseObserver);
          break;
        case METHODID_STREAM_DEVICE_STATUS:
          serviceImpl.streamDeviceStatus((automation.Automation.StreamDeviceStatusRequest) request,
              (io.grpc.stub.StreamObserver<automation.Automation.DeviceStatusResponse>) responseObserver);
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
        case METHODID_SEND_DEVICE_COMMANDS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendDeviceCommands(
              (io.grpc.stub.StreamObserver<automation.Automation.CommandSummaryResponse>) responseObserver);
        case METHODID_COMMUNICATE_WITH_DEVICE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.communicateWithDevice(
              (io.grpc.stub.StreamObserver<automation.Automation.DeviceMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getToggleDeviceMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              automation.Automation.ToggleDeviceRequest,
              automation.Automation.ToggleDeviceResponse>(
                service, METHODID_TOGGLE_DEVICE)))
        .addMethod(
          getSetScheduleMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              automation.Automation.SetScheduleRequest,
              automation.Automation.SetScheduleResponse>(
                service, METHODID_SET_SCHEDULE)))
        .addMethod(
          getStreamDeviceStatusMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              automation.Automation.StreamDeviceStatusRequest,
              automation.Automation.DeviceStatusResponse>(
                service, METHODID_STREAM_DEVICE_STATUS)))
        .addMethod(
          getSendDeviceCommandsMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              automation.Automation.DeviceCommand,
              automation.Automation.CommandSummaryResponse>(
                service, METHODID_SEND_DEVICE_COMMANDS)))
        .addMethod(
          getCommunicateWithDeviceMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              automation.Automation.DeviceMessage,
              automation.Automation.DeviceMessage>(
                service, METHODID_COMMUNICATE_WITH_DEVICE)))
        .build();
  }

  private static abstract class AutomationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AutomationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return automation.Automation.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AutomationService");
    }
  }

  private static final class AutomationServiceFileDescriptorSupplier
      extends AutomationServiceBaseDescriptorSupplier {
    AutomationServiceFileDescriptorSupplier() {}
  }

  private static final class AutomationServiceMethodDescriptorSupplier
      extends AutomationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AutomationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (AutomationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AutomationServiceFileDescriptorSupplier())
              .addMethod(getToggleDeviceMethod())
              .addMethod(getSetScheduleMethod())
              .addMethod(getStreamDeviceStatusMethod())
              .addMethod(getSendDeviceCommandsMethod())
              .addMethod(getCommunicateWithDeviceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
