package com.snail.webgame.engine.component.scene.protocal.in;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.core.ISceneMgtService;

public class InSceneProcessor extends ProtocolProcessor {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	private ISceneMgtService sceneMgtService;
	
	public void setSceneMgtService(ISceneMgtService sceneMgtService) {
		this.sceneMgtService = sceneMgtService;
	}

	public void execute(Request request, Response response) {

		Message message = request.getMessage();


		InSceneReq req = (InSceneReq) message.getBody();
		GameMessageHead head = (GameMessageHead) message.getHeader();
		head.setMsgType(0xB002);

		int roleId = head.getUserID0();
		
		InSceneResp resp = sceneMgtService.inScene(roleId, req, head.getUserID1());
		message.setBody(resp);
		response.write(message);
		if (logger.isInfoEnabled()) {
			logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_1")
							+ ":result=" + resp.getResult() + ",roleId="
							+ roleId);
		}
		
	}

}
