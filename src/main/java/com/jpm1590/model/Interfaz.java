package com.jpm1590.model;

import com.jpm1590.model.DbInserts;
import com.jpm1590.model.Process;

public class Interfaz {
	
	private Process process;
	private String[] date;
	private String[] interfaz;
	private String[] time0;
	private String[] time;
	private String[] regsCTL;
	private DbInserts[] regsIns;
	private String[] state;
	private String[] description;
	private String imags;

	public Interfaz(String[] date, Process process, String[] interfaz, String[] time0,
			String[] time, String[] regsCTL, DbInserts[] regsIns, String[] state) {
		super();
		this.date = date;
		this.interfaz = interfaz;
		this.process = process;
		this.time0 = time0;
		this.time = time;
		this.regsCTL = regsCTL;
		this.regsIns = regsIns;
		this.state = state;
	}
	
	public Interfaz(String[] date, Process process, String[] interfaz, String[] time0,
			String[] time, String[] regsCTL, String[] state) {
		super();
		this.date = date;
		this.process = process;
		this.interfaz = interfaz;
		this.time0 = time0;
		this.time = time;
		this.regsCTL = regsCTL;
		this.state = state;
	}

	public Interfaz() {
		super();
		this.date = null;
		this.interfaz = null;
		this.process = null;
		this.time0 = null;
		this.time = null;
		this.regsCTL = null;
		this.regsIns = null;
		this.state = null;
	}
	
	public Interfaz(Process process) {
		super();
		this.date = null;
		this.interfaz = null;
		this.process = process;
		this.time0 = null;
		this.time = null;
		this.regsCTL = null;
		this.regsIns = null;
		this.state = null;
	}

	public String[] getDate() {
		return date;
	}

	public void setDate(String[] date) {
		this.date = date;
	}

	public String[] getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(String[] interfaz) {
		this.interfaz = interfaz;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public String[] getTime0() {
		return time0;
	}

	public void setTime0(String[] time0) {
		this.time0 = time0;
	}

	public String[] getTime() {
		return time;
	}

	public void setTime(String[] time) {
		this.time = time;
	}

	public String[] getRegsCTL() {
		return regsCTL;
	}

	public void setRegsCTL(String[] regsCTL) {
		this.regsCTL = regsCTL;
	}

	public DbInserts[] getRegsIns() {
		return regsIns;
	}

	public void setRegsIns(DbInserts[] regsIns) {
		this.regsIns = regsIns;
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String getImags() {
		return imags;
	}

	public void setImags(String imags) {
		this.imags = imags;
	}
	
	
}
