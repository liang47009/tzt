package com.snail.webgame.engine.component.scene.protocal.fight.delphalanx;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class DelPhalanxResp extends MessageBody {

	private long fightId;
	private String phalanxId;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("fightId", 0);
		ps.addString("phalanxId", "flashCode", 0);
	}
	public long getFightId() {
		return fightId;
	}
	public void setFightId(long fightId) {
		this.fightId = fightId;
	}
	public String getPhalanxId() {
		return phalanxId;
	}
	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

}
