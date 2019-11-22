package com.docutools.matheus.footballmanager.dto;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.*;

@Data
public class ValidList<E> implements List<E> {
	@Valid
	@Delegate
	private List<E> list = new ArrayList<>();
}