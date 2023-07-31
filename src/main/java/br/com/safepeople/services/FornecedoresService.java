package br.com.safepeople.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.repository.FornecedorRepository;



@Service
public class FornecedoresService {

	 @Autowired
	 FornecedorRepository repository;
	    
	    

	    public Page<Fornecedor> listAllClientes(
	            int page,
	            int size ,
	            String cpf_cnpj) {
	      
	    	PageRequest pageRequest = PageRequest.of(
	                page,
	                size);

	        return repository.listarFornecedoresPaginados(cpf_cnpj, 
	                pageRequest);
	    }


}