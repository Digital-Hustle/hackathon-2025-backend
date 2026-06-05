package rnd.sueta.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.gateway.MinioGateway;
import rnd.sueta.service.entity.PhotoService;

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
