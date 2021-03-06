package com.qualcomm.robotcore.eventloop.opmode;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.EventLoopManager;

import org.firstinspires.ftc.robotcore.internal.webserver.WebServer;

/**
 * Created by David on 7/7/2017.
 */

public interface FtcRobotControllerServiceState extends EventLoopManagerClient {
    @NonNull
    WebServer getWebServer();

    EventLoopManager getEventLoopManager();
}
