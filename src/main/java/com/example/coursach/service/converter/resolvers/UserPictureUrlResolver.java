package com.example.coursach.service.converter.resolvers;

import com.example.coursach.entity.User;

@FunctionalInterface
public interface UserPictureUrlResolver {

    String getPictureUrlByUser(User user);

}
