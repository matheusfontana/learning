package com.appsdeveloperblog.photoapp.api.users.ui.repository;

import com.appsdeveloperblog.photoapp.api.users.ui.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

}
