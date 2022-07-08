package it.project.invoice.service;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.project.invoice.dto.CittaDTO;
import it.project.invoice.model.Citta;
import it.project.invoice.repository.CittaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CittaService {

	@Autowired
	CittaRepository cittaRepo;

	public Citta findByNome(String nome) {
		if(cittaRepo.existsByNome(nome)) {
			return cittaRepo.findByNome(nome);
		} else {
			return null;
		}
	}

	public Citta associaCitta(String nomeCitta) throws Exception {
		if (cittaRepo.existsByNome(nomeCitta)) {
			Citta citta = cittaRepo.findByNome(nomeCitta);
			return citta;
		} else {
			throw new Exception("la Citta " + nomeCitta + " non è presente nel sistema");
		}
	}

	public void aggiungiCitta(CittaDTO dto) throws Exception {
		Citta citta = new Citta();
		if(!cittaRepo.existsByNome(dto.getNome())){
			BeanUtils.copyProperties(dto, citta);
			cittaRepo.save(citta);
			log.info("Citta "+ dto.getNome() +" salvato");
		} else {
			throw new Exception("Citta "+dto.getNome()+" già presente nel sistema");
		}
	}

	public void modificaCitta(CittaDTO dto) throws Exception {
		if(cittaRepo.existsByNome(dto.getNome())){
			Citta citta = cittaRepo.findByNome(dto.getNome());
			BeanUtils.copyProperties(dto, citta);
			cittaRepo.save(citta);
			log.info("Citta "+ dto.getNome() +" modificata");
		} else {
			throw new Exception("Citta "+ dto.getNome() + " non presente nel sistema");
		}
	}

	public void eliminaCitta(String nome) throws Exception {
		if(cittaRepo.existsByNome(nome)) {
			cittaRepo.deleteByNome(nome);
		} else {
			throw new Exception("Citta "+ nome + " non presente nel sistema");
		}
	}



}
