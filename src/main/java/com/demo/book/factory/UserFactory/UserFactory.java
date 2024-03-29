package com.demo.book.factory.UserFactory;

import com.demo.book.entity.enums.UserType;

public class UserFactory {

    public static UserAbstractFactory getFactory(UserType userType) {
        switch (userType) {
            case STAFF :
                return new StaffFactory();
            case MEMBER :
                return new MemberFactory();
            default :
                throw new UnsupportedOperationException("This user is not supported");
        }

    }
}
