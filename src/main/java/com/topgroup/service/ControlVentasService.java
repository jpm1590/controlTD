package com.topgroup.service;

import java.util.List;

import com.topgroup.model.ErrorNumber;
import com.topgroup.model.Interfaz;

public interface ControlVentasService {

	public abstract List<ErrorNumber> getErrorNumbers();
	
	public abstract Interfaz getTitles();
	
	public abstract List<Interfaz> getAllProcess();
	
	public abstract List<Interfaz> getAllProcessEasy();
	
	public abstract List<Interfaz> getAllInterfaz(String date, int idTabla);
	
	public abstract List<Interfaz> getErrorsDesc(String date, int idTabla);
	
	public abstract String versionApp();
	
}
