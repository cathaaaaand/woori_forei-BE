package dnaaaaahtac.wooriforei.domain.scheduler.service;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Activity;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Hotel;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Landmark;
import dnaaaaahtac.wooriforei.domain.openapi.repository.ActivityRepository;
import dnaaaaahtac.wooriforei.domain.openapi.repository.HotelRepository;
import dnaaaaahtac.wooriforei.domain.openapi.repository.InformationRepository;
import dnaaaaahtac.wooriforei.domain.openapi.repository.LandmarkRepository;
import dnaaaaahtac.wooriforei.domain.scheduler.dto.*;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.*;
import dnaaaaahtac.wooriforei.domain.scheduler.repository.*;
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
    private final ActivityRepository activityRepository;
    private final SchedulerActivityRepository schedulerActivityRepository;
    private final SchedulerHotelRepository schedulerHotelRepository;
    private final InformationRepository informationRepository;
    private final SchedulerInformationRepository schedulerInformationRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final LandmarkRepository landmarkRepository;
    private final SchedulerLandmarkRepository schedulerLandmarkRepository;

    @Transactional
    public SchedulerResponseDTO createScheduler(SchedulerRequestDTO requestDTO) {

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
            SchedulerMember member = new SchedulerMember(savedScheduler, user);
            member.setScheduler(savedScheduler);
            member.setUser(user);
            schedulerMemberRepository.save(member);
        });

        List<UserDetailResponseDTO> memberDetails = users.stream()
                .map(user -> new UserDetailResponseDTO(user.getUserId(), user.getUsername(), user.getNickname(), user.getEmail()))
                .collect(Collectors.toList());

        return getSchedulerResponseDTO(savedScheduler, memberDetails);
    }

    @Transactional
    public SchedulerResponseDTO getSchedulerById(Long schedulerId) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        return getSchedulerResponseDTO(schedulerId, scheduler);
    }

    @Transactional
    public List<SchedulerResponseDTO> getAllSchedulers() {
        List<Scheduler> schedulers = schedulerRepository.findAll();

        return schedulers.stream().map(scheduler -> {
            List<SchedulerMember> schedulerMembers
                    = schedulerMemberRepository.findByScheduler_SchedulerId(scheduler.getSchedulerId());
            List<UserDetailResponseDTO> memberDetails = schedulerMembers.stream()
                    .map(member -> new UserDetailResponseDTO(
                            member.getUser().getUserId(),
                            member.getUser().getUsername(),
                            member.getUser().getNickname(),
                            member.getUser().getEmail()))
                    .collect(Collectors.toList());

            return getSchedulerResponseDTO(scheduler, memberDetails);

        }).collect(Collectors.toList());
    }

    @Transactional
    public SchedulerResponseDTO updateScheduler(Long schedulerId, SchedulerRequestDTO requestDTO) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        if (requestDTO.getStartDate() != null && requestDTO.getStartDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_START_DATE);
        }
        if (requestDTO.getEndDate() != null && requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new CustomException(ErrorCode.INVALID_END_DATE);
        }

        if (requestDTO.getSchedulerName() != null) {
            scheduler.setSchedulerName(requestDTO.getSchedulerName());
        }
        if (requestDTO.getStartDate() != null) {
            scheduler.setStartDate(requestDTO.getStartDate());
        }
        if (requestDTO.getEndDate() != null) {
            scheduler.setEndDate(requestDTO.getEndDate());
        }

        updateSchedulerMembers(scheduler, requestDTO.getMemberEmails());

        schedulerRepository.save(scheduler);

        return getSchedulerResponseDTO(schedulerId, scheduler);
    }

    private SchedulerResponseDTO getSchedulerResponseDTO(Long schedulerId, Scheduler scheduler) {
        List<SchedulerMember> schedulerMembers = schedulerMemberRepository.findByScheduler_SchedulerId(schedulerId);
        List<UserDetailResponseDTO> memberDetails = schedulerMembers.stream()
                .map(member -> new UserDetailResponseDTO(
                        member.getUser().getUserId(),
                        member.getUser().getUsername(),
                        member.getUser().getNickname(),
                        member.getUser().getEmail()))
                .collect(Collectors.toList());

        return getSchedulerResponseDTO(scheduler, memberDetails);
    }

    private void updateSchedulerMembers(Scheduler scheduler, List<String> memberEmails) {
        if (memberEmails == null) return;  // 멤버 변경이 없으면 로직을 수행하지 않음

        List<SchedulerMember> existingMembers = schedulerMemberRepository.findByScheduler(scheduler);
        List<String> existingEmails = existingMembers.stream()
                .map(member -> member.getUser().getEmail())
                .toList();

        // 새 멤버 추가
        memberEmails.forEach(email -> {
            if (!existingEmails.contains(email)) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
                schedulerMemberRepository.save(new SchedulerMember(scheduler, user));
            }
        });

        existingMembers.stream()
                .filter(member -> !memberEmails.contains(member.getUser().getEmail()))
                .forEach(schedulerMemberRepository::delete);
    }


    private SchedulerResponseDTO getSchedulerResponseDTO(Scheduler scheduler, List<UserDetailResponseDTO> memberDetails) {
        SchedulerResponseDTO response = new SchedulerResponseDTO();
        response.setSchedulerId(scheduler.getSchedulerId());
        response.setSchedulerName(scheduler.getSchedulerName());
        response.setStartDate(scheduler.getStartDate());
        response.setEndDate(scheduler.getEndDate());
        response.setCreatedAt(scheduler.getCreatedAt());
        response.setModifiedAt(scheduler.getModifiedAt());
        response.setMembers(memberDetails);

        return response;
    }

    @Transactional
    public void deleteScheduler(Long schedulerId) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        schedulerMemberRepository.deleteAllByScheduler(scheduler);
        schedulerRepository.delete(scheduler);
    }

    @Transactional
    public void addActivityToScheduler(Long schedulerId, SchedulerActivityRequestDTO activityDTO) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        Activity activity = activityRepository.findById(activityDTO.getActivityId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACTIVITY));

        if (activityDTO.getVisitStart().isBefore(scheduler.getStartDate())) {
            throw new CustomException(ErrorCode.INVALID_START_DATE);
        }

        if (activityDTO.getVisitEnd().isAfter(scheduler.getEndDate())) {
            throw new CustomException(ErrorCode.INVALID_END_DATE);
        }

        SchedulerActivity schedulerActivity = new SchedulerActivity(
                scheduler, activity, activityDTO.getVisitStart(), activityDTO.getVisitEnd());
        schedulerActivityRepository.save(schedulerActivity);

        getSchedulerResponseDTO(schedulerId, scheduler);
    }

    @Transactional
    public void addHotelToScheduler(Long schedulerId, SchedulerHotelRequestDTO hotelDTO) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        Hotel hotel = hotelRepository.findById(hotelDTO.getHotelId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOTEL));

        LocalDateTime startAt = hotelDTO.getStayStart();
        LocalDateTime endAt = hotelDTO.getStayEnd();

        SchedulerHotel schedulerHotel = new SchedulerHotel(scheduler, hotel, startAt, endAt);
        schedulerHotelRepository.save(schedulerHotel);
    }

    @Transactional
    public void addInformationToScheduler(Long schedulerId, SchedulerInformationRequestDTO informationDTO) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));
        Information information = informationRepository.findById(informationDTO.getInformationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INFORMATION));

        SchedulerInformation schedulerInformation = new SchedulerInformation(
                scheduler,
                information,
                informationDTO.getVisitStart(),
                informationDTO.getVisitEnd());

        schedulerInformationRepository.save(schedulerInformation);
    }

    @Transactional
    public void addLandmarkToScheduler(Long schedulerId, SchedulerLandmarkRequestDTO landmarkDTO) {
        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        Landmark landmark = landmarkRepository.findById(landmarkDTO.getLandmarkId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LANDMARK));

        SchedulerLandmark schedulerLandmark = new SchedulerLandmark(
                scheduler, landmark,
                landmarkDTO.getVisitStart(), landmarkDTO.getVisitEnd());

        schedulerLandmarkRepository.save(schedulerLandmark);
    }
}