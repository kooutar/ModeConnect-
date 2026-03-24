package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.request.ChatMessageRequestDto;
import com.example.ModeConnect.DTO.response.ChatMessageResponseDto;
import com.example.ModeConnect.Repository.ChatMessageRepository;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.model.ChatMessage;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.service.interfaces.ChatMessageServiceInterface;
import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageServiceInterface {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
   // private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto request) {
        User sender = getCurrentUser();
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        ChatMessageResponseDto response = mapToDto(saved);

        // Push to receiver in real-time using their email (Principal name)
//        messagingTemplate.convertAndSendToUser(
//                receiver.getEmail(),
//                "/queue/messages",
//                response
//        );

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getConversationWith(Long partnerId) {
        User currentUser = getCurrentUser();
        User partner = userRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        return chatMessageRepository.findConversationBetween(currentUser, partner)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(Long partnerId) {
        User currentUser = getCurrentUser();
        User partner = userRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        List<ChatMessage> unreadMessages = chatMessageRepository.findConversationBetween(currentUser, partner)
                .stream()
                .filter(m -> m.getReceiver().equals(currentUser) && !m.isRead())
                .collect(Collectors.toList());

        unreadMessages.forEach(m -> m.setRead(true));
        chatMessageRepository.saveAll(unreadMessages);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadMessages() {
        return chatMessageRepository.countByReceiverAndIsReadFalse(getCurrentUser());
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    private ChatMessageResponseDto mapToDto(ChatMessage message) {
        return ChatMessageResponseDto.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .isRead(message.isRead())
                .build();
    }
}
