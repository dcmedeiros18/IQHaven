package energy;

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
    comments = "Source: energy.proto")
public final class EnergyServiceGrpc {

  private EnergyServiceGrpc() {}

  public static final String SERVICE_NAME = "energy.EnergyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<energy.Energy.OptimizeEnergyRequest,
      energy.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OptimizeEnergy",
      requestType = energy.Energy.OptimizeEnergyRequest.class,
      responseType = energy.Energy.OptimizeEnergyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<energy.Energy.OptimizeEnergyRequest,
      energy.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod() {
    io.grpc.MethodDescriptor<energy.Energy.OptimizeEnergyRequest, energy.Energy.OptimizeEnergyResponse> getOptimizeEnergyMethod;
    if ((getOptimizeEnergyMethod = EnergyServiceGrpc.getOptimizeEnergyMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getOptimizeEnergyMethod = EnergyServiceGrpc.getOptimizeEnergyMethod) == null) {
          EnergyServiceGrpc.getOptimizeEnergyMethod = getOptimizeEnergyMethod = 
              io.grpc.MethodDescriptor.<energy.Energy.OptimizeEnergyRequest, energy.Energy.OptimizeEnergyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "energy.EnergyService", "OptimizeEnergy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.OptimizeEnergyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.OptimizeEnergyResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("OptimizeEnergy"))
                  .build();
          }
        }
     }
     return getOptimizeEnergyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<energy.Energy.StreamEnergyUsageRequest,
      energy.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamEnergyUsage",
      requestType = energy.Energy.StreamEnergyUsageRequest.class,
      responseType = energy.Energy.EnergyUsageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<energy.Energy.StreamEnergyUsageRequest,
      energy.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod() {
    io.grpc.MethodDescriptor<energy.Energy.StreamEnergyUsageRequest, energy.Energy.EnergyUsageResponse> getStreamEnergyUsageMethod;
    if ((getStreamEnergyUsageMethod = EnergyServiceGrpc.getStreamEnergyUsageMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getStreamEnergyUsageMethod = EnergyServiceGrpc.getStreamEnergyUsageMethod) == null) {
          EnergyServiceGrpc.getStreamEnergyUsageMethod = getStreamEnergyUsageMethod = 
              io.grpc.MethodDescriptor.<energy.Energy.StreamEnergyUsageRequest, energy.Energy.EnergyUsageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "energy.EnergyService", "StreamEnergyUsage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.StreamEnergyUsageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.EnergyUsageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("StreamEnergyUsage"))
                  .build();
          }
        }
     }
     return getStreamEnergyUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<energy.Energy.EnergyData,
      energy.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendEnergyData",
      requestType = energy.Energy.EnergyData.class,
      responseType = energy.Energy.EnergyDataSummaryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<energy.Energy.EnergyData,
      energy.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod() {
    io.grpc.MethodDescriptor<energy.Energy.EnergyData, energy.Energy.EnergyDataSummaryResponse> getSendEnergyDataMethod;
    if ((getSendEnergyDataMethod = EnergyServiceGrpc.getSendEnergyDataMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getSendEnergyDataMethod = EnergyServiceGrpc.getSendEnergyDataMethod) == null) {
          EnergyServiceGrpc.getSendEnergyDataMethod = getSendEnergyDataMethod = 
              io.grpc.MethodDescriptor.<energy.Energy.EnergyData, energy.Energy.EnergyDataSummaryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "energy.EnergyService", "SendEnergyData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.EnergyData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.EnergyDataSummaryResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EnergyServiceMethodDescriptorSupplier("SendEnergyData"))
                  .build();
          }
        }
     }
     return getSendEnergyDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<energy.Energy.EnergyUpdateRequest,
      energy.Energy.EnergyUpdateResponse> getMonitorEnergyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MonitorEnergy",
      requestType = energy.Energy.EnergyUpdateRequest.class,
      responseType = energy.Energy.EnergyUpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<energy.Energy.EnergyUpdateRequest,
      energy.Energy.EnergyUpdateResponse> getMonitorEnergyMethod() {
    io.grpc.MethodDescriptor<energy.Energy.EnergyUpdateRequest, energy.Energy.EnergyUpdateResponse> getMonitorEnergyMethod;
    if ((getMonitorEnergyMethod = EnergyServiceGrpc.getMonitorEnergyMethod) == null) {
      synchronized (EnergyServiceGrpc.class) {
        if ((getMonitorEnergyMethod = EnergyServiceGrpc.getMonitorEnergyMethod) == null) {
          EnergyServiceGrpc.getMonitorEnergyMethod = getMonitorEnergyMethod = 
              io.grpc.MethodDescriptor.<energy.Energy.EnergyUpdateRequest, energy.Energy.EnergyUpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "energy.EnergyService", "MonitorEnergy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.EnergyUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  energy.Energy.EnergyUpdateResponse.getDefaultInstance()))
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
    return new EnergyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnergyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EnergyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EnergyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EnergyServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class EnergyServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public void optimizeEnergy(energy.Energy.OptimizeEnergyRequest request,
        io.grpc.stub.StreamObserver<energy.Energy.OptimizeEnergyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getOptimizeEnergyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC: The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    public void streamEnergyUsage(energy.Energy.StreamEnergyUsageRequest request,
        io.grpc.stub.StreamObserver<energy.Energy.EnergyUsageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStreamEnergyUsageMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC: The customer sends multiple consumption data and receives a summary.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<energy.Energy.EnergyData> sendEnergyData(
        io.grpc.stub.StreamObserver<energy.Energy.EnergyDataSummaryResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getSendEnergyDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC: Continuous communication between client and server for energy optimisation.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<energy.Energy.EnergyUpdateRequest> monitorEnergy(
        io.grpc.stub.StreamObserver<energy.Energy.EnergyUpdateResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getMonitorEnergyMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOptimizeEnergyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                energy.Energy.OptimizeEnergyRequest,
                energy.Energy.OptimizeEnergyResponse>(
                  this, METHODID_OPTIMIZE_ENERGY)))
          .addMethod(
            getStreamEnergyUsageMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                energy.Energy.StreamEnergyUsageRequest,
                energy.Energy.EnergyUsageResponse>(
                  this, METHODID_STREAM_ENERGY_USAGE)))
          .addMethod(
            getSendEnergyDataMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                energy.Energy.EnergyData,
                energy.Energy.EnergyDataSummaryResponse>(
                  this, METHODID_SEND_ENERGY_DATA)))
          .addMethod(
            getMonitorEnergyMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                energy.Energy.EnergyUpdateRequest,
                energy.Energy.EnergyUpdateResponse>(
                  this, METHODID_MONITOR_ENERGY)))
          .build();
    }
  }

  /**
   */
  public static final class EnergyServiceStub extends io.grpc.stub.AbstractStub<EnergyServiceStub> {
    private EnergyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnergyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnergyServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public void optimizeEnergy(energy.Energy.OptimizeEnergyRequest request,
        io.grpc.stub.StreamObserver<energy.Energy.OptimizeEnergyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getOptimizeEnergyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server Streaming RPC: The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    public void streamEnergyUsage(energy.Energy.StreamEnergyUsageRequest request,
        io.grpc.stub.StreamObserver<energy.Energy.EnergyUsageResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getStreamEnergyUsageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client Streaming RPC: The customer sends multiple consumption data and receives a summary.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<energy.Energy.EnergyData> sendEnergyData(
        io.grpc.stub.StreamObserver<energy.Energy.EnergyDataSummaryResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getSendEnergyDataMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Bidirectional Streaming RPC: Continuous communication between client and server for energy optimisation.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<energy.Energy.EnergyUpdateRequest> monitorEnergy(
        io.grpc.stub.StreamObserver<energy.Energy.EnergyUpdateResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getMonitorEnergyMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class EnergyServiceBlockingStub extends io.grpc.stub.AbstractStub<EnergyServiceBlockingStub> {
    private EnergyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnergyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnergyServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public energy.Energy.OptimizeEnergyResponse optimizeEnergy(energy.Energy.OptimizeEnergyRequest request) {
      return blockingUnaryCall(
          getChannel(), getOptimizeEnergyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server Streaming RPC: The server sends continuous energy optimisation suggestions.
     * </pre>
     */
    public java.util.Iterator<energy.Energy.EnergyUsageResponse> streamEnergyUsage(
        energy.Energy.StreamEnergyUsageRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getStreamEnergyUsageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EnergyServiceFutureStub extends io.grpc.stub.AbstractStub<EnergyServiceFutureStub> {
    private EnergyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EnergyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnergyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EnergyServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Unary RPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<energy.Energy.OptimizeEnergyResponse> optimizeEnergy(
        energy.Energy.OptimizeEnergyRequest request) {
      return futureUnaryCall(
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
    private final EnergyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EnergyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OPTIMIZE_ENERGY:
          serviceImpl.optimizeEnergy((energy.Energy.OptimizeEnergyRequest) request,
              (io.grpc.stub.StreamObserver<energy.Energy.OptimizeEnergyResponse>) responseObserver);
          break;
        case METHODID_STREAM_ENERGY_USAGE:
          serviceImpl.streamEnergyUsage((energy.Energy.StreamEnergyUsageRequest) request,
              (io.grpc.stub.StreamObserver<energy.Energy.EnergyUsageResponse>) responseObserver);
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
              (io.grpc.stub.StreamObserver<energy.Energy.EnergyDataSummaryResponse>) responseObserver);
        case METHODID_MONITOR_ENERGY:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.monitorEnergy(
              (io.grpc.stub.StreamObserver<energy.Energy.EnergyUpdateResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EnergyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EnergyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return energy.Energy.getDescriptor();
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
