package org.firstinspires.ftc.teamcode.Joaquin;

import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.ArmUtils.ArmPoses;

import java.util.HashMap;
import java.util.Map;

public class PositionFlag {

    private final Map<ArmPoses, Boolean> positionsFlagMap = new HashMap<>();
    public PositionFlag(SubsystemBase subsystem, ArmPoses startingPosition) {

        for (ArmPoses pose: ArmPoses.values()) {
            positionsFlagMap.put(pose, false);
        }
        setStartingPosition(startingPosition);

    }
    private void setStartingPosition(ArmPoses position) {
        positionsFlagMap.replace(position, true);
    }
    public void markFlag(ArmPoses entryPosition) {
        for (ArmPoses p : positionsFlagMap.keySet()) {
            if (p == entryPosition) {
                positionsFlagMap.replace(p, true);
            } else {
                positionsFlagMap.replace(p, false);
            }
        }
    }
    public Boolean isFlagged(ArmPoses entryPosition) {
        return Boolean.TRUE.equals(positionsFlagMap.get(entryPosition));
    }
}
