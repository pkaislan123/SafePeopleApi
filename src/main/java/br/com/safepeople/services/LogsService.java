package br.com.safepeople.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.safepeople.models.Cliente;
import br.com.safepeople.models.Log;
import br.com.safepeople.repository.LogRepository;

@Service
public class LogsService {

	@Autowired
	LogRepository logRepository;
	
	 public Page<Log> listAllLogs(
	            int page,
	            int size ) {
	      
	    	PageRequest pageRequest = PageRequest.of(
	                page,
	                size);

	        return logRepository.listarLogsPaginados(
	                pageRequest);
	    }

}
