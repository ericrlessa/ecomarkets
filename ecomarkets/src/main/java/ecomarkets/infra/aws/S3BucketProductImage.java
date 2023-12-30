package ecomarkets.infra.aws;

import ecomarkets.domain.core.product.image.ImageRepository;
import ecomarkets.domain.core.product.image.ProductImage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class S3BucketProductImage implements ImageRepository {
    @ConfigProperty(name = "bucket.name")
    private String bucketName;

    @ConfigProperty(name = "presigned.url.duration.in.minutes")
    private Integer presignedUrlDurationInMinutes;

    @Inject
    private S3Client s3;

    @Inject
    S3Presigner presigner;

    public void save(Path file,
                     ProductImage productImage) {

        List<Tag> tagsS3 = getTags(productImage);
        s3.putObject(
        PutObjectRequest.builder()
                .bucket(productImage.bucket())
                .key(productImage.key())
                .tagging(Tagging.builder().tagSet(tagsS3).build())
                .build(),
                RequestBody.fromFile(file));
    }

    public void delete(ProductImage productImage) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(productImage.key())
                .build();

        // Delete the object
        s3.deleteObject(deleteRequest);
    }

    public byte[] find(ProductImage productImage) {
        try {
            return s3.getObject(GetObjectRequest.builder()
                       .bucket(productImage.bucket())
                       .key(productImage.key())
                       .build()).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createPresignedGetUrl(ProductImage productImage) {
//        try (S3Presigner presigner = S3Presigner.create()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(productImage.bucket())
                    .key(productImage.key())
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(presignedUrlDurationInMinutes))  // The URL will expire in 10 minutes.
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toExternalForm();
//        }
    }



    @PostConstruct
    private void createBucket(){
        try {
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3.headBucket(headBucketRequest);
        } catch (NoSuchBucketException e) {
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3.createBucket(bucketRequest);
        } catch (Exception e){
            //FIXME add log or change this. But should not prohibit the startup of the app
        }
    }

    private List<Tag> getTags(ProductImage productImage) {
        List<Tag> tagsS3 = productImage.tags().stream().map(
                t -> parseTagS3(t)
        ).collect(Collectors.toList());
        return tagsS3;
    }

    private Tag parseTagS3(ecomarkets.domain.core.product.image.Tag t){
//        try {
//            String key = URLEncoder.encode(t.key(), StandardCharsets.UTF_8.toString());
//            String value = URLEncoder.encode(t.value(), StandardCharsets.UTF_8.toString());
            String key = t.key();
            String value = t.value();
            return Tag.builder().key(key).value(value).build();
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getBucketName(){
        return this.bucketName;
    }

}
