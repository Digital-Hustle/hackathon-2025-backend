package rnd.sueta.event_ms.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.gateway.MinioGateway;
import rnd.sueta.event_ms.service.entity.PhotoService;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final MinioGateway minioGateway;

    @Override
    public void save(String absoluteFilePath, MultipartFile photo) {
        minioGateway.savePhoto(absoluteFilePath, photo);
    }

    @Override
    public void delete(String absoluteFilePath) {
        minioGateway.deletePhoto(absoluteFilePath);
    }
}
