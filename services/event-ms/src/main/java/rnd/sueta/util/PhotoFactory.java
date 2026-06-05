package rnd.sueta.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.model.PhotoWithUrl;
import rnd.sueta.model.entity.PhotoMeta;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhotoFactory {

    public static PhotoMeta newDefaultPhotoMetaInfo(MultipartFile file) {
        String fileNameWithExtension = file.getOriginalFilename();

        String fileName = FilenameUtils.getBaseName(fileNameWithExtension);
        String extension = FilenameUtils.getExtension(fileNameWithExtension);

        return PhotoMeta.builder()
                .originalFileName(fileName)
                .extension(extension)
                .fileSize((int) file.getSize())
                .contentType(file.getContentType())
                .build();
    }

    public static PhotoWithUrl newDefaultPhotoWithUrl(PhotoMeta photoMeta, String url) {
        return PhotoWithUrl.builder()
                .id(photoMeta.getId())
                .originalFileName(photoMeta.getOriginalFileName())
                .url(url)
                .build();
    }
}
