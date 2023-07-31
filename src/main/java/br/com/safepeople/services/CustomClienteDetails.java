package br.com.safepeople.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.safepeople.models.Cliente;


public class CustomClienteDetails implements UserDetails {
	private final Optional<Cliente> cliente;

	public CustomClienteDetails(Optional<Cliente> cliente) {
		this.cliente = cliente;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	public Integer getId() {
		return Integer.valueOf(
				((Cliente) this.cliente.orElse(new Cliente())).getId_cliente());
	}

	public String getPassword() {
		return ((Cliente) this.cliente.orElse(new Cliente())).getSenhaweb();
	}

	public String getUsername() {
		
		if( ((Cliente) this.cliente.orElse(new Cliente())).getTipo_cliente() == 0     )
		 return ((Cliente) this.cliente.orElse(new Cliente())).getCpf();
		else 
			return ((Cliente) this.cliente.orElse(new Cliente())).getCnpj();

	}
	
	
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
			return true;
	
	}
}
