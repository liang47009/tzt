package com.snail.webgame.engine.component.scene.core.policy;

import java.util.List;

import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.core.FightCalculate;
import com.snail.webgame.engine.component.scene.core.FightCmdSend;
/**
 * 自动攻击攻击范围内的目标，不在目标范围内则进行移动，追击指定时间后切换攻击目标,移动过程中不能攻击
 */
public class PursueAttackPolicyListener implements PolicyListener {

	public void execute(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList, int errorTime) {
		int controlType = roleFight.getControlMap().get(armyPhalanx.getRoleId());
		GameMap gameMap = roleFight.getMap();
		FightCalculate calculate = roleFight.getFightCalculate();
		List<Integer> roleList = roleFight.getRoleList();
		if (armyPhalanx.getAimPhalanxId() == null)
		{
			calculate.searchAimPhalanx(armyPhalanx, armyPhalanxList, roleFight);

		}
		if (armyPhalanx.getAimPhalanxId() == null) {
			if (controlType != 0) {

				armyPhalanx.setPolicy(Policy.PURSUE_AUTO_ATTACK);
				armyPhalanx.setAimX(-1);
				armyPhalanx.setAimY(-1);
				armyPhalanx.setAimPhalanxId(null);
				armyPhalanx.setPath(null);
				return;
			}

		}

		if (armyPhalanx.getAimPhalanxId() != null && armyPhalanx.getCurrMoveMaxTime() >= 0) {
			int aimStatus = calculate.judeAppointAim(armyPhalanx, armyPhalanxList, controlType, true, gameMap);

			if (armyPhalanx.getCurrPursueTime() < armyPhalanx.getMaxPursueTime()) {

			}
			else {
				if (controlType == 1 && aimStatus == 3) {
					armyPhalanx.setPolicy(Policy.PURSUE_AUTO_ATTACK);
					armyPhalanx.setAimX(-1);
					armyPhalanx.setAimY(-1);
					armyPhalanx.setAimPhalanxId(null);
					armyPhalanx.setCurrPursueTime(0);
					armyPhalanx.setPath(null);
					calculate.setArmyPhlanxStagePoint(armyPhalanx, roleFight.getMap());
					// 发送停止指令
					FightCmdSend.sendRoleStopCmd(roleFight.getFightId(), roleList, armyPhalanx);
					return;
				}
			}

			if (aimStatus == 0) {
				armyPhalanx.setAimPhalanxId(null);
				armyPhalanx.setPath(null);
				return;
			}
			else if (aimStatus == 1) {
				if (armyPhalanx.getStatus() == 2) {
					armyPhalanx.setStatus(0);
					armyPhalanx.setPath(null);
					calculate.setArmyPhlanxStagePoint(armyPhalanx, gameMap);
					// 发送停止指令
					FightCmdSend.sendRoleStopCmd(roleFight.getFightId(), roleList, armyPhalanx);
				}
				if (armyPhalanx.getCurrAttackTime() >= armyPhalanx.getAttackMaxTime()) {
					for (int i = 0; i < armyPhalanxList.length; i++) {
						if (armyPhalanxList[i] != null) {

							if (armyPhalanxList[i].getPhalanxId().equals(armyPhalanx.getAimPhalanxId())) {
								DefendInfo defendInfo = null;
								// 进行攻击
								defendInfo = calculate.getFightCore().calculateCommAttrack(roleFight, armyPhalanx,
											armyPhalanxList[i], armyPhalanxList);

								if (defendInfo != null) {
									String attackSlogan = calculate.getFightCore().getSlogan(armyPhalanx, 1);
									FightCmdSend.sendRoleAttackCmd(roleFight.getFightId(), roleList, armyPhalanx,
											armyPhalanxList[i], attackSlogan, defendInfo.getFace(), defendInfo.getTime(),
											0, 0);

									armyPhalanxList[i].getDefendList().add(defendInfo);// 遭受攻击了
									armyPhalanx.setPath(null);
								}

								break;
							}
						}
					}
					armyPhalanx.setCurrAttackTime(0);
					armyPhalanx.setCurrPursueTime(0);
				}
				else {

				}
			}
			else if (aimStatus == 2) {
				// 进行移动
//				if (armyPhalanx.getCurrMoveTime() > 1) {
					if (armyPhalanx.getCurrPursueTime() >= armyPhalanx.getMaxPursueTime()) {

						armyPhalanx.setAimPhalanxId(null);
						armyPhalanx.setCurrPursueTime(0);
						armyPhalanx.setAimX(-1);
						armyPhalanx.setAimY(-1);
						armyPhalanx.setPath(null);
						calculate.searchAimPhalanx(armyPhalanx, armyPhalanxList, roleFight);
					}
					Path path = calculate.getAimXY(gameMap, armyPhalanx, armyPhalanxList, roleFight.getFightMapType());
					calculate.move(roleFight.getFightId(), gameMap, armyPhalanx, roleList, path, controlType, 1);
					armyPhalanx.setCurrPursueTime(armyPhalanx.getCurrPursueTime() + errorTime
							+ armyPhalanx.getCurrMoveTime());
					armyPhalanx.setCurrMoveTime(0);

//				}

			}
		}
	
	}

}
