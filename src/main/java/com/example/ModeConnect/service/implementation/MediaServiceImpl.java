package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.response.ModelMediaDto;
import com.example.ModeConnect.Enums.MediaType;
import com.example.ModeConnect.Repository.ModelMediaRepository;
import com.example.ModeConnect.Repository.ModelRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.model.Model;
import com.example.ModeConnect.model.ModelMedia;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.service.interfaces.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaServiceImpl implements MediaService {

    private final ModelRepository modelRepository;
    private final ModelMediaRepository modelMediaRepository;
    private final UserRepository userRepository;

    @Value("${app.media.upload-dir}")
    private String uploadDir;

    @Override
    public ModelMediaDto uploadMedia(Long modelId, MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }

        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new RuntimeException("Model not found"));

        // Vérifier que le user est bien le creator du model
        if (model.getCreator() == null || !model.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to upload media for this model");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        MediaType mediaType;
        if (contentType != null && contentType.startsWith("image/")) {
            mediaType = MediaType.IMAGE;
        } else if (contentType != null && contentType.startsWith("video/")) {
            mediaType = MediaType.VIDEO;
        } else {
            throw new RuntimeException("Unsupported media type: " + contentType);
        }

        // Générer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) originalFilename = "file";
        String cleanName = StringUtils.cleanPath(originalFilename);

        String ext = "";
        int i = cleanName.lastIndexOf('.');
        if (i >= 0) ext = cleanName.substring(i);

        String filename = UUID.randomUUID() + ext;

        try {
            Path uploadPath = Path.of(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path target = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // Construire l'URL accessible (via WebConfig mapping /uploads/**)
            String mediaUrl = "/uploads/" + filename;

            ModelMedia media = new ModelMedia();
            media.setMediaUrl(mediaUrl);
            media.setMediaType(mediaType);
            media.setModel(model);

            ModelMedia saved = modelMediaRepository.save(media);

            // ajouter à la liste du model
            model.getMediaList().add(saved);
            modelRepository.save(model);

            ModelMediaDto dto = new ModelMediaDto();
            dto.setMediaUrl(mediaUrl);
            dto.setMediaType(saved.getMediaType());
            return dto;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void deleteMedia(Long mediaId) {
        ModelMedia media = modelMediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }

        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Seul le creator du model peut supprimer
        if (media.getModel().getCreator() == null || !media.getModel().getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this media");
        }

        // supprimer fichier du filesystem
        String mediaUrl = media.getMediaUrl();
        if (mediaUrl != null && mediaUrl.startsWith("/uploads/")) {
            String filename = mediaUrl.substring("/uploads/".length());
            Path path = Path.of(uploadDir).resolve(filename);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                // ignore
            }
        }

        modelMediaRepository.delete(media);
    }
}
