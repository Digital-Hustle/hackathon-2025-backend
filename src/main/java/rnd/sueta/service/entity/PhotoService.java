package rnd.sueta.service.entity;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    void save(String absoluteFilePath, MultipartFile photo);

    void delete(String absoluteFilePath);
}
