package org.example.repository;

import org.example.domain.Membership;
import org.example.dto.parameterDto.IsMemberParameterDto;
import org.example.dto.parameterDto.MembershipParameterDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipMapper {
    void registerMember(Membership membership);

    void withdrawMember(Integer membershipId);

    Membership getMembership(MembershipParameterDto membershipParameterDto);

    Integer findOldestMember(Integer clubId);

    void handOverPresident(MembershipParameterDto membershipParameterDto);

    Membership isMember(IsMemberParameterDto isMemberParameterDto);

    void deleteUserAllMembership(Integer userId);
}
