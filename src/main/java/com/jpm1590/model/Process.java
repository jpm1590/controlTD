package com.jpm1590.model;

public class Process {

	private String id;
	private String codProcess;
	private String process;

	public Process(String id, String codProcess, String process) {
		super();
		this.id = id;
		this.codProcess = codProcess;
		this.process = process;
	}
	public Process(String codProcess, String process) {
		super();
		this.codProcess = codProcess;
		this.process = process;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodProcess() {
		return codProcess;
	}
	public void setCodProcess(String codProcess) {
		this.codProcess = codProcess;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
}
