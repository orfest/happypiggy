/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.net.engine.roles.PersonDescription;
import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.ConnectionResponseProto;
import ru.nsu.ccfit.pm.econ.net.protos.ConnectionMessagesProtos.PersonDescriptionProto;

/**
 * @author orfest
 * 
 */
public class PlayerInfoReceiver implements Runnable {

	private Logger logger = LoggerFactory.getLogger(PlayerInfoReceiver.class);
	private Socket socket;
	private IServerCoordinator coordinator;

	/**
	 * @param coordinator
	 * @param socket
	 * 
	 */
	public PlayerInfoReceiver(Socket socket_, IServerCoordinator coordinator_) {
		socket = socket_;
		coordinator = coordinator_;
	}

	@Override
	public void run() {
		boolean toclose = false;
		try {
			logger.info("getting person description");
			PersonDescription playerDescription = buildPersonDescription(socket);
			if (playerDescription == null) {
				toclose = true;
				return;
			}
			String response = coordinator.shouldPlayerBeAdded(playerDescription);
			if (response == null)
				logger.info("player ok");
			else
				logger.info("response = "+response);
			if (response != null) {
				toclose = true;
				sendResponse(socket, false, response);
				return;
			}

			logger.info("sending response");
			sendResponse(socket, true, null);
			logger.info("response sent");
			coordinator.playerConnected(playerDescription, socket);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			if (toclose) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void sendResponse(Socket socket, boolean accepted, String reason) throws IOException {
		ConnectionResponseProto.Builder responseBuilder = ConnectionResponseProto.newBuilder();
		responseBuilder.setIsConnectionAccepted(accepted);
		if (reason == null) reason = "";
		responseBuilder.setReason(reason);
		ConnectionResponseProto response = responseBuilder.build();
		response.writeDelimitedTo(socket.getOutputStream());
	}

	private PersonDescription buildPersonDescription(Socket socket) {
		try {
			PersonDescriptionProto.Builder personBuilder = PersonDescriptionProto.newBuilder();
			personBuilder.mergeDelimitedFrom(socket.getInputStream());
			PersonDescriptionProto person = personBuilder.build();
			PersonDescription player = new PersonDescription(person.getName(), person.getGroup());
			return player;
		} catch (IOException e) {
			return null;
		}
	}
}
