package br.com.gabrielviannasr.gathering.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

public class AbstractService<T> {

	Example<T> getExample(T model) {
		return Example.of(model, ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
	}

}
