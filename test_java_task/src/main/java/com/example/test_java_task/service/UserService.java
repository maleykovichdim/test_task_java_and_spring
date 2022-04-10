package com.example.test_java_task.service;

import com.example.test_java_task.common.SectorStorage;
import com.example.test_java_task.model.pojo.User;
import com.example.test_java_task.model.pojo.UserSector;
import com.example.test_java_task.model.request.UserRequest;
import com.example.test_java_task.repository.SectorRepository;
import com.example.test_java_task.repository.UserRepository;
import com.example.test_java_task.repository.UserSectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SectorStorage sectorStorage;
    private final UserSectorRepository userSectorRepository;

    public UserService(UserRepository userRepository, SectorRepository sectorRepository, SectorStorage sectorStorage, UserSectorRepository userSectorRepository) {
        this.userRepository = userRepository;
        this.sectorStorage = sectorStorage;
        this.userSectorRepository = userSectorRepository;
    }

    public User getUser(String name){
        if (! name.matches( "[a-zA-Z]*" ) && !name.matches( "[a-zA-Z]+ [a-zA-Z]+$" )){
            return null;
        }
        Optional<User> checkUser = userRepository.findByName(name);
        if (!checkUser.isPresent()) return null;
        return checkUser.get();
    }


    public String saveUser(UserRequest input){

        String name = input.getName();
        if (! name.matches( "[a-zA-Z]*" ) && !name.matches( "[a-zA-Z]+ [a-zA-Z]+$" )){
            return "Wrong name: "+name;
        }

        int value_error = sectorStorage.checkSectorValues(input.getSectors());
        if (value_error != 0){
            return "Wrong sector value: "+value_error;
        }

        Optional<User> checkUser = userRepository.findByName(name);
        if (checkUser.isPresent()) return "User with name: "+name+" already exists!";

        User user = new User();
        user.setName(name);
        if (input.getAgreeTerms().intValue() == 1){
            user.setAgreeTerms(true);
        }else{
            user.setAgreeTerms(false);
        }
        userRepository.save(user);
        List<UserSector> userSectors = new ArrayList<>();
        for (Integer value: input.getSectors()) {
            UserSector us = new UserSector();
            us.setUserId(user.getId());
            us.setSectorValue(value);
            userSectors.add(us);
        }
        for (UserSector userSector: userSectors){
            userSectorRepository.save(userSector);
        }
        return "Done";
    }



    public String updateUser(UserRequest input){

        String name = input.getName();
        if (! name.matches( "[a-zA-Z]*" ) && !name.matches( "[a-zA-Z]+ [a-zA-Z]+$" )){
            return "Wrong name: "+name;
        }

        int value_error = sectorStorage.checkSectorValues(input.getSectors());
        if (value_error != 0){
            return "Wrong sector value: "+value_error;
        }

        Optional<User> checkUser = userRepository.findByName(name);
        if (!checkUser.isPresent()) return "User with name: "+name+" does not exist!";

        User user = checkUser.get();

        Boolean inputBoolean = (input.getAgreeTerms().intValue() == 1)? true: false;
        if (!user.getAgreeTerms().equals(inputBoolean)){
            if (input.getAgreeTerms().intValue() == 1) {
                user.setAgreeTerms(true);
            } else {
                user.setAgreeTerms(false);
            }
            userRepository.save(user);
        }
        List<UserSector> userSectors = user.getSectors();
        user.setSectors(null);



        List<UserSector> userSectorsDelete = new LinkedList<>();
        for (UserSector userSector: userSectors){
            boolean flag = true;
            for (Integer value: input.getSectors()) {
                if (userSector.getSectorValue().equals(value)){
                    flag = false; break;
                }
            }
            if (flag){
                userSectorsDelete.add(userSector);
                userSectorRepository.delete(userSector);
            }
        }
//        userSectors.remove(userSectorsDelete);


        for (Integer value: input.getSectors()) {
            boolean flag = true;
            for (UserSector userSector: userSectors){
                if (userSector.getSectorValue().equals(value)){
                    flag = false;
                }
            }
            if (flag){
                UserSector us = new UserSector();
                us.setUserId(user.getId());
                us.setSectorValue(value);
//                userSectors.add(us);
                userSectorRepository.save(us);
            }
        }

        return "Done";
    }
}
