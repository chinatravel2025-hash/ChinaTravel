package com.china.travel;

import androidx.navigation.NavController;

public class NavControllerHelper {
    public static boolean shouldHandleBackPress(NavController navController) {
        // 检查是否有任何的目的地可以处理回退事件
        return navController.getCurrentDestination() != null
                && navController.popBackStack();
    }
}
