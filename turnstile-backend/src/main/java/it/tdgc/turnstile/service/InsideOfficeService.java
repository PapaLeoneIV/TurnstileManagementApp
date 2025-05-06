package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.dto.InsideOfficeInsertDTO;
import it.tdgc.turnstile.dto.TurnstileDTO;
import it.tdgc.turnstile.model.InsideOffice;
import it.tdgc.turnstile.model.Turnstile;
import it.tdgc.turnstile.model.Users;
import it.tdgc.turnstile.repository.InsideOfficeRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
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
    public ResponseEntity<ApiResponse<InsideOfficeDTO>> insertInsideOffice(InsideOfficeInsertDTO insideOffice) {
        if(insideOffice.getUser_id() == null || insideOffice.getUser_id() < 0) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "user_id cannot be null/equal or less then 0!", null);
        }
        if(insideOfficeRepository.findById(insideOffice.getUser_id()).isPresent()) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "the user is already inside the office!", null);
        }

        InsideOffice io = new InsideOffice();
        io.setUser(io.getUser());
        InsideOffice newIo = insideOfficeRepository.save(io);
        InsideOfficeDTO insideOfficeDTO = mapperInterface.toInsideOfficeDTO(newIo);
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", insideOfficeDTO);
    }

    @Transactional
    public boolean isInsideOffice(Integer id){
        Optional<InsideOffice> IO = insideOfficeRepository.findByUserId(id);

        return IO.isPresent();
    }

    @Transactional
    public void addUser(Users user) {
        insideOfficeRepository.save(new InsideOffice(user));
    }
    @Transactional
    public void removeUser(Users user){
        Optional<InsideOffice> u = insideOfficeRepository.findByUserId(user.getId());
        u.ifPresent(users -> insideOfficeRepository.deleteById(users.getId()));
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


    public ResponseEntity<ApiResponse<InsideOfficeDTO>> searchById(Integer id) {
        if(id < 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<InsideOffice> IO = insideOfficeRepository.findById(id);
        if(IO.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "The ID is not present in the DB!", null);
        }
        InsideOfficeDTO IODTO = mapperInterface.toInsideOfficeDTO(IO.get());
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", IODTO);
    }

    public ResponseEntity<ApiResponse<InsideOfficeDTO>> deleteById(Integer id) {
        if(id == null || id <= 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "ID cannot be lower than 0!", null);
        }
        Optional<InsideOffice> io = insideOfficeRepository.findById(id);
        if (io.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "InsideOffice ID not found", null);
        }
        insideOfficeRepository.deleteById(id);
        InsideOfficeDTO turnstileDTO = mapperInterface.toInsideOfficeDTO(io.get());

        return responseBuilder.buildResponse(HttpStatus.OK, "InsideOffice successfully deleted", turnstileDTO);
    }
}
