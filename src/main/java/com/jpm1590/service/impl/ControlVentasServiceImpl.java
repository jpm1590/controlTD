package com.jpm1590.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jpm1590.model.DbInserts;
import com.jpm1590.model.ErrorNumber;
import com.jpm1590.model.Interfaz;
import com.jpm1590.model.Process;
import com.jpm1590.service.ControlVentasService;

@Service("controlVentasServiceImp")
public class ControlVentasServiceImpl implements ControlVentasService {

	private static final Log LOG = LogFactory.getLog(ControlVentasServiceImpl.class);

	@Override
	public List<ErrorNumber> getErrorNumbers() {

		List<ErrorNumber> errno = new ArrayList<>();
		try {
			BufferedReader ent = new BufferedReader(new FileReader("src/main/resources/static/data/errno"));
			String linea = ent.readLine();

			while (linea != null) {

				String[] str = linea.split(",");
				errno.add(new ErrorNumber(str[0], str[1]));

				linea = ent.readLine();
			}
			ent.close();
		} catch (IOException ioe) {
			LOG.debug(" - " + ioe);
		}

		return errno;
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

		List<Interfaz> processes = new ArrayList<>();
		try {

			BufferedReader ent = new BufferedReader(new FileReader("src/main/resources/static/data/process"));

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
					new FileReader("src/main/resources/logs/log_" + date1 + ".json"));
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
	public String directorio() {
		return System.getProperty("user.dir");
	}
}
