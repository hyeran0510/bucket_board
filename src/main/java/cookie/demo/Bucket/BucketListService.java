package cookie.demo.Bucket;

import cookie.demo.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BucketListService {

    private final BucketListRepository bucketListRepository;

    public List<Bucket> getList() {
        return this.bucketListRepository.findAll();
    }

    public Bucket getBucket(Long id) {
        Optional<Bucket> bucket = this.bucketListRepository.findById(id);
        if (bucket.isPresent()) {
            return bucket.get();
        } else {
            throw new DataNotFoundException("bucket not found");
        }
    }

    public void create(String title, String items, MultipartFile file, int rating) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        Bucket bucket = new Bucket();
        bucket.setItems(items);
        bucket.setFileName(fileName);
        bucket.setFilePath("/static/files/" + fileName);
        bucket.setRating(rating);
        bucket.setCreateDate(LocalDateTime.now());

        this.bucketListRepository.save(bucket);
    }


    public void updateBucket(Bucket bucket, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            bucket.setFileName(fileName);
            bucket.setFilePath("/static/files/" + fileName);
        }
        this.bucketListRepository.save(bucket);
    }

    public void deleteBucket(Long id) {
        this.bucketListRepository.deleteById(id);
    }

}