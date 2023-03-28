package cogip.core;

import static java.util.Objects.requireNonNull;

public sealed interface ImageReference permits LocalReference, LocalReferenceWithPlatform, Reference, ReferenceWithPlatform {

    interface WithPlatform {
        String platform();
    }

    String toName();
}

record LocalReference(String name, String tag) implements ImageReference {
    LocalReference {
        requireNonNull(name, "The name cannot be null");
        requireNonNull(tag, "The tag cannot be null");
    }

    @Override
    public String toName() {
        return name + ":" + tag;
    }
}

record Reference(String registry, String name, String tag) implements ImageReference {

    Reference {
        requireNonNull(registry, "The registry cannot be null");
        requireNonNull(name, "The name cannot be null");
        requireNonNull(tag, "The tag cannot be null");
    }

    @Override
    public String toName() {
        return registry + "/" + name + ":" + tag;
    }
}

record ReferenceWithPlatform(String registry, String name, String tag, String platform) implements ImageReference, ImageReference.WithPlatform {

    ReferenceWithPlatform {
        requireNonNull(registry, "The registry cannot be null");
        requireNonNull(name, "The name cannot be null");
        requireNonNull(tag, "The tag cannot be null");
        requireNonNull(platform, "The platform cannot be null");
    }

    @Override
    public String toName() {
        return registry + "/" + name + ":" + tag;
    }
}

record LocalReferenceWithPlatform(String name, String tag, String platform) implements ImageReference, ImageReference.WithPlatform {
    LocalReferenceWithPlatform {
        requireNonNull(name, "The name cannot be null");
        requireNonNull(tag, "The tag cannot be null");
        requireNonNull(platform, "The platform cannot be null");
    }

    @Override
    public String toName() {
        return name + ":" + tag;
    }
}
