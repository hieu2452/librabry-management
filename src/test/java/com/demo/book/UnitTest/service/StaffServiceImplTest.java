package com.demo.book.UnitTest.service;

import com.demo.book.entity.LibraryCard;
import com.demo.book.entity.Member;
import com.demo.book.exception.UserExistsException;
import com.demo.book.exception.UserNotFoundException;
import com.demo.book.repository.MemberRepository;
import com.demo.book.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private StaffServiceImpl staffService;

    @Test
    public void findMemberByLibraryCard_ReturnMember() {
        long cardId = 5L;
        Member expectedMember = new Member();
        expectedMember.setId(1);
        expectedMember.setFullName("max");
        expectedMember.setLibraryCard(new LibraryCard(cardId,"AVAILABLE",5));

        when(memberRepository.findByLibraryCard(cardId)).thenReturn(Optional.of(expectedMember));

        Member actualMember = staffService.findMemberByLibraryCard(cardId);

        assertEquals(expectedMember.getId(),actualMember.getId());

        verify(memberRepository, times(1)).findByLibraryCard(cardId);
    }

    @Test
    public void findMemberByInValidLibraryCard_ThrowException() {
        long cardId = 5L;
        Member expectedMember = new Member();
        expectedMember.setId(1);
        expectedMember.setFullName("max");
        expectedMember.setLibraryCard(new LibraryCard(3,"AVAILABLE",5));

        when(memberRepository.findByLibraryCard(any(Long.class))).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class,() -> staffService.findMemberByLibraryCard(cardId));

        verify(memberRepository, times(1)).findByLibraryCard(cardId);
    }
}
