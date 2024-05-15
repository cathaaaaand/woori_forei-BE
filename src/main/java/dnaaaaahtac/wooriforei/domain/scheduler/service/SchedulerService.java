package dnaaaaahtac.wooriforei.domain.scheduler.service;

import dnaaaaahtac.wooriforei.domain.openapi.entity.*;
import dnaaaaahtac.wooriforei.domain.openapi.repository.*;
import dnaaaaahtac.wooriforei.domain.scheduler.dto.*;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.*;
import dnaaaaahtac.wooriforei.domain.scheduler.repository.*;
import dnaaaaahtac.wooriforei.domain.user.dto.UserDetailResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final RestaurantRepository restaurantRepository;
    private final SchedulerRestaurantRepository schedulerRestaurantRepository;
    private final SeoulGoodsRepository seoulGoodsRepository;
    private final SchedulerSeoulGoodsRepository schedulerSeoulGoodsRepository;

    @Transactional
    public SchedulerResponseDTO createScheduler(UserDetailsImpl userDetails, SchedulerRequestDTO requestDTO) {

        if (requestDTO.getStartDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INVALID_START_DATE);
        }

        if (requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new CustomException(ErrorCode.INVALID_END_DATE);
        }

        // 스케줄러 객체 생성
        Scheduler scheduler = new Scheduler();
        scheduler.setSchedulerName(requestDTO.getSchedulerName());
        scheduler.setStartDate(requestDTO.getStartDate());
        scheduler.setEndDate(requestDTO.getEndDate());

        Scheduler savedScheduler = schedulerRepository.save(scheduler);

        // 인증된 사용자 정보를 스케줄러 멤버로 추가
        User creator = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        SchedulerMember creatorMember = new SchedulerMember(savedScheduler, creator);
        schedulerMemberRepository.save(creatorMember);

        // 멤버 이메일로 사용자 검색
        List<User> users = userRepository.findByEmailIn(requestDTO.getMemberEmails());
        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        // 스케줄러 멤버로 사용자 추가
        users.forEach(user -> {
            SchedulerMember member = new SchedulerMember(savedScheduler, user);
            schedulerMemberRepository.save(member);
        });

        List<UserDetailResponseDTO> memberDetails = users.stream()
                .map(user -> new UserDetailResponseDTO(user.getUserId(), user.getUsername(), user.getNickname(), user.getEmail()))
                .collect(Collectors.toList());

        return getSchedulerResponseDTO(savedScheduler, memberDetails);
    }

    @Transactional
    public SchedulerResponseDTO getSchedulerById(UserDetailsImpl userDetails, Long schedulerId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        List<UserDetailResponseDTO> memberDetails = schedulerMemberRepository.findByScheduler_SchedulerId(schedulerId)
                .stream()
                .map(member -> new UserDetailResponseDTO(
                        member.getUser().getUserId(),
                        member.getUser().getUsername(),
                        member.getUser().getNickname(),
                        member.getUser().getEmail()))
                .collect(Collectors.toList());

        List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = collectOpenAPIDetails(scheduler);

        return getSchedulerResponseDTO(scheduler, memberDetails, openAPIs);
    }

    private List<SchedulerResponseDTO.OpenAPIDetailsDTO> collectOpenAPIDetails(Scheduler scheduler) {

        List<SchedulerActivity> activities = schedulerActivityRepository.findByScheduler(scheduler);
        List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = new ArrayList<>(activities.stream()
                .map(this::convertActivityToOpenAPIDetails)
                .toList());

        List<SchedulerHotel> hotels = schedulerHotelRepository.findByScheduler(scheduler);
        openAPIs.addAll(hotels.stream()
                .map(this::convertHotelToOpenAPIDetails)
                .toList());

        List<SchedulerInformation> informations = schedulerInformationRepository.findByScheduler(scheduler);
        openAPIs.addAll(informations.stream()
                .map(this::convertInformationToOpenAPIDetails)
                .toList());

        List<SchedulerLandmark> landmarks = schedulerLandmarkRepository.findByScheduler(scheduler);
        openAPIs.addAll(landmarks.stream()
                .map(this::convertLandmarkToOpenAPIDetails)
                .toList());

        List<SchedulerRestaurant> restaurants = schedulerRestaurantRepository.findByScheduler(scheduler);
        openAPIs.addAll(restaurants.stream()
                .map(this::convertRestaurantToOpenAPIDetails)
                .toList());

        List<SchedulerSeoulGoods> seoulGoodsList = schedulerSeoulGoodsRepository.findByScheduler(scheduler);
        openAPIs.addAll(seoulGoodsList.stream()
                .map(this::convertSeoulGoodsToOpenAPIDetails)
                .toList());

        return openAPIs;
    }

    private SchedulerResponseDTO.OpenAPIDetailsDTO convertActivityToOpenAPIDetails(SchedulerActivity activity) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                activity.getActivity().getActivityId(),
                activity.getActivity().getSvcnm(),
                activity.getVisitStart(),
                activity.getVisitEnd(),
                "activity"
        );
    }

    private SchedulerResponseDTO.OpenAPIDetailsDTO convertHotelToOpenAPIDetails(SchedulerHotel hotel) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                hotel.getHotel().getHotelId(),
                hotel.getHotel().getNameKor(),
                hotel.getStayStart(),
                hotel.getStayEnd(),
                "hotel"
        );
    }

    private SchedulerResponseDTO.OpenAPIDetailsDTO convertInformationToOpenAPIDetails(SchedulerInformation information) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                information.getInformation().getInformationId(),
                information.getInformation().getTrsmicnm(),
                information.getVisitStart(),
                information.getVisitEnd(),
                "information"
        );
    }


    private SchedulerResponseDTO.OpenAPIDetailsDTO convertLandmarkToOpenAPIDetails(SchedulerLandmark landmark) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                landmark.getLandmark().getRandmarkId(),
                landmark.getLandmark().getPostSj(),
                landmark.getVisitStart(),
                landmark.getVisitEnd(),
                "landmark"
        );
    }

    private SchedulerResponseDTO.OpenAPIDetailsDTO convertRestaurantToOpenAPIDetails(SchedulerRestaurant restaurant) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                restaurant.getRestaurant().getRestaurantId(),
                restaurant.getRestaurant().getPostSj(),
                restaurant.getVisitStart(),
                restaurant.getVisitEnd(),
                "restaurant"
        );
    }

    private SchedulerResponseDTO.OpenAPIDetailsDTO convertSeoulGoodsToOpenAPIDetails(SchedulerSeoulGoods seoulGoods) {

        return new SchedulerResponseDTO.OpenAPIDetailsDTO(
                seoulGoods.getSeoulGoods().getSeoulGoodsId(),
                seoulGoods.getSeoulGoods().getNm(),
                seoulGoods.getVisitStart(),
                seoulGoods.getVisitEnd(),
                "seoulGoods"
        );
    }

    private SchedulerResponseDTO getSchedulerResponseDTO(
            Scheduler scheduler,
            List<UserDetailResponseDTO> memberDetails,
            List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs) {

        SchedulerResponseDTO response = new SchedulerResponseDTO();
        response.setSchedulerId(scheduler.getSchedulerId());
        response.setSchedulerName(scheduler.getSchedulerName());
        response.setStartDate(scheduler.getStartDate());
        response.setEndDate(scheduler.getEndDate());
        response.setCreatedAt(scheduler.getCreatedAt());
        response.setModifiedAt(scheduler.getModifiedAt());
        response.setMembers(memberDetails);
        response.setOpenAPIs(openAPIs);

        return response;
    }

    @Transactional
    public List<SchedulerResponseDTO> getAllSchedulers(UserDetailsImpl userDetails) {

        List<Scheduler> schedulers = schedulerRepository.findAll();

        return schedulers.stream().map(scheduler -> {
            List<UserDetailResponseDTO> memberDetails = schedulerMemberRepository.findByScheduler_SchedulerId(scheduler.getSchedulerId())
                    .stream()
                    .map(member -> new UserDetailResponseDTO(
                            member.getUser().getUserId(),
                            member.getUser().getUsername(),
                            member.getUser().getNickname(),
                            member.getUser().getEmail()))
                    .collect(Collectors.toList());

            List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = collectOpenAPIDetails(scheduler);

            return getSchedulerResponseDTO(scheduler, memberDetails, openAPIs);
        }).collect(Collectors.toList());
    }

    @Transactional
    public SchedulerResponseDTO updateScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerRequestDTO requestDTO) {

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

        List<UserDetailResponseDTO> memberDetails = schedulerMemberRepository.findByScheduler(scheduler)
                .stream()
                .map(member -> new UserDetailResponseDTO(
                        member.getUser().getUserId(),
                        member.getUser().getUsername(),
                        member.getUser().getNickname(),
                        member.getUser().getEmail()))
                .collect(Collectors.toList());

        List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = collectOpenAPIDetails(scheduler);

        return getSchedulerResponseDTO(scheduler, memberDetails, openAPIs);
    }

    private SchedulerResponseDTO getSchedulerResponseDTO(Scheduler scheduler) {

        List<UserDetailResponseDTO> memberDetails = schedulerMemberRepository.findByScheduler(scheduler)
                .stream()
                .map(member -> new UserDetailResponseDTO(
                        member.getUser().getUserId(),
                        member.getUser().getUsername(),
                        member.getUser().getNickname(),
                        member.getUser().getEmail()))
                .collect(Collectors.toList());

        List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = collectOpenAPIDetails(scheduler);

        SchedulerResponseDTO response = new SchedulerResponseDTO();
        response.setSchedulerId(scheduler.getSchedulerId());
        response.setSchedulerName(scheduler.getSchedulerName());
        response.setStartDate(scheduler.getStartDate());
        response.setEndDate(scheduler.getEndDate());
        response.setCreatedAt(scheduler.getCreatedAt());
        response.setModifiedAt(scheduler.getModifiedAt());
        response.setMembers(memberDetails);
        response.setOpenAPIs(openAPIs);

        return response;
    }

    private void updateSchedulerMembers(Scheduler scheduler, List<String> memberEmails) {

        if (memberEmails == null) return;

        List<SchedulerMember> existingMembers = schedulerMemberRepository.findByScheduler(scheduler);
        List<String> existingEmails = existingMembers.stream()
                .map(member -> member.getUser().getEmail())
                .toList();

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
    public void deleteScheduler(UserDetailsImpl userDetails, Long schedulerId) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        schedulerMemberRepository.deleteAllByScheduler(scheduler);
        schedulerRepository.delete(scheduler);
    }

    @Transactional
    public void addActivityToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerActivityRequestDTO activityDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        Activity activity = activityRepository.findById(activityDTO.getActivityId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACTIVITY));

        checkForTimeConflicts(scheduler, activityDTO.getVisitStart(), activityDTO.getVisitEnd(), activityDTO.getActivityId());

        if (activityDTO.getVisitStart().isBefore(scheduler.getStartDate())) {
            throw new CustomException(ErrorCode.INVALID_START_DATE);
        }

        if (activityDTO.getVisitEnd().isAfter(scheduler.getEndDate())) {
            throw new CustomException(ErrorCode.INVALID_END_DATE);
        }

        SchedulerActivity schedulerActivity = new SchedulerActivity(
                scheduler, activity, activityDTO.getVisitStart(), activityDTO.getVisitEnd());
        schedulerActivityRepository.save(schedulerActivity);

        getSchedulerResponseDTO(scheduler);
    }

    @Transactional
    public void addHotelToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerHotelRequestDTO hotelDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        checkForTimeConflicts(scheduler, hotelDTO.getStayStart(), hotelDTO.getStayEnd(), hotelDTO.getHotelId());

        Hotel hotel = hotelRepository.findById(hotelDTO.getHotelId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOTEL));

        LocalDateTime startAt = hotelDTO.getStayStart();
        LocalDateTime endAt = hotelDTO.getStayEnd();

        SchedulerHotel schedulerHotel = new SchedulerHotel(scheduler, hotel, startAt, endAt);
        schedulerHotelRepository.save(schedulerHotel);
    }

    @Transactional
    public void addInformationToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerInformationRequestDTO informationDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        checkForTimeConflicts(scheduler, informationDTO.getVisitStart(), informationDTO.getVisitEnd(), informationDTO.getInformationId());

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
    public void addLandmarkToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerLandmarkRequestDTO landmarkDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        checkForTimeConflicts(scheduler, landmarkDTO.getVisitStart(), landmarkDTO.getVisitEnd(), landmarkDTO.getLandmarkId());

        Landmark landmark = landmarkRepository.findById(landmarkDTO.getLandmarkId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LANDMARK));

        SchedulerLandmark schedulerLandmark = new SchedulerLandmark(
                scheduler, landmark,
                landmarkDTO.getVisitStart(), landmarkDTO.getVisitEnd());

        schedulerLandmarkRepository.save(schedulerLandmark);
    }

    @Transactional
    public void addRestaurantToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerRestaurantRequestDTO restaurantDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        checkForTimeConflicts(scheduler, restaurantDTO.getVisitStart(), restaurantDTO.getVisitEnd(), restaurantDTO.getRestaurantId());

        Restaurant restaurant = restaurantRepository.findById(restaurantDTO.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESTAURANT));

        SchedulerRestaurant schedulerRestaurant = new SchedulerRestaurant(
                scheduler,
                restaurant,
                restaurantDTO.getVisitStart(),
                restaurantDTO.getVisitEnd());

        schedulerRestaurantRepository.save(schedulerRestaurant);
    }

    @Transactional
    public void addSeoulGoodsToScheduler(UserDetailsImpl userDetails, Long schedulerId, SchedulerSeoulGoodsRequestDTO seoulGoodsDTO) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        checkForTimeConflicts(scheduler, seoulGoodsDTO.getVisitStart(), seoulGoodsDTO.getVisitEnd(), seoulGoodsDTO.getGoodsId());

        SeoulGoods seoulGoods = seoulGoodsRepository.findById(seoulGoodsDTO.getGoodsId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SEOUL_GOODS));

        SchedulerSeoulGoods schedulerSeoulGoods = new SchedulerSeoulGoods(
                scheduler,
                seoulGoods,
                seoulGoodsDTO.getVisitStart(),
                seoulGoodsDTO.getVisitEnd());

        schedulerSeoulGoodsRepository.save(schedulerSeoulGoods);
    }

    @Transactional
    public SchedulerResponseDTO addMemberToScheduler(UserDetailsImpl userDetails, Long schedulerId, String userEmail) {

        Scheduler scheduler = schedulerRepository.findById(schedulerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULER));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        SchedulerMember member = new SchedulerMember(scheduler, user);
        schedulerMemberRepository.save(member);

        List<UserDetailResponseDTO> memberDetails = schedulerMemberRepository.findByScheduler(scheduler)
                .stream()
                .map(mem -> new UserDetailResponseDTO(
                        mem.getUser().getUserId(),
                        mem.getUser().getUsername(),
                        mem.getUser().getNickname(),
                        mem.getUser().getEmail()))
                .collect(Collectors.toList());

        List<SchedulerResponseDTO.OpenAPIDetailsDTO> openAPIs = collectOpenAPIDetails(scheduler);

        return getSchedulerResponseDTO(scheduler, memberDetails, openAPIs);
    }

    private void checkForTimeConflicts(Scheduler scheduler, LocalDateTime start, LocalDateTime end, Long entityId) {

        boolean hasConflict = scheduler.getEvents().stream()
                .anyMatch(event -> !Objects.equals(event.getEventId(), entityId) &&
                        event.getStartTime().isBefore(end) &&
                        event.getEndTime().isAfter(start));
        if (hasConflict) {
            throw new CustomException(ErrorCode.INVALID_TIME_OVERLAP);
        }
    }
}
