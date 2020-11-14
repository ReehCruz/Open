package br.com.deveficiente.apicdc.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Optional;

import br.com.deveficiente.apicdc.controller.form.NovaCategoriaForm;
import br.com.deveficiente.apicdc.model.Categoria;
import br.com.deveficiente.apicdc.repository.CategoriaRepository;

public class SemCategoriaComNomeDuplicadoValidator implements Validator {

	private CategoriaRepository categoriaRepository;
	
	public SemCategoriaComNomeDuplicadoValidator(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return NovaCategoriaForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NovaCategoriaForm form = (NovaCategoriaForm) target;
		Optional<Categoria> possivelCategoria = categoriaRepository.findByNome(form.getNome());
		
		if (possivelCategoria.isPresent()) {
			errors.rejectValue("nome", null, "Já existe uma categoria com esse nome");
			
		}
	}

}
