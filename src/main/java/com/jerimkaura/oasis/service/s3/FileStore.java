package com.jerimkaura.oasis.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@Service
@Slf4j
public class FileStore {
    private final AmazonS3 amazonS3;
    private final Logger logger = LoggerFactory.getLogger(FileStore.class);
    public String uploadFile(
            String fileName, String
            bucketName, String folder,
            MultipartFile multipartFile
    ) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String filePath = null;
        try {
            String path = String.format("%s/%s", bucketName, folder);
            amazonS3.putObject(path, fileName, multipartFile.getInputStream(), metadata);
            filePath = "https://" + bucketName + ".s3.amazonaws.com/" + folder + "/" + fileName;
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
