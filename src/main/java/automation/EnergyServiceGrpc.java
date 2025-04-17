package automation;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.1)",
    comments = "Source: energy.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EnergyServiceGrpc {

  private EnergyServiceGrpc() {}

  public static final String SERVICE_NAME = "automation.EnergyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<automation.Energy.OptimizeEnergyRequest,
      automation.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OptimizeEnergy",
      requestType = automation.Energy.OptimizeEnergyRequest.class,
      responseType = automation.Energy.OptimizeEnergyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<automation.Energy.OptimizeEnergyRequest,
      automation.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod() {
    io.grpc.MethodDescriptor<automation.Energy.OptimizeEnergyRequest, automation.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod;
    if ((getOptimizeEnergyMethod = EnergyServiceGrpc.getOptimizeEnergyMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getOptimizeEnergyMethod = EnergyServiceGrpc.getOptimizeEnergyMethod) == null) {
          EnergyServiceGrpc.getOptimizeEnergyMethod = getOptimizeEnergyMethod =
              io.grpc.MethodDescriptor.<automation.Energy.OptimizeEnergyRequest, automation.Energy.OptimizeEnergyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OptimizeEnergy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.OptimizeEnergyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.OptimizeEnergyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("OptimizeEnergy"))
              .build();
        }
      }
    }
    return getOptimizeEnergyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Energy.StreamEnergyUsageRequest,
      automation.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamEnergyUsage",
      requestType = automation.Energy.StreamEnergyUsageRequest.class,
      responseType = automation.Energy.EnergyUsageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Energy.StreamEnergyUsageRequest,
      automation.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod() {
    io.grpc.MethodDescriptor<automation.Energy.StreamEnergyUsageRequest, automation.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod;
    if ((getStreamEnergyUsageMethod = EnergyServiceGrpc.getStreamEnergyUsageMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getStreamEnergyUsageMethod = EnergyServiceGrpc.getStreamEnergyUsageMethod) == null) {
          EnergyServiceGrpc.getStreamEnergyUsageMethod = getStreamEnergyUsageMethod =
              io.grpc.MethodDescriptor.<automation.Energy.StreamEnergyUsageRequest, automation.Energy.EnergyUsageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamEnergyUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.StreamEnergyUsageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.EnergyUsageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("StreamEnergyUsage"))
              .build();
        }
      }
    }
    return getStreamEnergyUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Energy.EnergyData,
      automation.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendEnergyData",
      requestType = automation.Energy.EnergyData.class,
      responseType = automation.Energy.EnergyDataSummaryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Energy.EnergyData,
      automation.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod() {
    io.grpc.MethodDescriptor<automation.Energy.EnergyData, automation.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod;
    if ((getSendEnergyDataMethod = EnergyServiceGrpc.getSendEnergyDataMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getSendEnergyDataMethod = EnergyServiceGrpc.getSendEnergyDataMethod) == null) {
          EnergyServiceGrpc.getSendEnergyDataMethod = getSendEnergyDataMethod =
              io.grpc.MethodDescriptor.<automation.Energy.EnergyData, automation.Energy.EnergyDataSummaryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendEnergyData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.EnergyData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.EnergyDataSummaryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("SendEnergyData"))
              .build();
        }
      }
    }
    return getSendEnergyDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<automation.Energy.EnergyUpdateRequest,
      automation.Energy.EnergyUpdateResponse> getMonitorEnergyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorEnergy",
      requestType = automation.Energy.EnergyUpdateRequest.class,
      responseType = automation.Energy.EnergyUpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<automation.Energy.EnergyUpdateRequest,
      automation.Energy.EnergyUpdateResponse> getMonitorEnergyMethod() {
    io.grpc.MethodDescriptor<automation.Energy.EnergyUpdateRequest, automation.Energy.EnergyUpdateResponse> getMonitorEnergyMethod;
    if ((getMonitorEnergyMethod = EnergyServiceGrpc.getMonitorEnergyMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getMonitorEnergyMethod = EnergyServiceGrpc.getMonitorEnergyMethod) == null) {
          EnergyServiceGrpc.getMonitorEnergyMethod = getMonitorEnergyMethod =
              io.grpc.MethodDescriptor.<automation.Energy.EnergyUpdateRequest, automation.Energy.EnergyUpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MonitorEnergy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.EnergyUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  automation.Energy.EnergyUpdateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("MonitorEnergy"))
              .build();
        }
      }
    }
    return getMonitorEnergyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EnergyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnergyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnergyServiceStub>() {
        @java.lang.Override
        public EnergyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnergyServiceStub(channel, callOptions);
        }
      };
    return EnergyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnergyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnergyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnergyServiceBlockingStub>() {
        @java.lang.Override
        public EnergyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnergyServiceBlockingStub(channel, callOptions);
        }
      };
    return EnergyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EnergyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnergyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnergyServiceFutureStub>() {
        @java.lang.Override
        public EnergyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnergyServiceFutureStub(channel, callOptions);
        }
      };
    return EnergyServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    default void optimizeEnergy(automation.Energy.OptimizeEnergyRequest request,
        io.grpc.stub.StreamObserver<automation.Energy.OptimizeEnergyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOptimizeEnergyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC:The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    default void streamEnergyUsage(automation.Energy.StreamEnergyUsageRequest request,
        io.grpc.stub.StreamObserver<automation.Energy.EnergyUsageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamEnergyUsageMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC:The customer sends multiple consumption data and receives a summary.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<automation.Energy.EnergyData> sendEnergyData(
        io.grpc.stub.StreamObserver<automation.Energy.EnergyDataSummaryResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSendEnergyDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC:Continuous communication between client and server for energy optimisation.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<automation.Energy.EnergyUpdateRequest> monitorEnergy(
        io.grpc.stub.StreamObserver<automation.Energy.EnergyUpdateResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getMonitorEnergyMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EnergyService.
   */
  public static abstract class EnergyServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EnergyServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EnergyService.
   */
  public static final class EnergyServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EnergyServiceStub> {
    private EnergyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnergyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public void optimizeEnergy(automation.Energy.OptimizeEnergyRequest request,
        io.grpc.stub.StreamObserver<automation.Energy.OptimizeEnergyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOptimizeEnergyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC:The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    public void streamEnergyUsage(automation.Energy.StreamEnergyUsageRequest request,
        io.grpc.stub.StreamObserver<automation.Energy.EnergyUsageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamEnergyUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC:The customer sends multiple consumption data and receives a summary.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<automation.Energy.EnergyData> sendEnergyData(
        io.grpc.stub.StreamObserver<automation.Energy.EnergyDataSummaryResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSendEnergyDataMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC:Continuous communication between client and server for energy optimisation.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<automation.Energy.EnergyUpdateRequest> monitorEnergy(
        io.grpc.stub.StreamObserver<automation.Energy.EnergyUpdateResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getMonitorEnergyMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EnergyService.
   */
  public static final class EnergyServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EnergyServiceBlockingStub> {
    private EnergyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnergyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public automation.Energy.OptimizeEnergyResponse optimizeEnergy(automation.Energy.OptimizeEnergyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOptimizeEnergyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server Streaming RPC:The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    public java.util.Iterator<automation.Energy.EnergyUsageResponse> streamEnergyUsage(
        automation.Energy.StreamEnergyUsageRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamEnergyUsageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EnergyService.
   */
  public static final class EnergyServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EnergyServiceFutureStub> {
    private EnergyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnergyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<automation.Energy.OptimizeEnergyResponse> optimizeEnergy(
        automation.Energy.OptimizeEnergyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOptimizeEnergyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPTIMIZE_ENERGY = 0;
  private static final int METHODID_STREAM_ENERGY_USAGE = 1;
  private static final int METHODID_SEND_ENERGY_DATA = 2;
  private static final int METHODID_MONITOR_ENERGY = 3;

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
        case METHODID_OPTIMIZE_ENERGY:
          serviceImpl.optimizeEnergy((automation.Energy.OptimizeEnergyRequest) request,
              (io.grpc.stub.StreamObserver<automation.Energy.OptimizeEnergyResponse>) responseObserver);
          break;
        case METHODID_STREAM_ENERGY_USAGE:
          serviceImpl.streamEnergyUsage((automation.Energy.StreamEnergyUsageRequest) request,
              (io.grpc.stub.StreamObserver<automation.Energy.EnergyUsageResponse>) responseObserver);
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
        case METHODID_SEND_ENERGY_DATA:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendEnergyData(
              (io.grpc.stub.StreamObserver<automation.Energy.EnergyDataSummaryResponse>) responseObserver);
        case METHODID_MONITOR_ENERGY:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.monitorEnergy(
              (io.grpc.stub.StreamObserver<automation.Energy.EnergyUpdateResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getOptimizeEnergyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              automation.Energy.OptimizeEnergyRequest,
              automation.Energy.OptimizeEnergyResponse>(
                service, METHODID_OPTIMIZE_ENERGY)))
        .addMethod(
          getStreamEnergyUsageMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              automation.Energy.StreamEnergyUsageRequest,
              automation.Energy.EnergyUsageResponse>(
                service, METHODID_STREAM_ENERGY_USAGE)))
        .addMethod(
          getSendEnergyDataMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              automation.Energy.EnergyData,
              automation.Energy.EnergyDataSummaryResponse>(
                service, METHODID_SEND_ENERGY_DATA)))
        .addMethod(
          getMonitorEnergyMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              automation.Energy.EnergyUpdateRequest,
              automation.Energy.EnergyUpdateResponse>(
                service, METHODID_MONITOR_ENERGY)))
        .build();
  }

  private static abstract class EnergyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EnergyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return automation.Energy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EnergyService");
    }
  }

  private static final class EnergyServiceFileDescriptorSupplier
      extends EnergyServiceBaseDescriptorSupplier {
    EnergyServiceFileDescriptorSupplier() {}
  }

  private static final class EnergyServiceMethodDescriptorSupplier
      extends EnergyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EnergyServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (EnergyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EnergyServiceFileDescriptorSupplier())
              .addMethod(getOptimizeEnergyMethod())
              .addMethod(getStreamEnergyUsageMethod())
              .addMethod(getSendEnergyDataMethod())
              .addMethod(getMonitorEnergyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
