### EMM企业移动管理
企业移动管理EMM（Enterprise Mobile Management）功能对移动终端设备进行全生命周期的管理。包括资产管理、设备管理(MDM)、应用管理(MAM)、内容管理(MCM)和日志管理等多方面提供完善的移动管理方案。

#### 移动资产管理
资产管理功能可以将移动终端作为企业资产纳入管理体系，包括终端注册、状态、注销等管理，具体功能如下：
+ 资产注册：终端首次接入企业内网时，用户需要输入用户名称、终端类型、资产编号等信息进行资产的注册，以完成资产和用户信息的关联；
+ 保密协议：用户在终端上首次接入时弹出“保密协议”信息，用户阅读并确认后才允许资产注册，协议信息可由企业根据自身信息安全要求进行定制；
+ 资产注销：支持用户自助注销和管理员手工注销资产信息，被注销终端将会解除和用户的绑定并擦除保存在本地的企业信息数据；
+ 终端状态报表：显示终端型号、所属用户、系统信息以及已安装应用列表等；
+ 用户自助管理：用户可以通过自助网站查看绑定资产信息，对终端进行锁屏、解锁、资产注销、数据擦除等管理操作。

#### 移动设备管理(MDM)
移动设备管理主要提供终端设备能力的管理和系统环境策略的配置，具体功能如下：
+ Root权限检查：支持检测获取Root权限的设备，可对获取Root权限的设备采取审计、提示、禁止接入、擦除AnyOffice数据和恢复出厂配置等策略；
+ 系统功能控制：在Android操作系统中，支持摄像头、蓝牙、蓝牙扫描、Wi-Fi、便携式WLAN热点等功能配置；在iOS操作系统中，支持摄像头、截屏等功能配置；
+ 一键式配置系统应用（iOS）：支持Exchange ActiveSync账号、IMAP/POP邮件账号、VPN账号、企业WiFi的一键式配置和管理；
+ 远程控制：支持远程锁定/解锁终端、恢复出厂设置；
+ 密码策略：支持密码中要求包含字母和数字、最小密码长度、密码最长有效期、新输入的密码和旧密码是否相同、输错密码的最大次数（达到次数即启动设备擦除）、自动锁屏时间配置；
+ 漫游管理：支持漫游提醒，允许/禁止漫游时的数据推送。

#### 移动应用管理(MAM)
移动应用管理主要提供企业应用管理及第三方应用的策略控制，具体功能如下：
+ 查看应用列表：终端上可查询应用信息列表，应用信息包括名称、大小、ID、版本和应用程序等数据；
+ 应用黑白名单：如果终端安装了列入黑名单的应用，则根据应用检查策略可对终端采取审计、提醒、禁止接入的操作；如果运行了黑名单中的应用，则不允许接入应用；如配置了白名单应用，则会提示没有安装用户安装；
+ 企业应用商店：企业应用商店的创建、删除、查看，以及版本管理；提供应用手工安装、卸载、升级、搜索、分类查看功能。

#### 移动内容管理(MCM)
移动内容管理主要指对企业数据在终端本地保存使用和传输过程中的安全保护，具体功能如下：
+ 文件在线模式下加解密：在线模式下用户企业数据&文档都加密保存，保持对涉密操作的在线管理，外部应用无法调用企业数据；和GigaTust软件集成，可以查看编辑RMS加密文档，实现双层保护；
+ 离线数据保护：在策略允许的情况下，可以离线使用，离线数据在终端上加密保存，用户可以输入密码在离线状态下打开文件；
+ 远程擦除：远程擦除包括全盘擦除和选择性擦除，全盘擦除指擦除终端上的所有数据，使终端配置恢复到出厂配置。例如，手机丢失的时候，用户可以通过自助页面或者通过管理员设置恢复出厂设置。选择性擦除指擦除应用自有的数据。

## DeviceAdmin和DeviceOwner
移动设备管理中主要使用Android的DeviceAdmin和DeviceOwner两大模块功能。

### DeviceAdmin(设备管理)：弱管控
移动设备管理中使用Google提供的DeviceAdmin设备管理功能，在Android在2.2版本中引进的。通过用户授权自己的应用设备管理权限后，可以在代码中修改很多系统设置，比如设置锁屏方式、恢复出厂设置、设置密码、强制清除密码，修改密码等操作。

### DeviceOwner(设备所有者)：强管控
通过DeviceAdmin所做的功能很有限，所以你需要用到DeviceOwner。“设备所有者”是一类特殊的设备管理员，具有在设备上创建和移除辅助用户以及配置全局设置的额外能力。之前申请的DeviceAdmin可以对你的设备进行一些修改，而当你的应用成为DeviceOwner后，你就可以拥有更多的能力，可以对其他应用进行限制。

## DeviceAdmin使用教程

+ 在res/xml目录下新建device_admin_receiver.xml文件
```xml
<?xml version="1.0" encoding="utf-8"?>
<device-admin>
    <uses-policies>
        <!--停用相机-->
        <disable-camera/>
        <!--锁屏时禁用某些功能-->
        <disable-keyguard-features/>
        <!--设置存储设备加密-->
        <encrypted-storage/>
        <!--设置锁定屏幕密码的有效期-->
        <expire-password/>
        <!--锁定屏幕-->
        <force-lock/>
        <!--设置密码规则-->
        <limit-password/>
        <!--更改屏幕解锁密码-->
        <reset-password/>
        <!--设置设备全局代理-->
        <set-global-proxy/>
        <!--监控屏幕解锁尝试次数-->
        <watch-login/>
        <!--恢复出厂设置-->
        <wipe-data/>
    </uses-policies>
</device-admin>
```

+ 注册一个广播继承DeviceAdminReceiver

```java
package cn.com.codeteenager.devicemanager.receiver;

import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import cn.com.codeteenager.devicemanager.MainActivity;
import cn.com.codeteenager.devicemanager.R;

public class CTDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i("onEnabled", "设备管理：可用");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        Log.e("onDisableRequested", "设备管理：不可用");
        return super.onDisableRequested(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.e("onDisableRequested", "设备管理：不可用");
    }

    /**
     * Called on the new profile when device owner provisioning has completed. Device owner
     * provisioning is the process of setting up the device so that its main profile is managed by
     * the mobile device management (MDM) application set up as the device owner.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        // Enable the profile
        DevicePolicyManager manager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = getComponentName(context);
        manager.setProfileName(componentName, context.getString(R.string.app_name));
        // Open the main screen
        Intent launch = new Intent(context, MainActivity.class);
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launch);

    }

    /**
     * Generates a {@link ComponentName} that is used throughout the app.
     *
     * @return a {@link ComponentName}
     */
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), CTDeviceAdminReceiver.class);
    }
}
```
+ 在清单文件里注册广播

```xml
<!--定义设备管理策略-->
        <receiver
            android:name=".receiver.CTDeviceAdminReceiver"
            android:permission="android.permission.BIND_DEVICE_ADMIN">
            <meta-data
                android:name="android.app.device_admin"
                android:resource="@xml/device_admin_receiver" />
            <intent-filter>
                <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
                <action android:name="android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED" />
                <action android:name="android.app.action.DEVICE_ADMIN_DISABLED" />
            </intent-filter>
        </receiver>
```
+ 激活设备管理器

```java
if (!mDevicePolicyManager.isAdminActive(mDeviceAdminReceiver)) {
    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminReceiver);
    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "用于设备和应用安全管理");
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    mContext.startActivity(intent);
} else {
    Toast.makeText(this, "此App已激活设备管理器", Toast.LENGTH_SHORT).show()
}
```
## DeviceOwner激活教程
使用利用ADB命令。
```shell
$adb shell dpm set-device-owner cn.com.codeteenager.devicemanager/cn.com.codeteenager.devicemanager.receiver.CTDeviceAdminReceiver
```
若出现如下类似错误：
```shell
java.lang.IllegalStateException: Not allowed to set the device owner because
there are already some accounts on the device
```
则可尝试到设置-账号中退出所有账户，然后重新尝试ADB设置。

## DeviceOwner移除教程

当一个app成为DeviceOwner后，这个app是不能被卸载，也无法在设置->安全中关闭其权限。要想DeviceOwner后还能卸载这个app，也就是退出DeviceOwner，有如下方法：
+ devicePolicyManager.clearDeviceOwnerApp(packageName)
+ 1. 在AndroidManifest.xml中的<application/>节点添加android:testOnly="true"；
  2. 通过命令adb install -t examole.apk安装该app；
  3. 通过命令adb shell dpm set-device-owner com.example.sample/.MyDeviceAdminReceiver成为DeviceOwner；
  4. 通过命令adb shell dpm remove-active-admin com.example.sample/.MyDeviceAdminReceive退出DeviceOwner；

