package com.ranger.auth_system;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Auth System application.
 *
 * <p>This class bootstraps the Spring Boot application and loads environment
 * variables from a local .env file during development.</p>
 *
 * <h3>Environment Configuration Strategy:</h3>
 * <ul>
 *     <li>In development: Loads variables from `.env` using dotenv</li>
 *     <li>In production: Uses system environment variables (Docker, AWS, etc.)</li>
 * </ul>
 *
 * <h3>Security Note:</h3>
 * Sensitive credentials (DB, Redis, etc.) should never be hardcoded
 * and must be injected via environment variables.
 */
@SpringBootApplication
public class AuthSystemApplication {

	/**
	 * Main method that initializes environment variables and starts the application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		// Load environment variables from .env file (development only)
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // Allows app to run even if .env is absent (production)
				.load();

		// Set database configuration
		setIfPresent("DB_URL", dotenv.get("DB_URL"));
		setIfPresent("DB_USER", dotenv.get("DB_USER"));
		setIfPresent("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Set Redis configuration
		setIfPresent("REDIS_HOST", dotenv.get("REDIS_HOST"));
		setIfPresent("REDIS_PORT", dotenv.get("REDIS_PORT"));
		setIfPresent("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));

		// Start Spring Boot application
		SpringApplication.run(AuthSystemApplication.class, args);
	}

	/**
	 * Utility method to safely set system properties only if the value exists.
	 *
	 * <p>This prevents overriding existing environment variables in production
	 * and avoids setting null values.</p>
	 *
	 * @param key   system property key
	 * @param value environment variable value
	 */
	private static void setIfPresent(String key, String value) {
		if (value != null && !value.isBlank()) {
			System.setProperty(key, value);
		}
	}
}