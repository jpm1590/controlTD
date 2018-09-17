package com.jpm1590.service;

import java.util.List;

import com.jpm1590.model.ErrorNumber;
import com.jpm1590.model.Interfaz;

public interface ControlVentasService {

	public abstract List<ErrorNumber> getErrorNumbers();
	
	public abstract Interfaz getTitles();
	
	public abstract List<Interfaz> getAllProcess();
	
	public abstract void deleteProperties();
	
	public abstract List<Interfaz> getAllInterfaz(String date);
	
	public abstract List<Interfaz> getErrorsDesc(String date);
	
	public abstract String versionApp();
	
}
