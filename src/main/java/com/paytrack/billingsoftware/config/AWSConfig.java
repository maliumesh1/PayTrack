package com.paytrack.billingsoftware.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AWSConfig {
// The @Value annotation in Spring is basically a way to inject values
// (from application.properties, application.yml, environment variables,
//  or even inline expressions) into fields, method parameters, or constructor parameters.
//
    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;


    @Value("${aws.bucket.name}")
    private String bucketName;


   // @Bean //Marks this method as a Spring bean producer.
    // The returned S3Client will be managed by Spring's IoC container.

    /*s to create, configure, and register an Amazon S3 client in your Spring Boot application so that
     you can interact with AWS S3 without manually creating the client every time.*/
//    public S3Client s3Client()
//    {
//        System.out.println("Using AWS region: " + region); // ✅ Debug log
//        return S3Client.builder()
//        .region(Region.of(region))
//                .credentialsProvider((StaticCredentialsProvider.create( AwsBasicCredentials.create(accessKey,secretKey))))
//                .build();
//    }
    @Bean
    public S3Client s3Client() {
        String finalRegion = (region != null && !region.isBlank()) ? region : "eu-north-1";
        if (!"eu-north-1".equalsIgnoreCase(finalRegion)) {
            System.out.println("⚠️ Overriding region from '" + finalRegion + "' to 'eu-north-1'");
            finalRegion = "eu-north-1";
        }

        S3Client client = S3Client.builder()
                .region(Region.of(finalRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        // Test: List buckets
        try {
            System.out.println("Testing AWS S3 connection...");
            client.listBuckets().buckets().forEach(bucket ->
                    System.out.println("✅ Bucket found: " + bucket.name())
            );
            System.out.println("AWS S3 connection successful!");
        } catch (Exception e) {
            System.err.println("❌ AWS S3 connection failed: " + e.getMessage());
        }

        return client;
    }



    /*
What it enables
  Once this method runs, you can:
  Upload files to an S3 bucket
  Download files from S3
  List all buckets
  Delete objects or buckets
  Perform any S3 operations supported by AWS SDK
     */
}
