package org.firstinspires.ftc.teamcode.autonomous

import com.pedropathing.localization.Pose

sealed class AutoConfiguration {
    /**
     * The [AutoPosesKeeper] class is used to keep the pose coordinates in one single place
     */
    data class AutoPosesKeeper(
        val startPose: Pose,
        val scorePose: Pose,
        val pickup1Pose: Pose,
        val pickup2Pose: Pose,
        val pickup3Pose: Pose,
        val parkPose: Pose,
        val controlParkPose: Pose
    )

    /**
     * The [PathState] class is used only for reading-friendly stuff.
     */
    data class PathState(private val pathState: Int)
}