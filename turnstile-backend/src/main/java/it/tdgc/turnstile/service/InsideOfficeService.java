package it.tdgc.turnstile.service;

import it.tdgc.turnstile.model.InsideOffice;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.InsideOfficeRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InsideOfficeService {
    private final InsideOfficeRepository insideOfficeRepository;
    private final MapperInterface mapperInterface;

    public InsideOfficeService(InsideOfficeRepository insideOfficeRepository, MapperInterface mapperInterface) {
        this.insideOfficeRepository = insideOfficeRepository;
        this.mapperInterface = mapperInterface;
    }

    @Transactional
    public boolean isInsideOffice(Integer id){
        InsideOffice IO = insideOfficeRepository.findByUserId(id);
        return IO != null;
    }

    @Transactional
    public void addUser(Users user) {
        insideOfficeRepository.save(new InsideOffice(user));
    }
    @Transactional
    public void removeUser(Users user){
        insideOfficeRepository.delete(insideOfficeRepository.findByUserId(user.getId()));
    }


    @Transactional
    public List<Integer> getAllIdsInsideOffice() {
        List<InsideOffice> IO = insideOfficeRepository.findAll();
        if(IO.isEmpty()){
            return new ArrayList<>();  // Return an empty list instead of null
        }
        List<Integer> ids = new ArrayList<>();
        for (InsideOffice i : IO) {
            ids.add(i.getUser().getId());
        }
        return ids;
    }


//    @Transactional
//    public ResponseEntity<ApiResponse<InsideOfficeDTO>> getNumberOfUsersInsideOffice(){
//        Integer numOfUsers = insideOfficeRepository.getTotNumOfUsers();
//
//        InsideOfficeDTO insideOfficeDTO = mapperInterface.toInsideOfficeDTONumOfUsers(numOfUsers);
//
//        return buildResponse(HttpStatus.OK, "OK", insideOfficeDTO);
//    }

//    @Transactional
//    public Employee isEmployeeInsideOffice(Employee employee){
//        Integer id = employee.getUser().getId();
//        Optional<InsideOffice> IO = insideOfficeRepository.findByUserId(id);
//        if(IO.isEmpty()){
//            return null;
//        }
//        return employee;
//    }
//
//
//    @Transactional
//    public Visitor isVisitorInsideOffice(Visitor visitor){
//        Integer id = visitor.getUser().getId();
//        Optional<InsideOffice> IO = insideOfficeRepository.findByUserId(id);
//        if(IO.isEmpty()){
//            return null;
//        }
//        return visitor;
//    }

    private <T> ResponseEntity<ApiResponse<T>> buildResponse(HttpStatus status, String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(
                String.valueOf(status.value()),
                message,
                data,
                new Date(),
                null
        );
        return ResponseEntity.status(status).body(response);
    }
}
