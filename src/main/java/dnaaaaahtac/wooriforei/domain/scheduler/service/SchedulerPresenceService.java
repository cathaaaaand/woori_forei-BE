package dnaaaaahtac.wooriforei.domain.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SchedulerPresenceService {

    private final Map<Long, Set<Long>> schedulerUsers = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public void userJoinScheduler(Long schedulerId, Long userId) {
        schedulerUsers.computeIfAbsent(schedulerId, k -> ConcurrentHashMap.newKeySet())
                      .add(userId);
        broadcastPresence(schedulerId);
    }

    public void userLeaveScheduler(Long schedulerId, Long userId) {
        Set<Long> users = schedulerUsers.get(schedulerId);
        if (users != null) {
            users.remove(userId);
            if (users.isEmpty()) {
                schedulerUsers.remove(schedulerId);
            }
        }
        broadcastPresence(schedulerId);
    }

    private void broadcastPresence(Long schedulerId) {
        Set<Long> users = schedulerUsers.getOrDefault(schedulerId, Set.of());
        messagingTemplate.convertAndSend("/topic/scheduler/" + schedulerId + "/presence",
            Map.of("onlineUsers", users, "count", users.size()));
    }
}