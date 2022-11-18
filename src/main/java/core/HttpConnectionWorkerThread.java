package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
	private Socket socket;

	public HttpConnectionWorkerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			String html = "<html><head><title>Servidor HTTP</title></head><body><h1>Teste</h1></body></html>";

			final String CRLF = "\r\n";

			String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length + CRLF + CRLF
					+ html + CRLF + CRLF;

			outputStream.write(response.getBytes());

			LOGGER.info(" * Processamento da Conexao concluida...");
		} catch (IOException e) {
			LOGGER.error("Problema com a comunicacao!",e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
				}
		}
	}
}