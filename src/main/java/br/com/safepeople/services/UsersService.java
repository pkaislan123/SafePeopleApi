package br.com.safepeople.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.models.User;
import br.com.safepeople.repository.FornecedorRepository;
import br.com.safepeople.repository.UserRepository;

@Service
public class UsersService {

	 @Autowired
	 UserRepository repository;
	    
	    

	    public Page<User> listAllClientes(
	            int page,
	            int size ,
	            String cpf) {
	      
	    	PageRequest pageRequest = PageRequest.of(
	                page,
	                size);

	        return repository.listarUsuariosPaginados(cpf, 
	                pageRequest);
	    }

	
}
