package com.topgroup.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.topgroup.model.DbInserts;
import com.topgroup.model.ErrorNumber;
import com.topgroup.model.Interfaz;
import com.topgroup.model.Process;
import com.topgroup.service.ControlVentasService;

@Service("controlVentasServiceImp")
public class ControlVentasServiceImpl implements ControlVentasService {

	@Value("${controlTD.process.uri}")
	private String processURI;

	private static final Log LOG = LogFactory.getLog(ControlVentasServiceImpl.class);

	@Override
	public List<ErrorNumber> getErrorNumbers() {

		URL errno = this.getClass().getClassLoader().getResource("static/data/errno");
		List<ErrorNumber> errnos = new ArrayList<>();
		try {

			BufferedReader ent = new BufferedReader(new FileReader(new File(errno.getFile())));

			String linea = ent.readLine();

			while (linea != null) {

				String[] str = linea.split(",");
				errnos.add(new ErrorNumber(str[0], str[1]));

				linea = ent.readLine();
			}
			ent.close();
		} catch (IOException ioe) {
			LOG.debug(" - " + ioe);
		}

		return errnos;
	}

	@Override
	public Interfaz getTitles() {

		String[] date = { "Fecha" };
		String[] interfaz = { "Interfaz" };
		Process process = new Process("#", "Cod Proceso", "Proceso");
		String[] time0 = { "Hora Inicio" };
		String[] time = { "Hora Fin" };
		String[] regsCTL = { "Reg CTL" };
		DbInserts[] regsIns = { new DbInserts("Registros", "") };
		String[] state = { "Estado" };

		Interfaz titles = new Interfaz(date, process, interfaz, time0, time, regsCTL, regsIns, state);

		return titles;
	}

	@Override
	public List<Interfaz> getAllProcess() {

		URL process = this.getClass().getClassLoader().getResource("static/data/process");
		List<Interfaz> processes = new ArrayList<>();
		try {

			BufferedReader ent = new BufferedReader(new FileReader(new File(process.getFile())));

			String linea = ent.readLine();

			while (linea != null) {

				String[] str = linea.split(",");
				processes.add(new Interfaz(new Process(str[0], str[1], str[2])));

				linea = ent.readLine();
			}
			ent.close();
		} catch (IOException ioe) {
			LOG.debug(" - " + ioe);
		}

		return processes;
	}

	@Override
	public List<Interfaz> getAllProcessEasy() {

		URL process = this.getClass().getClassLoader().getResource("static/data/processEasy");
		List<Interfaz> processes = new ArrayList<>();
		try {

			BufferedReader ent = new BufferedReader(new FileReader(new File(process.getFile())));

			String linea = ent.readLine();

			while (linea != null) {

				String[] str = linea.split(",");
				processes.add(new Interfaz(new Process(str[0], str[1], str[2])));

				linea = ent.readLine();
			}
			ent.close();
		} catch (IOException ioe) {
			LOG.debug(" - " + ioe);
		}

		return processes;
	}

	@Override
	public List<Interfaz> getAllInterfaz(String date1, int idTabla) {

		URL logs = this.getClass().getClassLoader().getResource("logs");
		List<Interfaz> processes, processesEasy = new ArrayList<>();
		processes = getAllProcess();
		processesEasy = getAllProcessEasy();

		List<Interfaz> processFile = new ArrayList<>();

		String[] date;
		String[] interfaz;
		Process process;
		String[] time0;
		String[] time;
		String[] regsCTL;
		DbInserts[] regsIns;
		String[] state;
		// *
		try {

			BufferedReader ent = new BufferedReader(
					new FileReader(new File(logs.getFile()) + "/log_" + date1 + ".txt"));
			String linea = ent.readLine();

			while (linea != null) {

				String[] str = linea.split(",");

				date = str[0].split(" ");
				String[] strProcess = str[1].split("/");
				process = new Process(strProcess[0], strProcess[1]);
				interfaz = str[2].split(" ");
				time0 = str[3].split(" ");
				time = str[4].split(" ");
				regsCTL = str[5].split(" ");
				String[] strRegsIns = str[7].split(" ");
				regsIns = new DbInserts[strRegsIns.length];
				for (int i = 0; i < strRegsIns.length; i++) {
					String[] strRegsInsProps = strRegsIns[i].split("/");
					regsIns[i] = new DbInserts(strRegsInsProps[0], strRegsInsProps[1]);
				}
				state = str[6].split(" ");

				processFile.add(new Interfaz(date, process, interfaz, time0, time, regsCTL, regsIns, state));

				linea = ent.readLine();
			}
			ent.close();
		} catch (IOException ioe) {
			LOG.debug(" - " + ioe);
		}

		for (Interfaz data1 : processFile) {
			int i = 0;
			switch (data1.getProcess().getCodProcess()) {
			case "P0605":
				i = 1;
				break;
			case "P0600":
			case "P0805":
				i = 2;
				break;
			case "P0400":
			case "P0401":
			case "P0402":
				i = 3;
				break;
			default:
				i = 0;
				break;
			}

			for (Interfaz process2 : processesEasy) {
				if (process2.getProcess().getCodProcess().equals(data1.getProcess().getCodProcess())) {
					switch (data1.getProcess().getCodProcess()) {
					case "P0605":
						if (data1.getInterfaz()[0].split("_")[i].equals("EASY")) {
							getInterfazProps(process2, data1);
						}
						break;
					case "P0600":
					case "P0805":
					case "P0401":
					case "P0402":
						if (data1.getInterfaz()[0].split("_")[i].charAt(0) > 64
								&& data1.getInterfaz()[0].split("_")[i].charAt(0) < 91) {
							getInterfazProps(process2, data1);
						}
						break;
					case "P0400":
						if (data1.getInterfaz()[0].split("_")[i].charAt(0) > 64
								&& data1.getInterfaz()[0].split("_")[i].charAt(0) < 91
								&& !data1.getInterfaz()[0].split("_")[i].equals("POS")) {
							getInterfazProps(process2, data1);
						}
						break;
					}
				}
			}

			for (Interfaz process1 : processes) {
				if (process1.getProcess().getCodProcess().equals(data1.getProcess().getCodProcess())) {
					switch (data1.getProcess().getCodProcess()) {
					case "P0605":
						if (data1.getInterfaz()[0].split("_")[i].equals("GEN")) {
							getInterfazProps(process1, data1);
						}
						break;
					case "P0600":
					case "P0805":
					case "P0401":
					case "P0402":
						if (data1.getInterfaz()[0].split("_")[i].charAt(0) > 47
								&& data1.getInterfaz()[0].split("_")[i].charAt(0) < 58) {
							getInterfazProps(process1, data1);
						}
						break;
					case "P0400":
						if (Integer.parseInt(data1.getDate()[0].split("-")[0]) < 2018
								|| (Integer.parseInt(data1.getDate()[0].split("-")[0]) == 2018
										&& Integer.parseInt(data1.getDate()[0].split("-")[1]) < 10)
								|| (Integer.parseInt(data1.getDate()[0].split("-")[0]) == 2018
										&& Integer.parseInt(data1.getDate()[0].split("-")[1]) == 10
										&& Integer.parseInt(data1.getDate()[0].split("-")[2]) <= 11)
						) {
							if (data1.getInterfaz()[0].split("_")[i].charAt(0) > 47
									&& data1.getInterfaz()[0].split("_")[i].charAt(0) < 58) {
								getInterfazProps(process1, data1);
							}
						} else {
							if (data1.getInterfaz()[0].split("_")[i].equals("POS")) {
								getInterfazProps(process1, data1);
							}
						}
						break;
					default:
						getInterfazProps(process1, data1);
						break;
					}
				}
			}
		}

		return idTabla == 1 ? processes : processesEasy;
	}

	@Override
	public List<Interfaz> getErrorsDesc(String date, int idTabla) {
		List<Interfaz> processes = new ArrayList<>();
		processes = getAllInterfaz(date, idTabla);

		for (Interfaz process1 : processes) {
			if (process1.getState() != null) {
				for (String interf : process1.getState()) {
					for (ErrorNumber error : getErrorNumbers()) {
						if (interf.equals(error.getErrNumber())) {
							String[] errDesc = { error.getDescription() };
							process1.setDescription(errDesc);
						}
					}
				}
			} else {
				String[] errState = { "-1" };
				String[] errDesc = { "Esperando interfaz" };
				process1.setState(errState);
				process1.setDescription(errDesc);
				process1.setImags("../imgs/wait.png");
			}
		}

		return processes;
	}

	@Override
	public String versionApp() {

		final Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException ioe) {
			LOG.info(ioe);
		}
		return properties.getProperty("version");
	}

	public void getInterfazProps(Interfaz interfaz, Interfaz interfazFile) {

		interfaz.setDate(interfazFile.getDate());
		interfaz.setTime0(interfazFile.getTime0());
		interfaz.setTime(interfazFile.getTime());
		interfaz.setInterfaz(interfazFile.getInterfaz());
		interfaz.setRegsCTL(interfazFile.getRegsCTL());
		interfaz.setRegsIns(interfazFile.getRegsIns());
		interfaz.setState(interfazFile.getState());

		int val = 0;
		for (String state1 : interfaz.getState()) {
			if (Integer.parseInt(state1) == 0) {
				interfaz.setImags("../imgs/ok.png");
			} else if (Integer.parseInt(state1) > 0) { // val = 1;
				interfaz.setImags("../imgs/error1.png");
			} else if (Integer.parseInt(state1) == -2) {
				interfaz.setImags("../imgs/processing.png");
			} else {
				interfaz.setImags("../imgs/wait.png");
			}
		}

		if (val == 1) {
			interfaz.setImags("../imgs/error1.png");
		}

	}// */
}
