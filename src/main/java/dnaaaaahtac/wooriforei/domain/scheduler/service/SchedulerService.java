package dnaaaaahtac.wooriforei.domain.scheduler.service;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerCreateRequestDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerResponseDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.Scheduler;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerMember;
import dnaaaaahtac.wooriforei.domain.scheduler.repository.SchedulerMemberRepository;
import dnaaaaahtac.wooriforei.domain.scheduler.repository.SchedulerRepository;
import dnaaaaahtac.wooriforei.domain.user.dto.UserDetailResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SchedulerRepository schedulerRepository;
    private final SchedulerMemberRepository schedulerMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public SchedulerResponseDTO createScheduler(SchedulerCreateRequestDTO requestDTO) {

        if (requestDTO.getStartDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_START_DATE);
        }

        if (requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new CustomException(ErrorCode.INVALID_END_DATE);
        }

        Scheduler scheduler = new Scheduler();
        scheduler.setSchedulerName(requestDTO.getSchedulerName());
        scheduler.setStartDate(requestDTO.getStartDate());
        scheduler.setEndDate(requestDTO.getEndDate());

        Scheduler savedScheduler = schedulerRepository.save(scheduler);

        List<User> users = userRepository.findByEmailIn(requestDTO.getMemberEmails());
        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        users.forEach(user -> {
            SchedulerMember member = new SchedulerMember();
            member.setScheduler(savedScheduler);
            member.setUser(user);
            schedulerMemberRepository.save(member);
        });

        List<UserDetailResponseDTO> memberDetails = users.stream()
                .map(user -> new UserDetailResponseDTO(user.getUserId(), user.getUsername(), user.getNickname(), user.getEmail()))
                .collect(Collectors.toList());

        SchedulerResponseDTO response = new SchedulerResponseDTO();
        response.setSchedulerId(savedScheduler.getSchedulerId());
        response.setSchedulerName(savedScheduler.getSchedulerName());
        response.setStartDate(savedScheduler.getStartDate());
        response.setEndDate(savedScheduler.getEndDate());
        response.setMembers(memberDetails);

        return response;
    }

    @Transactional
    public SchedulerResponseDTO getSchedulerById(Long schedulerId) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        List<SchedulerMember> schedulerMembers = schedulerMemberRepository.findByScheduler_SchedulerId(schedulerId);
        List<UserDetailResponseDTO> memberDetails = schedulerMembers.stream()
                .map(member -> new UserDetailResponseDTO(
                        member.getUser().getUserId(),
                        member.getUser().getUsername(),
                        member.getUser().getNickname(),
                        member.getUser().getEmail()))
                .collect(Collectors.toList());

        SchedulerResponseDTO response = new SchedulerResponseDTO();
        response.setSchedulerId(scheduler.getSchedulerId());
        response.setSchedulerName(scheduler.getSchedulerName());
        response.setStartDate(scheduler.getStartDate());
        response.setEndDate(scheduler.getEndDate());
        response.setMembers(memberDetails);

        return response;
    }

}
