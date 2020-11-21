package br.com.deveficiente.apicdc.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.deveficiente.apicdc.config.Cookies;
import br.com.deveficiente.apicdc.controller.dto.LivroDetalheDTO;
import br.com.deveficiente.apicdc.model.Carrinho;
import br.com.deveficiente.apicdc.model.Livro;
import br.com.deveficiente.apicdc.repository.LivroRepository;

@RestController
public class DetalheLivroController {
	
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private Cookies cookies;

	@GetMapping(value = "/api/livro/{id}")
	public LivroDetalheDTO exibeDetalhes(@PathVariable("id") Long id) {
		
		Livro livro = livroRepository.findById(id).get();
		return new LivroDetalheDTO(livro);
	}
	
	@PostMapping(value = "/api/carrinho/{idLivro}")
	public String adicionaLivroCarrinho(@PathVariable("idLivro") Long idLivro,@CookieValue("carrinho") Optional<String> jsonCarrinho,HttpServletResponse response) throws JsonProcessingException {
		Carrinho carrinho = Carrinho.cria(jsonCarrinho);
		
		carrinho.adiciona(livroRepository.findById(idLivro).get());
		
		cookies.writeAsJson("carrinho",carrinho,response);
		
		return carrinho.toString();
	}
	
}
