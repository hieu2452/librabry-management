package com.demo.book.service;

import com.demo.book.entity.Member;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StaffService {
    Member findMemberByLibraryCard(long id);
}
