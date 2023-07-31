package br.com.safepeople.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GetData {


	public GetData() {
	
		
	
	}
	
	
	
	
	public String getHora() {

	    
	    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

	 }
	public String getData() {

		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    
	}
	
	public String getDataHora() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		return sdf.format(new Date());
	}
	
	
}
