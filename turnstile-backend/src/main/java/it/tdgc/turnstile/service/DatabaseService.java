package it.tdgc.turnstile.service;
import it.tdgc.turnstile.repository.DatabaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    private final DatabaseRepository databaseRepository;


    @Autowired
    public DatabaseService(DatabaseRepository databaseRepository){
        this.databaseRepository = databaseRepository;
    }

    @Transactional
    public List<String> getTableNames(){
        return databaseRepository.getTableNames();
    }

}
