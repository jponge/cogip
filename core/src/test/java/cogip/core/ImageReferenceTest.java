package cogip.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ImageReferenceTest {

    @Test
    void localReference_toName() {
        LocalReference localReference = new LocalReference("name", "tag");
        assertThat(localReference.toName()).isEqualTo("name:tag");
    }

    @Test
    void reference_toName() {
        Reference reference = new Reference("registry", "name", "tag");
        assertThat(reference.toName()).isEqualTo("registry/name:tag");
    }

    @Test
    void referenceWithPlatform_toName() {
        ReferenceWithPlatform referenceWithPlatform = new ReferenceWithPlatform("registry", "name", "tag", "platform");
        assertThat(referenceWithPlatform.toName()).isEqualTo("registry/name:tag");
    }

    @Test
    void localReferenceWithPlatform_toName() {
        LocalReferenceWithPlatform localReferenceWithPlatform = new LocalReferenceWithPlatform("name", "tag", "platform");
        assertThat(localReferenceWithPlatform.toName()).isEqualTo("name:tag");
    }

    @Test
    void localReference_doesNotAllowNullValues() {
        assertThatNullPointerException().isThrownBy(() ->
                        new LocalReference(null, "tag"))
                .withMessage("The name cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new LocalReference("name", null))
                .withMessage("The tag cannot be null");
    }

    @Test
    void reference_doesNotAllowNullValues() {
        assertThatNullPointerException().isThrownBy(() ->
                        new Reference(null, "name", "tag"))
                .withMessage("The registry cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new Reference("registry", null, "tag"))
                .withMessage("The name cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new Reference("registry", "name", null))
                .withMessage("The tag cannot be null");
    }

    @Test
    void referenceWithPlatform_doesNotAllowNullValues() {
        assertThatNullPointerException().isThrownBy(() ->
                        new ReferenceWithPlatform(null, "name", "tag", "platform"))
                .withMessage("The registry cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new ReferenceWithPlatform("registry", null, "tag", "platform"))
                .withMessage("The name cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new ReferenceWithPlatform("registry", "name", null, "platform"))
                .withMessage("The tag cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new ReferenceWithPlatform("registry", "name", "tag", null))
                .withMessage("The platform cannot be null");
    }

    @Test
    void localReferenceWithPlatform_doesNotAllowNullValues() {
        assertThatNullPointerException().isThrownBy(() ->
                        new LocalReferenceWithPlatform(null, "tag", "platform"))
                .withMessage("The name cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new LocalReferenceWithPlatform("name", null, "platform"))
                .withMessage("The tag cannot be null");
        assertThatNullPointerException().isThrownBy(() ->
                        new LocalReferenceWithPlatform("name", "tag", null))
                .withMessage("The platform cannot be null");
    }
}
