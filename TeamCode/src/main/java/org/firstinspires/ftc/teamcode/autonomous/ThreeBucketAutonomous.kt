package org.firstinspires.ftc.teamcode.autonomous

import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierCurve
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Path
import com.pedropathing.pathgen.PathChain
import com.pedropathing.pathgen.Point
import com.pedropathing.util.Constants
import com.pedropathing.util.Timer
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.robocol.Command
import com.seattlesolvers.solverslib.command.CommandOpMode
import org.firstinspires.ftc.teamcode.arm.armSystem.Arm
import org.firstinspires.ftc.teamcode.autonomous.AutoConfiguration.AutoPosesKeeper
import org.firstinspires.ftc.teamcode.autonomous.AutoConfiguration.PathState
import org.firstinspires.ftc.teamcode.autonomous.constants.FConstants
import org.firstinspires.ftc.teamcode.autonomous.constants.LConstants
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.IntakePose
import org.firstinspires.ftc.teamcode.arm.armSystem.ArmPoses.HighBasketPose
import org.firstinspires.ftc.teamcode.arm.armSystem.armSystemConfig

val Auto2dPoses = AutoPosesKeeper(

// Radians have to be adjusted
// All poses must be tested before execution, everything is an estimated pose
// Pickup3Pose is an estimated pose to pick up the third piece because it doesn't has direct contact
    startPose = Pose(135.0, 35.0, Math.toRadians(270.0)),
    scorePose = Pose(128.0, 16.0, Math.toRadians(315.0)),
    pickup1Pose = Pose(42.0, 121.0, Math.toRadians(0.0)),
    pickup2Pose = Pose(43.0, 130.0, Math.toRadians(0.0)),
    pickup3Pose = Pose(112.0, 8.0, Math.toRadians(0.0)),
    parkPose = Pose(79.0, 48.0, Math.toRadians(90.0)),
    controlParkPose = Pose(79.0, 48.0, Math.toRadians(90.0))
)

@Autonomous(name = "ThreeBucketBlueAuto", group = "Autonomous")
class ThreeBucketAuto: CommandOpMode() {

    private lateinit var follower: Follower

    private lateinit var pathTimer: Timer
    private lateinit var actionTimer: Timer
    private lateinit var opModeTimer: Timer

    private lateinit var pathState: PathState

    private lateinit var scorePreload: Path
    private lateinit var park: Path

    private lateinit var grabPiece1: PathChain
    private lateinit var grabPiece2: PathChain
    private lateinit var grabPiece3: PathChain

    private lateinit var scorePiece1: PathChain
    private lateinit var scorePiece2: PathChain
    private lateinit var scorePiece3: PathChain

    private lateinit var arm: Arm

    /**
     * It is called in the robot´s init in order to build the desired paths.
     */
    private fun buildPaths() {
        scorePreload = Path(BezierLine(Point(Auto2dPoses.startPose), Point(Auto2dPoses.scorePose)))
        scorePreload.setLinearHeadingInterpolation(Auto2dPoses.startPose.heading, Auto2dPoses.scorePose.heading)

        grabPiece1 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.scorePose), Point(Auto2dPoses.pickup1Pose)))
            .setLinearHeadingInterpolation(Auto2dPoses.scorePose.heading, Auto2dPoses.pickup1Pose.heading)
            .build()

        scorePiece1 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.pickup1Pose), Point(Auto2dPoses.scorePose)))
            .setLinearHeadingInterpolation(Auto2dPoses.pickup1Pose.heading, Auto2dPoses.scorePose.heading)
            .build()

        grabPiece2 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.scorePose), Point(Auto2dPoses.pickup2Pose)))
            .setLinearHeadingInterpolation(Auto2dPoses.scorePose.heading, Auto2dPoses.pickup2Pose.heading)
            .build()

        scorePiece2 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.pickup2Pose), Point(Auto2dPoses.scorePose)))
            .setLinearHeadingInterpolation(Auto2dPoses.pickup2Pose.heading, Auto2dPoses.scorePose.heading)
            .build()

        grabPiece3 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.scorePose), Point(Auto2dPoses.pickup3Pose)))
            .setLinearHeadingInterpolation(Auto2dPoses.scorePose.heading, Auto2dPoses.pickup3Pose.heading)
            .build()

        scorePiece3 = follower.pathBuilder()
            .addPath(BezierLine(Point(Auto2dPoses.pickup3Pose), Point(Auto2dPoses.scorePose)))
            .setLinearHeadingInterpolation(Auto2dPoses.pickup3Pose.heading, Auto2dPoses.scorePose.heading)
            .build()

        park = Path(BezierCurve(Point(Auto2dPoses.scorePose), Point(Auto2dPoses.controlParkPose), Point(Auto2dPoses.parkPose)))
        park.setLinearHeadingInterpolation(Auto2dPoses.scorePose.heading, Auto2dPoses.parkPose.heading)


    }

    /**
     * This function does a when that is called continuously during the autonomous, this way it can check
     * various conditions and perform the actions inside them such as following paths and performing arm
     * actions
     */
    fun autonomousPathUpdate() {
        when (pathState) {
            PathState(0) -> {
                follower.followPath(scorePreload, true)
                pathState.changeTo(1)
            }
            PathState(1) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(HighBasketPose)
                    pathState.changeTo(2)
                }
            }
            PathState(2) -> {
                if (follower.isBusy.not()) {
                    follower.followPath(grabPiece1, true)
                    pathState.changeTo(3)
                }
            }
            PathState(3) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(IntakePose)
                    pathState.changeTo(4)
                }
            }
            PathState(4) -> {
                if (follower.isBusy.not()){
                    follower.followPath(scorePiece1, true)
                    pathState.changeTo(5)
                }
            }
            PathState(5) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(HighBasketPose)
                    pathState.changeTo(6)
                }
            }
            PathState(6) -> {
                if(follower.isBusy.not()){
                    follower.followPath(grabPiece2, true)
                    pathState.changeTo(7)
                }
            }
            PathState(7) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(IntakePose)
                    pathState.changeTo(8)
                }
            }
            PathState(8) -> {
                if(follower.isBusy.not()){
                    follower.followPath(scorePiece2, true)
                    pathState.changeTo(9)
                }
            }
            PathState(9) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(HighBasketPose)
                    pathState.changeTo(10)
                }
            }
            PathState(10) -> {
                if(follower.isBusy.not()){
                    follower.followPath(grabPiece3, true)
                    pathState.changeTo(11)
                }
            }
            PathState(11) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(IntakePose)
                    pathState.changeTo(12)
                }
            }
            PathState(12) -> {
                if(follower.isBusy.not()){
                    follower.followPath(scorePiece3, true)
                    pathState.changeTo(13)
                }
            }
            PathState(13) -> {
                if (follower.isBusy.not()) {
                    arm.setPosition(HighBasketPose)
                    pathState.changeTo(14)
                }
            }
            PathState(14) -> {
                if(follower.isBusy.not()){
                    follower.followPath(park, true)
                    // Set the path state to one that we are not going to use
                    pathState.changeTo(-1)
                }
            }
        }
    }

    /**
     * This function changes the [pathState] to the desired one
     * It also resets the timer of the individual switches
     */
    fun PathState.changeTo(value: Int) {
        val pState = PathState(value)
        pathState = pState
        pathTimer.resetTimer()
    }

    /**
     * This method is called once in the init of the OpMode
     */
    override fun initialize() {
        pathTimer = Timer()
        opModeTimer = Timer()
        opModeTimer.resetTimer()

        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap, FConstants::class.java, LConstants::class.java)
        follower.setStartingPose(Auto2dPoses.startPose)
        buildPaths()

        //  Initialize the arm system
        arm = Arm(armSystemConfig, hardwareMap)
    }

    override fun runOpMode() {

        initialize()

        waitForStart()

        opModeTimer.resetTimer()
        pathState.changeTo(0)

        while (!isStopRequested && opModeIsActive()) {

            follower.update()
            autonomousPathUpdate()

            telemetry.addData("PathState: ", pathState)
            telemetry.addData("X Coordinates: ", follower.pose.x)
            telemetry.addData("Y Coordinates: ", follower.pose.y)
            telemetry.addData("Robot heading: ", follower.pose.heading)
            telemetry.update()

            run()
        }
        reset()
    }
}