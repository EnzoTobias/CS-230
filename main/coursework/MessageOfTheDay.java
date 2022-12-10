package coursework;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
/**
 * 
 * @author Enzo Tobias 2117781, Marcus Siziba
 *
 */
public class MessageOfTheDay {

	
	private static String decryptMessage = "";
	
	/**
	 * HTTP request requesting the puzzle.
	 */
	public static void connect() {
		decryptMessage = "";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle"))
				.build();
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body).thenApply(t -> {
					try {
						return parse(t);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return t;
				})
				.join();

	}
	/**
	 * Parsing the puzzle and requesting the message of the day with the parse puzzle solution.
	 * @param responseBody The puzzle to parse.
	 * @return The returned message of the day.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String parse(String responseBody) throws InterruptedException, ExecutionException {
		String data = responseBody;// original
		int charCount = 0;
		boolean backwards = true;
		for (int i = 0; i < data.length(); i++) {

			Character c = data.charAt(i);
			if (backwards == false) {
				c = shiftForward(c, i);
				backwards = true;
			} else {
				c = shiftBackward(c, i);
				backwards = false;
			}

			decryptMessage += c.toString();
			charCount += 1;

		}
		decryptMessage = (charCount + 6) + decryptMessage + "CS-230";
		HttpClient clientTwo = HttpClient.newHttpClient();
		HttpRequest requestTwo = HttpRequest.newBuilder()
				.uri(URI.create(
						"http://cswebcat.swansea.ac.uk/message?solution="
								+ decryptMessage))
				.build();
		decryptMessage = clientTwo.sendAsync(requestTwo, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body).get();
				//.join();

		return decryptMessage;
	}

	public static String returnString() {
		connect();
		return decryptMessage;
	}

	public static Character shiftForward(Character c, int amount) {
		int asciiChar = (int) c;

		for (int i = 0; i <= amount; i++) {
			if (asciiChar == (int) 'Z') {
				asciiChar  = (int) 'A';
			} else {
				asciiChar += 1;
			}
			
		}
		return (char) asciiChar;
	}

	public static Character shiftBackward(Character c, int amount) {
		int asciiChar = (int) c;

		for (int i = 0; i <= amount; i++) {
			if (asciiChar == (int) 'A') {
				asciiChar = (int) 'Z';
			} else {
				asciiChar -= 1;
			}
		}
		return (char) asciiChar;
	}

}
