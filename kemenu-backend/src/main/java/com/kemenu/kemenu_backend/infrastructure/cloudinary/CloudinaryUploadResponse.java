package com.kemenu.kemenu_backend.infrastructure.cloudinary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CloudinaryUploadResponse.CloudinaryUploadResponseBuilder.class)
public class CloudinaryUploadResponse {
    String signature;
    String format;
    String resourceType;
    String secureUrl;
    Instant createdAt;
    String assetId;
    String versionId;
    String type;
    Integer version;
    String url;
    String publicId;
    List<String> tags;
    String originalFilename;
    Integer bytes;
    Integer width;
    Integer height;
    String etag;
    Boolean placeholder;

    public static CloudinaryUploadResponse from(Map<String, Object> uploadResponse) {
        return CloudinaryUploadResponse.builder()
                .signature((String) uploadResponse.get("signature"))
                .format((String) uploadResponse.get("format"))
                .resourceType((String) uploadResponse.get("resource_type"))
                .secureUrl((String) uploadResponse.get("secure_url"))
                .createdAt(Instant.parse((String) uploadResponse.get("created_at")))
                .assetId((String) uploadResponse.get("asset_id"))
                .versionId((String) uploadResponse.get("version_id"))
                .type((String) uploadResponse.get("type"))
                .version((Integer) uploadResponse.get("version"))
                .url((String) uploadResponse.get("url"))
                .publicId((String) uploadResponse.get("public_id"))
                .tags((ArrayList<String>) uploadResponse.get("tags"))
                .originalFilename((String) uploadResponse.get("original_filename"))
                .bytes((Integer) uploadResponse.get("bytes"))
                .width((Integer) uploadResponse.get("width"))
                .height((Integer) uploadResponse.get("height"))
                .etag((String) uploadResponse.get("etag"))
                .placeholder((Boolean) uploadResponse.get("placeholder"))
                .build();
    }
}
