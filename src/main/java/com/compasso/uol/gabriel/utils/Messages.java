package com.compasso.uol.gabriel.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.compasso.uol.gabriel.response.Error;

public class Messages {
	public static Error getAuthentication(final String key, final Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("AuthenticationMessages", Locale.getDefault());
		return getMessages(bundle, key, params);
	}

	public static Error getCity(final String key, final Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("CityMessages", Locale.getDefault());
		return getMessages(bundle, key, params);
	}

	public static Error getClient(final String key, final Object... params) {
		ResourceBundle bundle = ResourceBundle.getBundle("ClientMessages", Locale.getDefault());
		return getMessages(bundle, key, params);
	}

	private static Error getMessages(ResourceBundle bundle, final String key, final Object... params) {
		List<String> keys = convert(key);

		String text = bundle.getString(keys.get(0));
		text = MessageFormat.format(text, params);

		String title = bundle.getString(keys.get(1));
		return Error.convert(title, text);
	}

	private static List<String> convert(String key) {
		String text = key.concat(".text");
		String title = key.concat(".title");

		List<String> converted = new ArrayList<String>();
		converted.add(title);
		converted.add(text);

		return converted;
	}
}
