package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;



@Controller
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@Autowired
	private FileSaver filesaver;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(new ProdutoValidation());
	}
		
	
	@RequestMapping("/produtos/form")
	public ModelAndView form(Produto produto){
		
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		
		return modelAndView;
	}
	
	@RequestMapping(value="/produtos", method=RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes){
		
		
		
		System.out.println(sumario.getOriginalFilename());
		
		
		if(result.hasErrors()){
			ModelAndView modelAndView = new ModelAndView("produtos/form");
			modelAndView.addObject("tipos", TipoPreco.values());
			return modelAndView;
		}
		
		String path = filesaver.write("arquivos-sumarios", sumario);
		produto.setSumarioPath(path);
		
		dao.gravar(produto);

		redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!"); 
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(value="/produtos", method=RequestMethod.GET)
	public ModelAndView listar(){
		List<Produto> produtos = dao.listar();
		ModelAndView modelAndView = new ModelAndView("/produtos/lista");
		modelAndView.addObject("produtos", produtos); 
		return modelAndView;
		}


	@RequestMapping("/produtos/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = dao.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	@RequestMapping("/produtos/testeDao/{id}")
	public ModelAndView testeDAO(Integer id){
		ModelAndView modelAndView = new ModelAndView("produtos/testeDao");
		Produto produto = dao.find(id);
		modelAndView.addObject("produto", produto);
		return modelAndView;
	}
	
	
}
