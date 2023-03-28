package cogip.core;

import com.github.dockerjava.api.command.InspectImageResponse;

import java.util.function.Consumer;


public class ImageVerifier {

    private final InspectImageResponse data;

    ImageVerifier(InspectImageResponse data) {
        this.data = data;
    }

    public void verify(Consumer<InspectImageResponse> consumer) {
        consumer.accept(data);
    }
}