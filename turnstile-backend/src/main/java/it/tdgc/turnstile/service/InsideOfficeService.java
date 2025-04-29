package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.InsideOfficeDTO;
import it.tdgc.turnstile.dto.InsideOfficeInsertDTO;
import it.tdgc.turnstile.model.InsideOffice;
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
        if(insideOffice.getUser_id() == null || insideOffice.getUser_id() <= 0) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "user_id cannot be null/equal or less then 0!", null);
        }
        if(insideOfficeRepository.findById(insideOffice.getUser_id()).isPresent()) {
            return responseBuilder.buildResponse(HttpStatus.CONFLICT, "the user is already inside the office!", null);
        }

        InsideOffice io = new InsideOffice();
        io.setUser(insideOfficeRepository.findByUserId(insideOffice.getUser_id()).getUser());

        InsideOffice newIo = insideOfficeRepository.save(io);
        InsideOfficeDTO insideOfficeDTO = mapperInterface.toInsideOfficeDTO(newIo);
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", insideOfficeDTO);
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



}
