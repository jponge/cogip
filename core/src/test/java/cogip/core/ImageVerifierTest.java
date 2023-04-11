package cogip.core;

import com.github.dockerjava.api.command.RootFS;
import com.github.dockerjava.api.model.ContainerConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageVerifierTest {

    @Test
    void smoke_test() throws Exception {
        try (ContainerEngineSession session = new ContainerEngineSession()) {
            var ref = new Reference("docker.io", "library/hello-world", "linux");
            session.pullImage(ref);
            ImageVerifier imageVerifier = session.imageVerifier(ref);
            imageVerifier.verify(img -> {
                ContainerConfig conf = img.getConfig();
                RootFS rootFS = img.getRootFS();
                assertThat(img.getOs()).isEqualTo("linux");
                assertThat(img.getParent()).isEmpty();
                assertThat(conf.getCmd()).containsExactly("/hello");
                assertThat(rootFS.getType()).isEqualTo("layers");
                assertThat(rootFS.getLayers()).hasSize(1);
            });
        }
    }
}