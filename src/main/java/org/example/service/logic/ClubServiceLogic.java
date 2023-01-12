package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.Club;
import org.example.domain.Membership;
import org.example.domain.User;
import org.example.dto.ClubDto;
import org.example.dto.parameterDto.MembershipParameterDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.repository.ClubMapper;
import org.example.repository.MembershipMapper;
import org.example.repository.UserMapper;
import org.example.service.CheckValidationService;
import org.example.service.ClubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubServiceLogic implements ClubService {
    private final ClubMapper clubMapper;
    private final UserMapper userMapper;
    private final MembershipMapper membershipMapper;
    private final CheckValidationService check;

    @Override
    public void createClub(Integer userId, ClubDto newClubDto) {
        check.checkValidationClub(newClubDto);

        Club newClub = newClubDto.toEntity();
        newClub.setClubInit();

        clubMapper.createClub(newClub);

        Club club = clubMapper.getClubByName(newClub.getName());

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Membership presidentMembership = new Membership(user.getId(), club.getId());
        boolean isPresident = true;
        presidentMembership.setMembershipInit(isPresident);

        membershipMapper.registerMember(presidentMembership);
        clubMapper.increaseCurrentUser(club.getId());
    }

    @Override
    public void registerClub(Integer userId, String clubName) {
        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        check.checkValidationMembership(user.getId(), club.getId());
        check.isPrivateClub(club.getPublicity());
        check.isFullClub(club.getMax_User(), club.getCurrent_User());

        Membership newMembership = new Membership(user.getId(), club.getId());
        boolean isPresident = false;
        newMembership.setMembershipInit(isPresident);

        membershipMapper.registerMember(newMembership);
        clubMapper.increaseCurrentUser(club.getId());
    }

    @Override
    public void withrawClub(Integer userId, String clubName) {
        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        Membership membership = membershipMapper.getMembership(new MembershipParameterDto(user.getId(), club.getId()));
        membership.checkValidation();

        if (membership.getRole() == 1) {
            handOverPresident(club.getId());
        }

        membershipMapper.withdrawMember(membership.getId());

        clubMapper.decreaseCurrentUser(club.getId());
        if (club.getCurrent_User() == 1) {
            clubMapper.deleteClub(club.getId());
        }
    }

    @Override
    public void updateName(Integer userId, String clubName, String newName) {
        check.checkValidationClubName(newName);

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        check.isPresidentMember(user.getId(), club.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(club.getId()).value(newName).build();

        clubMapper.updateName(updateParameterDto);
    }

    @Override
    public void updateIntro(Integer userId, String clubName, String newIntro) {
        check.checkValidationClubIntro(newIntro);

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        check.isPresidentMember(user.getId(), club.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(club.getId()).value(newIntro).build();

        clubMapper.updateIntro(updateParameterDto);
    }

    @Override
    public void updateMaxUser(Integer userId, String clubName, String newMaxUser) {
        check.checkValidationClubMaxUser(Short.parseShort(newMaxUser));

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        check.isPresidentMember(user.getId(), club.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(club.getId()).integerValue(Integer.parseInt(newMaxUser)).build();

        clubMapper.updateMaxUser(updateParameterDto);
    }

    @Override
    public void updatePublicity(Integer userId, String clubName, String newPublicity) {
        check.checkValidationClubPublicity(Byte.parseByte(newPublicity));

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        User user = userMapper.getUser(userId);
        user.checkValidation();

        check.isPresidentMember(user.getId(), club.getId());

        UpdateParameterDto updateParameterDto = UpdateParameterDto.builder().id(club.getId()).integerValue(Integer.parseInt(newPublicity)).build();

        clubMapper.updatePublicity(updateParameterDto);
    }

    @Override
    public ClubDto getMyClubInfo(Integer userId, String clubName) {//본인의 클럽 정보를 조회
        User user = userMapper.getUser(userId);
        user.checkValidation();

        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        Membership membership = membershipMapper.getMembership(new MembershipParameterDto(user.getId(), club.getId()));
        membership.checkValidation();

        return new ClubDto(club);
    }

    @Override
    public String findJoinableClub(String clubName) {
        Club club = clubMapper.getClubByName(clubName);
        club.checkValidation();

        check.isPrivateClub(club.getPublicity());

        ClubDto clubDto = new ClubDto(club);
        return clubDto.toString();
    }

    private void handOverPresident(Integer clubId) {
        Integer OldestMemberId = membershipMapper.findOldestMember(clubId);

        membershipMapper.handOverPresident(new MembershipParameterDto(OldestMemberId, clubId));
    }
}
