package com.equationl.common.platform

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.equationl.common.constant.PlatformType
import com.equationl.common.overlay.OverlayService
import com.equationl.common.viewModel.KeyboardTypeStandard
import com.equationl.shared.generated.resources.Res
import com.equationl.shared.generated.resources.tip_need_float_permission
import com.equationl.shared.generated.resources.tip_not_support_float_cause_android_version
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

@OptIn(ExperimentalResourceApi::class)
actual suspend fun showFloatWindows() {
    val context = ActivityUtils.getTopActivity()

    if (context == null) {
        Log.e("EL", "showFloatWindows: get Context is null!")
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (Settings.canDrawOverlays(context)) {
            context.startService(Intent(context, OverlayService::class.java))

            // 返回主页
            Intent(Intent.ACTION_MAIN).apply{
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.let { context.startActivity(it) }
        }
        else {
            Toast.makeText(context, getString(Res.string.tip_need_float_permission), Toast.LENGTH_LONG).show()
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivity(intent)
        }
    }
    else {
        Toast.makeText(context, getString(Res.string.tip_not_support_float_cause_android_version), Toast.LENGTH_LONG).show()
    }
}

actual fun changeKeyBoardType(changeTo: Int, isFromUser: Boolean) {
    if (!isFromUser) return
    vibrateOnClick()
    val activity = ActivityUtils.getTopActivity()
    activity?.requestedOrientation =
        if (changeTo == KeyboardTypeStandard)
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

actual fun isNeedShowFloatBtn(): Boolean = true

actual fun currentPlatform(): PlatformType = PlatformType.Android