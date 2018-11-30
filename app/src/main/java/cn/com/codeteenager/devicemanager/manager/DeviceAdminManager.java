package cn.com.codeteenager.devicemanager.manager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import cn.com.codeteenager.devicemanager.receiver.CTDeviceAdminReceiver;

public class DeviceAdminManager {
    private static DeviceAdminManager mDeviceAdminManager = new DeviceAdminManager();
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * DevicePolicyManager
     */
    private DevicePolicyManager mDevicePolicyManager;

    /**
     * DeviceAdminReceiver
     */
    private ComponentName mDeviceAdminReceiver;

    /**
     * 获取 DevicePolicyManager
     *
     * @return
     */
    public DevicePolicyManager getDevicePolicyManager() {
        return mDevicePolicyManager;
    }

    /**
     * 获取 DeviceAdminReceiver
     *
     * @return
     */
    public ComponentName getDeviceAdminReceiver() {
        return mDeviceAdminReceiver;
    }

    public static DeviceAdminManager get() {
        return mDeviceAdminManager;
    }

    /**
     * 获取设备管理服务
     */
    public void init(Context context) {
        mContext = context;
        // 获取设备管理服务
        mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // AMSDeviceAdminReceiver 继承自 DeviceAdminReceiver
        mDeviceAdminReceiver = new ComponentName(context, CTDeviceAdminReceiver.class);
        // 激活设备管理员权限
        if (!mDevicePolicyManager.isAdminActive(mDeviceAdminReceiver)) {
            activeDeviceAdmin();
        }
    }

    /**
     * 激活管理员
     */
    public void activeDeviceAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "用于设备和应用安全管理");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

    /**
     * 移除管理员
     */
    public void removeActiveAdmin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDevicePolicyManager.clearDeviceOwnerApp(mContext.getPackageName());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDevicePolicyManager.removeActiveAdmin(mDeviceAdminReceiver);
        }
    }

    /**
     * 检测设备所有者
     */
    public boolean isDeviceOwner() {
        return mDevicePolicyManager.isDeviceOwnerApp(mContext.getPackageName());
    }
}
