package com.xidian.bookstore.dao;

import com.xidian.bookstore.entities.user.User;
import com.xidian.bookstore.entities.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<UserAddress,Integer> {

    public void deleteByAddressId(Integer addressId);
    public UserAddress findByAddressId(Integer addressId);
    public List<UserAddress> findUserAddressByUser(User user);
    public UserAddress findByFlag(String flag);

}
