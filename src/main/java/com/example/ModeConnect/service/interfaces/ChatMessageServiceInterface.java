package com.example.ModeConnect.service.interfaces;

import com.example.ModeConnect.DTO.request.ChatMessageRequestDto;
import com.example.ModeConnect.DTO.response.ChatMessageResponseDto;

import java.util.List;

public interface ChatMessageServiceInterface {
    ChatMessageResponseDto sendMessage(ChatMessageRequestDto request);
    List<ChatMessageResponseDto> getConversationWith(Long partnerId);
    void markAsRead(Long partnerId);
    long countUnreadMessages();
}
