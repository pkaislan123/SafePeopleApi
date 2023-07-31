package br.com.safepeople.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.safepeople.models.Cliente;
import br.com.safepeople.repository.ClienteRepository;




@Service
public class ClientesService {

	 @Autowired
	 ClienteRepository repository;
	    
	 

	 
	  public Page<Cliente> listAllClientesPorFornecedor(
	            int page,
	            int size ,
	            String cpf_cnpj, int fornecedor_id) {
	      
	    	PageRequest pageRequest = PageRequest.of(
	                page,
	                size);

	        return repository.listarClientesPaginadosPorFornecedor(cpf_cnpj, fornecedor_id,
	                pageRequest);
	    }

	 
	    public Page<Cliente> listAllClientes(
	            int page,
	            int size ,
	            String cpf_cnpj) {
	      
	    	PageRequest pageRequest = PageRequest.of(
	                page,
	                size);

	        return repository.listarClientesPaginados(cpf_cnpj, 
	                pageRequest);
	    }


}
