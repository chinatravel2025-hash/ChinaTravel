package com.china.travel.widget.permission;


import android.content.Context;

import androidx.annotation.NonNull;

import com.china.travel.widget.R;

import java.util.List;

/**
 *    github : https://github.com/getActivity/XXPermissions
 *    desc   : 权限描述转换器
 */
public final class PermissionDescriptionConvert {

    /**
     * 获取权限描述
     */
   public static String getPermissionDescription(Context context, List<String> permissions) {
       StringBuilder stringBuilder = new StringBuilder();
       List<String> permissionNames = PermissionNameConvert.permissionsToNames(context, permissions);
       for (String permissionName : permissionNames) {
           stringBuilder.append(permissionName)
               .append(context.getString(R.string.common_permission_colon))
               .append(permissionsToDescription(context, permissionName))
               .append("\n");
       }
       return stringBuilder.toString().trim();
   }

   /**
    * 将权限名称列表转换成对应权限描述
    */
   @NonNull
   public static String permissionsToDescription(Context context, String permissionName) {
       // 请根据权限名称转换成对应权限说明
       return "用于"+permissionName+"业务";
   }
}