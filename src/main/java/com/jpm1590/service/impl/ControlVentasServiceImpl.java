package com.jpm1590.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.jpm1590.model.DbInserts;
import com.jpm1590.model.ErrorNumber;
import com.jpm1590.model.Interfaz;
import com.jpm1590.model.Process;
import com.jpm1590.service.ControlVentasService;

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
	public void deleteProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Interfaz> getAllInterfaz(String date1) {

		URL logs = this.getClass().getClassLoader().getResource("logs");
		List<Interfaz> processes = new ArrayList<>();
		processes = getAllProcess();

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

		for (Interfaz process1 : processes) {
			for (Interfaz data1 : processFile) {
				if (process1.getProcess().getCodProcess().equals(data1.getProcess().getCodProcess())) {
					process1.setDate(data1.getDate());
					process1.setTime0(data1.getTime0());
					process1.setTime(data1.getTime());
					process1.setInterfaz(data1.getInterfaz());
					process1.setRegsCTL(data1.getRegsCTL());
					process1.setRegsIns(data1.getRegsIns());
					process1.setState(data1.getState());
					// *
					int val = 0;
					for (String state1 : process1.getState()) {
						if (Integer.parseInt(state1) == 0) {
							process1.setImags("../imgs/ok.png");
						} else if (Integer.parseInt(state1) > 0) {
							val = 1;
						} else {
							process1.setImags("../imgs/wait.png");
						}
					}

					if (val == 1) {
						process1.setImags("../imgs/error.png");
					} // */

				}
			}
		}

		return processes;
	}

	@Override
	public List<Interfaz> getErrorsDesc(String date) {
		List<Interfaz> processes = new ArrayList<>();
		processes = getAllInterfaz(date);

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
				String[] errState = { "-" };
				String[] errDesc = { "A la espera de la interfaz o de la finalizacion" };
				process1.setState(errState);
				process1.setDescription(errDesc);
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

	public void myFunction(String i) throws Exception {
		/*
		 * var popup = document.getElementById("myPopup"+i);
		 * popup.classList.toggle("show");
		 * 
		 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 * Document doc = factory.newDocumentBuilder().parse( new InputSource(new
		 * StringReader(""))); Element name = doc.getElementById("myPopup" + i);
		 */
	}

}
