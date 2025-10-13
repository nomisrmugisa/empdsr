package org.pdsr.json;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.pdsr.summary.model.big_case_identifiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedCaseIdentifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private big_case_identifiers selected;
	private List<big_case_identifiers> data;

	public DecryptedCaseIdentifiers() {
	}
	
	public DecryptedCaseIdentifiers(big_case_identifiers selected) {
		this.selected = selected;
	}

	public DecryptedCaseIdentifiers(List<big_case_identifiers> data) {
		super();
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public big_case_identifiers getSelected() {
		return selected;
	}

	public void setSelected(big_case_identifiers selected) {
		this.selected = selected;
	}

	public List<big_case_identifiers> getData() {
		return data;
	}

	public void setData(List<big_case_identifiers> data) {
		this.data = data;
	}

	public String encrypt(final String KEY, final String ISS, final String AUD) {


		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		Key hmacKey;
		try {
			hmacKey = new SecretKeySpec(KEY.getBytes("UTF-8"), SignatureAlgorithm.HS256.getJcaName());
		} catch (UnsupportedEncodingException e) {
			hmacKey = null;
			e.printStackTrace();
		}

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder()
				.setIssuer(ISS)
				.setAudience(AUD)
				.setHeaderParam("typ", "JWT")
				.claim("data", selected)
				.signWith(signatureAlgorithm, hmacKey);

		// Builds the JWT and serializes it to a compact, URL-safe string

		final String jwt = builder.compact();

		return jwt;

	}

	public String encryptList(final String KEY, final String ISS, final String AUD) {


		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		Key hmacKey;
		try {
			hmacKey = new SecretKeySpec(KEY.getBytes("UTF-8"), SignatureAlgorithm.HS256.getJcaName());
		} catch (UnsupportedEncodingException e) {
			hmacKey = null;
			e.printStackTrace();
		}

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder()
				.setIssuer(ISS)
				.setAudience(AUD)
				.setHeaderParam("typ", "JWT")
				.claim("data", data)
				.signWith(signatureAlgorithm, hmacKey);

		// Builds the JWT and serializes it to a compact, URL-safe string

		final String jwt = builder.compact();

		return jwt;

	}

}
