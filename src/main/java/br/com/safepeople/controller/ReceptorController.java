package br.com.safepeople.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.safepeople.models.Receptor;
import br.com.safepeople.repository.ReceptorRepository;



@CrossOrigin
@RestController
@RequestMapping("v1/")
public class ReceptorController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ReceptorRepository receptorRepository;

	@CrossOrigin
	@GetMapping("protected/receptores/listartodas")
	public List<Receptor> searchAll()

	{
		return receptorRepository.findAll();

	}

	@CrossOrigin
	@GetMapping("protected/receptores/listarporfornecedor/{id_fornecedor}")
	public List<Receptor> listarControladorasPorFornecedor(@PathVariable int id_fornecedor) {
		return receptorRepository.listarPorFornecedor(id_fornecedor);
	}

	@CrossOrigin
	@GetMapping("protected/receptores/listar/{id}")
	public Optional<Receptor> buscarPorId(@PathVariable int id)

	{
		return receptorRepository.findById(id);

	}

	@CrossOrigin
	@PostMapping("protected/receptores/cadastrar")
	public boolean cadastrarControladora(@RequestBody Receptor receptor) {

		receptor.setSenhaweb(encoder.encode("TIt@niwm2014"));
		return receptorRepository.save(receptor) != null;

	}

	@CrossOrigin
	@DeleteMapping(path = "protected/receptores/excluir/{id}")
	public ResponseEntity<Void> excluirControladora(@PathVariable int id) {

		receptorRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}

	@CrossOrigin
	@PutMapping(path = "protected/receptores/atualizar/{id}")
	public ResponseEntity atualizarControladora(@PathVariable("id") int id, @RequestBody Receptor receptor) {

		return receptorRepository.findById(id).map(record -> {

			record.setNumero_serie(receptor.getNumero_serie());
			record.setMarca(receptor.getMarca());
			record.setModelo(receptor.getModelo());
			record.setData_fabricacao(receptor.getData_fabricacao());
			record.setStatus(receptor.getStatus());
			record.setDescricao(receptor.getDescricao());
			record.setLatitude(receptor.getLatitude());
			record.setLongitude(receptor.getLongitude());

		

			// record.setSenhaweb(encoder.encode(controladora.getSenhaweb()));
			record.setFornecedor(receptor.getFornecedor());

			Receptor updated = receptorRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

}
