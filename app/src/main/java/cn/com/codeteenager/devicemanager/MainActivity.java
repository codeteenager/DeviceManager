package cn.com.codeteenager.devicemanager;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import cn.com.codeteenager.devicemanager.manager.DeviceAdminManager;

import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_NUMERIC;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private AppCompatButton btnLock;
    private AppCompatButton btnResetFactory;
    private SwitchCompat switchDisableCamera, switchDisableScreen, switchDisableUsb, switchDisableCall, switchDisableDebug, switchDisableGPS, switchDisableNetworkShare, switchDisableResetFactory, switchDisableSMS, switchDisableTF;
    private AppCompatButton btnLockPassword;
    private DeviceAdminManager deviceAdminManager;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        btnLock.setOnClickListener(this);
        btnLockPassword.setOnClickListener(this);
        btnResetFactory.setOnClickListener(this);
        switchDisableCamera.setOnCheckedChangeListener(this);
        switchDisableScreen.setOnCheckedChangeListener(this);
        switchDisableUsb.setOnCheckedChangeListener(this);
        switchDisableCall.setOnCheckedChangeListener(this);
        switchDisableDebug.setOnCheckedChangeListener(this);
        switchDisableGPS.setOnCheckedChangeListener(this);
        switchDisableNetworkShare.setOnCheckedChangeListener(this);
        switchDisableResetFactory.setOnCheckedChangeListener(this);
        switchDisableSMS.setOnCheckedChangeListener(this);
        switchDisableTF.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        deviceAdminManager = DeviceAdminManager.get();
        deviceAdminManager.init(this);

        devicePolicyManager = deviceAdminManager.getDevicePolicyManager();
        componentName = deviceAdminManager.getDeviceAdminReceiver();

        btnLock = findViewById(R.id.btn_lock);
        btnResetFactory = findViewById(R.id.btn_reset_factory);
        btnLockPassword = findViewById(R.id.btn_lock_password);

        switchDisableCamera = findViewById(R.id.switch_disable_camera);
        switchDisableScreen = findViewById(R.id.switch_disable_screen);
        switchDisableUsb = findViewById(R.id.switch_disable_usb);
        switchDisableCall = findViewById(R.id.switch_disable_call);
        switchDisableDebug = findViewById(R.id.switch_disable_debug);
        switchDisableGPS = findViewById(R.id.switch_disable_gps);
        switchDisableNetworkShare = findViewById(R.id.switch_disable_network_share);
        switchDisableResetFactory = findViewById(R.id.switch_disable_reset_factory);
        switchDisableSMS = findViewById(R.id.switch_disable_sms);
        switchDisableTF = findViewById(R.id.switch_disable_tf);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lock:
                lockScreen();
                break;
            case R.id.btn_lock_password:
                setLockPassword();
                break;
            case R.id.btn_reset_factory:
                resetFactory();
                break;
        }
    }

    /**
     * 锁屏
     */
    private void lockScreen() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.lockNow();
        }
    }

    /**
     * 是否禁用摄像头
     */
    private void disableCamera(boolean isDisable) {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.setCameraDisabled(componentName, isDisable);
        }
    }

    /**
     * 恢复出厂设置
     */
    private void resetFactory() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.wipeData(DevicePolicyManager.WIPE_RESET_PROTECTION_DATA);
        }
    }

    /**
     * 设备重启(7.0以上支持)
     */
    private void reboot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (devicePolicyManager.isAdminActive(componentName)) {
                devicePolicyManager.reboot(componentName);
            }
        }
    }

    /**
     * 设置锁屏密码
     */
    private void setLockPassword() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.setPasswordMinimumSymbols(componentName, 0);
            devicePolicyManager.setPasswordMinimumLength(componentName, 0);
            devicePolicyManager.setPasswordQuality(componentName, PASSWORD_QUALITY_NUMERIC);
            devicePolicyManager.resetPassword("000000", 0);
        }
    }

    /**
     * 是否禁止截屏
     *
     * @param isDisable
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableScreen(boolean isDisable) {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.setScreenCaptureDisabled(componentName, isDisable);
        }
    }

    /**
     * 禁止使用usb
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableUsb(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_USB_FILE_TRANSFER);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_USB_FILE_TRANSFER);
        }
    }

    /**
     * 禁止在设置中恢复出厂设置
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableResetFactory(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_FACTORY_RESET);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_FACTORY_RESET);
        }
    }

    /**
     * 禁止发送短信
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableSMS(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_SMS);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_SMS);
        }
    }

    /**
     * 禁止使用外部存储卡
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableTF(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA);
        }
    }

    /**
     * 禁止打电话
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableCall(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_OUTGOING_CALLS);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_OUTGOING_CALLS);
        }
    }

    /**
     * 禁止Debug调试功能
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableDebug(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_DEBUGGING_FEATURES);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_DEBUGGING_FEATURES);
        }
    }

    /**
     * 禁止使用GPS
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableGPS(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_SHARE_LOCATION);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_SHARE_LOCATION);
        }
    }

    /**
     * 禁止使用网络
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableNetwork(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_CONFIG_MOBILE_NETWORKS);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_CONFIG_MOBILE_NETWORKS);
        }
    }

    /**
     * 禁止开启热点
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void disableNetworkSharing(boolean isDisable) {
        if (isDisable) {
            devicePolicyManager.addUserRestriction(componentName, UserManager.DISALLOW_CONFIG_TETHERING);
        } else {
            devicePolicyManager.clearUserRestriction(componentName, UserManager.DISALLOW_CONFIG_TETHERING);
        }
    }

    /**
     * 设置为默认桌面
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setDefaultLauncher() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        DevicePolicyManager mDevicePolicyManager = DeviceAdminManager.get().getDevicePolicyManager();
        mDevicePolicyManager.clearPackagePersistentPreferredActivities(componentName, "com.huawei.android.launcher");
        mDevicePolicyManager.addPersistentPreferredActivity(
                componentName, filter, new ComponentName(this, MainActivity.class));
    }

    /**
     * 清除默认桌面
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void clearDefaultLauncher() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        DevicePolicyManager mDevicePolicyManager = DeviceAdminManager.get().getDevicePolicyManager();
        mDevicePolicyManager.clearPackagePersistentPreferredActivities(
                componentName,
                getPackageName());
        mDevicePolicyManager.addPersistentPreferredActivity(
                componentName, filter, new ComponentName(this, MainActivity.class));
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.switch_disable_camera:
                disableCamera(checked);
                break;
            case R.id.switch_disable_screen:
                disableScreen(checked);
                break;
            case R.id.switch_disable_usb:
                disableUsb(checked);
                break;
            case R.id.switch_disable_call:
                disableCall(checked);
                break;
            case R.id.switch_disable_debug:
                disableDebug(checked);
                break;
            case R.id.switch_disable_gps:
                disableGPS(checked);
                break;
            case R.id.switch_disable_network_share:
                disableNetworkSharing(checked);
                break;
            case R.id.switch_disable_reset_factory:
                disableResetFactory(checked);
                break;
            case R.id.switch_disable_sms:
                disableSMS(checked);
                break;
            case R.id.switch_disable_tf:
                disableTF(checked);
                break;
        }
    }
}
