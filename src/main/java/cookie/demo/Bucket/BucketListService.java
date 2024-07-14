package cookie.demo.Bucket;

import cookie.demo.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BucketListService {

    private final BucketListRepository bucketListRepository;
    // 파일 저장 경로 설정
    private final String uploadDir = "/Users/hyeranpakr/Downloads/demo/src/main/resources/static/files/";

    // 최신 항목이 위로 올라오게 하는 getList 메서드
    @Transactional(readOnly = true)
    public List<Bucket> getList() {
        return bucketListRepository.findAllByOrderByCreateDateDesc();
    }

    public Bucket getBucket(Long id) {
        Optional<Bucket> bucket = this.bucketListRepository.findById(id);
        return bucket.orElseThrow(() -> new DataNotFoundException("Bucket not found with id: " + id));
    }

    public void create(String title, String items, MultipartFile file, int rating) throws IOException {
        // 파일 저장할 경로 설정
        String projectPath = System.getProperty("user.dir") + "/" + uploadDir;

        // 디렉토리가 존재하지 않으면 생성
        Files.createDirectories(Paths.get(projectPath));

        // 파일 이름 중복 방지를 위한 UUID 생성
        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(projectPath + fileName);

        // 파일 저장
        Files.copy(file.getInputStream(), filePath);

        // Bucket 객체 생성 및 저장
        Bucket bucket = new Bucket();
        bucket.setTitle(title);
        bucket.setItems(items);
        bucket.setFileName(fileName);
        bucket.setFilePath("/files/" + fileName);
        bucket.setRating(rating);
        bucket.setCreateDate(LocalDateTime.now());

        this.bucketListRepository.save(bucket);
    }

    public void modify(Bucket bucket, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // 파일 저장할 경로 설정
            String projectPath = System.getProperty("user.dir") + "/" + uploadDir;

            // 디렉토리가 존재하지 않으면 생성
            Files.createDirectories(Paths.get(projectPath));

            // 기존 파일 삭제 (선택 사항)
            if (bucket.getFileName() != null) {
                Files.deleteIfExists(Paths.get(projectPath + bucket.getFileName()));
            }

            // 파일 이름 중복 방지를 위한 UUID 생성
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(projectPath + fileName);

            // 파일 저장
            Files.copy(file.getInputStream(), filePath);

            // Bucket 객체 업데이트
            bucket.setFileName(fileName);
            bucket.setFilePath("/files/" + fileName);
        }

        this.bucketListRepository.save(bucket);
    }

    public void deleteBucket(Long id) {
        Optional<Bucket> optionalBucket = bucketListRepository.findById(id);
        optionalBucket.ifPresent(bucket -> {
            try {
                // 파일 삭제
                String projectPath = System.getProperty("user.dir") + "/" + uploadDir;
                Files.deleteIfExists(Paths.get(projectPath + bucket.getFileName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 데이터베이스에서 삭제
            bucketListRepository.deleteById(id);
        });
    }
}