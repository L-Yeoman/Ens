package com.mapuni.caremission_ens.config;

import android.os.Environment;

/**
 * Created by yawei on 2017/3/28.
 */

public class PathManager {
//  pdf路径
    public static String Dir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDF";

    public static String ip = "http://220.202.61.115:8855";
//    检测站信息
    public static String GetRegionStation =ip+"/znzd/common/get-info!getRegionStation.action";
    public static String GetPointFilter =ip+"/znzd/gis/point-filter.action";
    public static String GetCheckStationInfo =ip+"/znzd/common/get-info!getCheckStationInfo.action";
    public static String GetCarInfo =ip+"/znzd/common/get-info!getCheckInfo.action";
    public static String GetAllFile =ip+"/znzd/file/file-download.action";
    public static String GetAllCommonInfo =ip+"/znzd/message/message-manage!commonInfo.action";
    public static String GetInfoContent = ip+"/znzd/message/message-manage!getInfoContent.action";
    public static String GetNewMessageCount= ip+"/znzd/message/message-manage!getNewMessageCount.action";
}
