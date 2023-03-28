package cogip.core;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;

import java.util.HashMap;
import java.util.Map;

public final class ContainerEngineSession implements AutoCloseable {

    private final DockerClient dockerClient;
    private final Map<ImageReference, InspectImageResponse> imageInfoCache = new HashMap<>();

    public ContainerEngineSession(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public ContainerEngineSession() {
        DefaultDockerClientConfig config = new DefaultDockerClientConfig.Builder()
                .build();

        DockerHttpClient httpClient = new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        this.dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    public void pullImage(ImageReference reference) throws InterruptedException {
        PullImageCmd cmd = dockerClient.pullImageCmd(reference.toName());
        if (reference instanceof ImageReference.WithPlatform ref) {
            cmd.withPlatform(ref.platform());
        }
        cmd.exec(new PullImageResultCallback()).awaitCompletion();
    }

    public ImageVerifier imageVerifier(ImageReference reference) {
        InspectImageResponse data = imageInfoCache.computeIfAbsent(reference, this::fetchImageInfo);
        return new ImageVerifier(data);
    }

    private InspectImageResponse fetchImageInfo(ImageReference reference) {
        return dockerClient.inspectImageCmd(reference.toName()).exec();
    }

    @Override
    public void close() throws Exception {
        dockerClient.close();
    }
}
