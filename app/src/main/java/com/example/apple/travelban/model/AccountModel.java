package com.example.apple.travelban.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.apple.travelban.app.App;
import com.example.apple.travelban.model.bean.User;
import com.kermit.exutils.utils.ExUtils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by Kermit on 15-8-16.
 * e-mail : wk19951231@163.com
 */
public class AccountModel {

    private static final String TAG = "AccountModel";

    private static AccountModel accountModel;
    private static Context mContext;

    private AccountModel(){
        mContext = App.getIntance();
    }

    public static AccountModel getInstance(){
        if (accountModel == null)
            accountModel = new AccountModel();
        return accountModel;
    }

    private ILoginListener mLoginListener;
    public interface ILoginListener{
        void onLoginSuccess();
        void onLoginFailure(String msg);
    }
    public void setLoginListener(ILoginListener loginListener){
        mLoginListener = loginListener;
    }

    public void login(String userName, String pwd){
        final BmobUser user = new BmobUser();
        user.setUsername(userName);
        user.setPassword(pwd);
        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if (mLoginListener != null) {
                    mLoginListener.onLoginSuccess();
                } else {
                    Log.i(TAG, "login listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (mLoginListener != null) {
                    mLoginListener.onLoginFailure(s);
                } else {
                    Log.i(TAG, "login listener is null,you must set one!");
                }
            }

        });
    }


    public void logout(){
        BmobUser.logOut(mContext);
        Log.i(TAG, "logout result:" + (null == getCurrentUser()));
    }

    public interface IUpdateListener{
        void onUpdateSuccess();
        void onUpdateFailure(String msg);
    }
    private IUpdateListener updateListener;
    public void setOnUpdateListener(IUpdateListener updateListener){
        this.updateListener = updateListener;
    }
    public void update(String... args){
        User user = getCurrentUser();
        user.setUsername(args[0]);
        user.setEmail(args[1]);
        user.setPassword(args[2]);
        user.setSex(args[3]);
//        user.setSignature(args[4]);
        //...
        user.update(mContext, new UpdateListener() {

            @Override
            public void onSuccess() {
                if (updateListener != null) {
                    updateListener.onUpdateSuccess();
                } else {
                    Log.i(TAG, "update listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int arg0, String msg) {
                if (updateListener != null) {
                    updateListener.onUpdateFailure(msg);
                } else {
                    Log.i(TAG, "update listener is null,you must set one!");
                }
            }
        });
    }

    public void updateFace(String face){
        User user = getCurrentUser();
        user.setFace(face);
        user.update(mContext, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "头像更新成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    //验证
    public void requestVerify(Context context, String num, String template){
        BmobSMS.requestSMSCode(context, num, template, new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    Log.i(TAG, integer.toString());
                    ExUtils.Toast("发送成功");
                } else {
                    ExUtils.Toast("发送失败");
                }
            }
        });
    }

    public interface IVerifyListener{
        void onVerifySuccess();
    }
    private IVerifyListener mIVerifyListener;
    public void setIVerifyListener(IVerifyListener listener){
        this.mIVerifyListener = listener;
    }
    public void verify(Context context, String num, String smsCode){
        BmobSMS.verifySmsCode(context, num, smsCode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mIVerifyListener.onVerifySuccess();
                    ExUtils.Toast("验证成功");
                } else {
                    ExUtils.Toast("验证失败，请核实验证码或退出重试");
                }
            }
        });
    }



    private ISignUpListener mSignUpListener;
    public interface ISignUpListener{
        void onSignUpSuccess();
        void onSignUpFail();
    }
    public void setSignUpListener(ISignUpListener listener){
        mSignUpListener = listener;
    }

    public void register(String name, String pwd, String sex, String face){
        User user = new User();
        user.setPassword(pwd);
        user.setUsername(name);
        user.setSex(sex);
        user.setFace(face);
        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                if (mSignUpListener != null) {
                    ExUtils.Toast("注册成功");
                    mSignUpListener.onSignUpSuccess();
                } else {
                    Log.i(TAG, "signup listener is null,you must set one!");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (mSignUpListener != null) {
                    mSignUpListener.onSignUpFail();
                } else {
                    Log.i(TAG, "signup listener is null,you must set one!");
                }
            }
        });
    }

    public User getCurrentUser(){
        User user = BmobUser.getCurrentUser(mContext, User.class);
        if (user != null){
            Log.i(TAG,"本地用户信息" + user.getObjectId() + "-"
                    + user.getUsername() + "-"
                    + user.getSessionToken() + "-"
                    + user.getCreatedAt() + "-"
                    + user.getUpdatedAt() + "-"
                    + user.getSex());
            return user;
        }else{
            Log.i(TAG, "本地用户为null，请登录");
        }
        return null;
    }

    //// TODO: 15-8-16 修改密码
//    public interface IResetPasswordListener{
//        void onResetSuccess();
//        void onResetFailure(String msg);
//    }
//    private IResetPasswordListener resetPasswordListener;
//    public void setOnResetPasswordListener(IResetPasswordListener resetPasswordListener){
//        this.resetPasswordListener = resetPasswordListener;
//    }
}
