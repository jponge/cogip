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

            session.verifyImage(ref, img -> {

                ContainerConfig conf = img.getConfig();
                assertThat(conf).isNotNull();

                RootFS rootFS = img.getRootFS();
                assertThat(rootFS).isNotNull();

                assertThat(img.getOs()).isEqualTo("linux");
                assertThat(img.getParent()).isEmpty();
                assertThat(conf.getCmd()).containsExactly("/hello");
                assertThat(rootFS.getType()).isEqualTo("layers");
                assertThat(rootFS.getLayers()).hasSize(1);
            });
        }
    }
}