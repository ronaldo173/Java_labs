package ru.javalabs.lab2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {

	public static void main(String[] args) {

		// have to be valid
		String uRLForCheck = "http://www.example.com";
		String uRLForCheck2 = "http://example.com";
		String uRLForCheck3 = "example.com";
		String uRLForCheck4 = "https://example.com";

		// invalid
		String uRLForCheck5 = "just text";
		String uRLForCheck6 = "http://a.com";
		String uRLForCheck7 = "b.com";

		Map<String, Boolean> mapOfCheckingValues = new LinkedHashMap<>();
		mapOfCheckingValues.put(uRLForCheck, isValidURL(uRLForCheck));
		mapOfCheckingValues.put(uRLForCheck2, isValidURL(uRLForCheck2));
		mapOfCheckingValues.put(uRLForCheck3, isValidURL(uRLForCheck3));
		mapOfCheckingValues.put(uRLForCheck4, isValidURL(uRLForCheck4));
		mapOfCheckingValues.put(uRLForCheck5, isValidURL(uRLForCheck5));
		mapOfCheckingValues.put(uRLForCheck6, isValidURL(uRLForCheck6));
		mapOfCheckingValues.put(uRLForCheck7, isValidURL(uRLForCheck7));

		for (Entry<String, Boolean> entry : mapOfCheckingValues.entrySet()) {
			System.out.println(entry.getKey() + "  ---> " + entry.getValue());
		}
	}

	public static boolean isValidURL(String URLForCheck) {
		String regex = "((https?|http)://)?[a-zA-Z0-9:]{2,}(\\.\\w[a-zA-Z0-9:]+)+(/[#&\\n=?\\+\\%/\\.\\w]+)?";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(URLForCheck);

		return matcher.matches();
	}
}