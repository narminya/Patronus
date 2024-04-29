package com.demo.patronus.services;


import com.demo.patronus.exception.StreamNotFoundException;
import com.demo.patronus.exception.UserNotFoundException;
import com.demo.patronus.models.Like;
import com.demo.patronus.repository.LikeRepository;
import com.demo.patronus.repository.StreamRepository;
import com.demo.patronus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likesRepository;
    private final UserRepository userRepository;
    private final StreamRepository streamRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void likeStream(UUID userId, UUID streamId){

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new StreamNotFoundException(streamId));

        likesRepository.save(
                Like.builder().stream(stream).user(user).build());
        Integer likeCount = stream.getLikeCount();
        broadcastLikeCountUpdate(streamId,likeCount);
    }


    private void broadcastLikeCountUpdate(UUID streamId, Integer likeCount) {
        messagingTemplate.convertAndSend("/topic/likeCount/" + streamId, likeCount);
    }
}
