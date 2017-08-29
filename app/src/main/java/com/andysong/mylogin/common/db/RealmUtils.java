package com.andysong.mylogin.common.db;


import com.andysong.mylogin.GYTService;
import com.andysong.mylogin.UserBean;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 *数据库帮助类
 * Created by Administrator on 2017/5/16.
 *
 */

public class RealmUtils {
    public static final String DB_NAME = "cpigeonhelper.realm";
    private Realm mRealm;
    private static RealmUtils instance;
    private RealmUtils() {
    }

    public static RealmUtils getInstance() {
        if (instance == null) {
            synchronized (RealmUtils.class) {
                if (instance == null)
                    instance = new RealmUtils();
            }
        }
        return instance;
    }

    private Realm getRealm() {
        if (mRealm == null || mRealm.isClosed())
            mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 登录相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 添加用户信息
     * @param userBean
     */
    public void insertUserInfo(UserBean userBean)
    {
        getRealm().beginTransaction();//开启事务
        getRealm().copyToRealm(userBean);
        getRealm().commitTransaction();
    }

    /**
     * 查询用户信息
     * @return
     */
    public RealmResults<UserBean> queryUserInfo(){

        return  getRealm().where(UserBean.class).findAll();
    }

    /**
     * 判断是否存在用户信息
     * @return
     */
    public boolean existUserInfo(){
        RealmResults<UserBean> results = getRealm().where(UserBean.class).findAll();
        if (results!=null && results.size()>0)
        {
            return true;
        }
        return false;
    }

    /**
     * 删除用户信息
     */
    public void deleteUserInfo() {
        final RealmResults<UserBean> results = getRealm().where(UserBean.class).findAll();
//        getRealm().executeTransaction(realm -> results.deleteAllFromRealm());
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

//
//    ///////////////////////////////////////////////////////////////////////////
//    // 鸽运通服务信息
//    ///////////////////////////////////////////////////////////////////////////
//
//    /**
//     * 查询鸽运通服务
//     * @return
//     */
//    public RealmResults<GYTService> queryGTYInfo(){
//        return getRealm().where(GYTService.class).findAll();
//    }
//
//    /**
//     * 添加鸽运通服务
//     * @param gytService
//     */
//    public void insertGYTService(GYTService gytService) {
//        getRealm().beginTransaction();//开启事务
//        getRealm().copyToRealm(gytService);
//        getRealm().commitTransaction();
//    }
//
    /**
     * 是否存在鸽运通服务
     * @return
     */
    public boolean existGYTInfo(){
        RealmResults<GYTService> results = getRealm().where(GYTService.class).findAll();
        if (results!=null && results.size()>0)
        {
            return true;
        }
        return false;
    }

    /**
     * 删除鸽运通服务
     */
    public void deleteGYTInfo() {
        final RealmResults<GYTService> results = getRealm().where(GYTService.class).findAll();
//        getRealm().executeTransaction(realm -> results.deleteAllFromRealm());
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }


//
//
//    ///////////////////////////////////////////////////////////////////////////
//    // 坐标相关
//    ///////////////////////////////////////////////////////////////////////////
//
//
//    /**
//     * 插入坐标
//     * @param location
//     */
//    public void insertLocation(MyLocation location)
//    {
//        getRealm().beginTransaction();
//        getRealm().copyToRealm(location);
//        getRealm().commitTransaction();
//    }
//
//    /**
//     * 查询坐标
//     * @param raceId
//     * @return
//     */
//    public RealmResults<MyLocation> queryLocation(int raceId)
//    {
//        return getRealm().where(MyLocation.class).equalTo("raceId",raceId).findAll();
//    }
//
//    /**
//     * 删除所有坐标
//     */
//    public void deleteLocation() {
//        RealmResults<MyLocation> results = getRealm().where(MyLocation.class).findAll();
//        getRealm().executeTransaction(realm -> results.deleteAllFromRealm());
//    }
//
//    /**
//     * 是否存在坐标数据
//     * @return
//     */
//    public boolean existLocation(){
//        RealmResults<MyLocation> results = getRealm().where(MyLocation.class).findAll();
//        if (results!=null && results.size()>0)
//        {
//            return true;
//        }
//        return false;
//    }

//    ///////////////////////////////////////////////////////////////////////////
//    // 协会信息
//    ///////////////////////////////////////////////////////////////////////////
//
//    /**
//     * 插入协会信息
//     * @param orgInfo
//     */
//    public void insertXieHuiInfo(OrgInfo orgInfo) {
//        getRealm().beginTransaction();
//        getRealm().copyToRealmOrUpdate(orgInfo);
//        getRealm().commitTransaction();
//    }
//
//    /**
//     * 删除用户关联的协会信息
//     */
//    public void deleteXieHuiInfo(){
//        RealmResults<OrgInfo> results = getRealm().where(OrgInfo.class).findAll();
//        getRealm().executeTransaction(realm -> results.deleteAllFromRealm());
//    }
//
//    /**
//     * 查询用户关联的协会信息
//     * @param uid
//     * @return
//     */
//    public RealmResults<OrgInfo> queryOrgInfo(int uid) {
//        return getRealm().where(OrgInfo.class).equalTo("uid",uid).findAll();
//    }
//

}
