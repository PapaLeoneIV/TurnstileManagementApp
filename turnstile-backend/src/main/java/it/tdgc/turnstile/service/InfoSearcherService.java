package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.UserDTO;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.UsersRepository;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoSearcherService {
    final private InsideOfficeService insideOfficeService;
    private final UsersRepository usersRepository;
    private final MapperInterface mapperInterface;

    public InfoSearcherService(InsideOfficeService insideOfficeService,
                               UsersRepository usersRepository,
                               MapperInterface mapperInterface)
    {
        this.insideOfficeService = insideOfficeService;
        this.usersRepository = usersRepository;
        this.mapperInterface = mapperInterface;
    }


    @Transactional
    public  List<UserDTO> getAllUsersInsideOffice(){
        List<Integer> ids = insideOfficeService.getAllIdsInsideOffice();
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<Users> usersList = new ArrayList<>();
        for (Integer id : ids) {
            Users user = usersRepository.findUsersById(id);
            if (user == null) {
                continue;
            }
            usersList.add(user);
        }

        return usersList.stream().map(mapperInterface::toUserDTO).toList();
    }

    @Transactional
    public List<UserDTO> getPeopleInsideOffice(String userType) {
        List<Integer> ids = insideOfficeService.getAllIdsInsideOffice();
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<Users> usersList = new ArrayList<>();
        for (Integer id : ids) {
            Users user = usersRepository.findUsersByIdAndUserType(id, userType);
            if (user == null) {
                continue;
            }
            usersList.add(user);
        }

        return usersList.stream().map(mapperInterface::toUserDTO).toList();
    }


    @Transactional
    public List<UserDTO> getByRole(String role, String userType){
        List<Users> users = usersRepository.findByRole(role, userType);
        if(users.isEmpty()){
            return null;
        }
        return users.stream().map(mapperInterface::toUserDTO).toList();

    }

    @Transactional
    public List<UserDTO> getByCompany(String name, String userType){
        List<Users> users = usersRepository.findByCompany(name, userType);
        if(users.isEmpty()){
            return null;
        }
        return users.stream().map(mapperInterface::toUserDTO).toList();

    }
}
