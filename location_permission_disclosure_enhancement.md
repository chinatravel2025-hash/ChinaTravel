# 位置权限披露增强建议

## 当前状态

### ✅ 已有披露
- **位置：** `PrivacyPolicyDialogHelper.kt` 第23行
- **内容：** "Location permission (to provide location-based services and map features)"

### ⚠️ 需要增强

根据Google Play的要求，应用需要在请求位置权限时向用户提供**显著披露（Prominent Disclosure）**，说明：
1. 为什么需要位置权限
2. 如何使用位置数据
3. 位置数据是否会被分享

## 建议的增强方案

### 1. 在权限请求时添加详细说明

**建议位置：** 在请求位置权限前显示一个说明对话框

**建议内容（英文）：**
```
Location Permission Request

ChinaTravel needs access to your location to provide the following features:

• Interactive Maps: Display attractions, restaurants, and points of interest on maps
• Current Location: Show your current position to help you navigate
• Route Planning: Help you plan travel routes based on your location

Your location data is only used within the app and is never shared with third parties. Location access is only used when you actively view map content.

[Allow] [Deny]
```

**建议内容（中文）：**
```
位置权限请求

ChinaTravel需要访问您的位置以提供以下功能：

• 交互式地图：在地图上显示景点、餐厅和兴趣点
• 当前位置：显示您当前的位置以帮助您导航
• 路线规划：根据您的位置帮助您规划旅行路线

您的位置数据仅在应用内使用，绝不会与第三方分享。位置访问仅在您主动查看地图内容时使用。

[允许] [拒绝]
```

### 2. 在设置页面添加位置权限说明

**建议位置：** 应用的设置/隐私设置页面

**建议内容：**
```
Location Services

ChinaTravel uses your location to:
- Display interactive maps with attractions and points of interest
- Show your current location on maps
- Help you plan travel routes

Location data is only used when you view map content in the app. We do not collect, store, or share your location data with third parties.

You can manage location permissions in your device settings.
```

### 3. 更新隐私政策中的位置权限说明

**当前内容：**
```
(3) Location permission (to provide location-based services and map features);
```

**建议增强为：**
```
(3) Location permission: We request location permission to provide interactive map features that enhance your travel planning experience. When you view sightseeing details or trip itineraries, the app displays maps showing the exact locations of attractions, restaurants, and points of interest. The map also shows your current location to help you navigate. Location data is only used when you actively view map content in the app. We do not collect, store, or share your location data with third parties. You can revoke this permission at any time through your device settings.
```

## 实施建议

### 优先级1：添加权限请求前的说明对话框
- 在请求位置权限前显示说明
- 用户同意后才请求系统权限
- 符合Google Play的"显著披露"要求

### 优先级2：更新隐私政策文本
- 在`PrivacyPolicyDialogHelper.kt`中更新位置权限说明
- 确保说明详细且准确

### 优先级3：添加设置页面说明
- 在应用的设置页面添加位置权限使用说明
- 提供管理权限的快捷入口

## 代码实施位置

1. **权限请求前说明：** 在`MainActivity.kt`或权限请求工具类中添加
2. **隐私政策更新：** 修改`PrivacyPolicyDialogHelper.kt`的`getPrivacyPolicyContent()`方法
3. **设置页面：** 如果应用有设置页面，在那里添加说明

