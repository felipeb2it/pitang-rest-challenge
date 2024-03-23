package org.pitang.restchallenge;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SandBox {
	
	
	public SandBox() {
		
		List<Integer> numeros = Arrays.asList(1, 2, 3, 2, 4, 6, 6, 6);
		
		Integer numeroFrequente = numeros.stream()
	            .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
	            .entrySet()
	            .stream()
	            .max(Map.Entry.comparingByValue())
	            .map(Map.Entry::getKey)
	            .orElse(null);
		
		/*
		 * Integer numeroFrequente2 = numeros.stream() .collect(Collectors.groupingBy(i
		 * -> i, Collectors.counting())) .entrySet() .stream()
		 * 
		 * .reduce((n1, n2) -> n1 > n2 ? (n1 % 2 != 0 ? -2 : -1) : (n2 % 2 != 0 ? -1 :
		 * -2)) .count();
		 */
	            
		/*
		 * Versão concorrente:
		 * Integer numeroFrequente = numeros.parallelStream()
		 * .collect(Collectors.groupingByConcurrent(i -> i, Collectors.counting()))
		 * .entrySet() .parallelStream() .max(Map.Entry.comparingByValue())
		 * .map(Map.Entry::getKey) .orElse(null);
		 */
		
		System.out.println(numeroFrequente);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SandBox();
	}

}
