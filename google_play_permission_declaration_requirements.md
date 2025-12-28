# Google Play Console 权限声明要求分析

根据截图，Google Play Console 要求解决以下问题：

## 🔴 需要解决的问题

### 1. 未声明的权限（Permissions that haven't been declared）
**出现次数：** 2次

**要求：** 需要在 Google Play Console 的"敏感应用权限"部分声明所有敏感权限。

### 2. 照片和视频权限的核心功能声明
**要求：** 必须说明照片和视频权限如何支持应用的核心功能。

---

## 📋 应用声明的敏感权限清单

根据 `AndroidManifest.xml` 分析，应用声明了以下敏感权限：

### 照片和视频相关权限
- ✅ `READ_MEDIA_IMAGES` (Android 13+)
- ✅ `READ_MEDIA_VIDEO` (Android 13+)
- ✅ `READ_EXTERNAL_STORAGE` (Android 12及以下)
- ✅ `CAMERA`

### 位置相关权限
- ✅ `ACCESS_FINE_LOCATION`
- ✅ `ACCESS_COARSE_LOCATION`
- ✅ `ACCESS_BACKGROUND_LOCATION` (在lib/map模块中)

### 其他敏感权限
- ✅ `READ_PHONE_STATE`
- ✅ `RECORD_AUDIO`
- ✅ `WRITE_EXTERNAL_STORAGE`
- ✅ `MANAGE_EXTERNAL_STORAGE`
- ✅ `POST_NOTIFICATIONS` (Android 13+)

---

## 📝 照片和视频权限使用场景

根据代码分析，照片和视频权限用于以下核心功能：

### 1. 聊天功能中的图片发送
- **位置：** `BaseChatFragmentController.kt`
- **用途：** 用户在与导游聊天时，可以拍照或从相册选择图片发送
- **权限：** `CAMERA`, `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`

### 2. 用户支持功能
- **位置：** `UserSupportActivity.kt`
- **用途：** 用户在上传支持请求时，可以拍照或选择图片作为附件
- **权限：** `CAMERA`, `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`

### 3. 用户头像设置
- **用途：** 用户设置个人头像时，可以拍照或从相册选择
- **权限：** `CAMERA`, `READ_MEDIA_IMAGES`

---

## ✍️ Google Play Console 声明文本

### 照片和视频权限声明（Photo and Video Permissions）

#### 英文版本（500字符以内）：

**核心功能说明：**

ChinaTravel uses photo and video permissions to enable essential communication features. Users can take photos or select images from their gallery to share with travel guides during chat conversations, upload images when submitting support requests, and set profile pictures. These permissions are only used when users actively choose to share photos or videos, and are essential for the app's core communication and user support functionality.

**字符数：** 约 380 字符

#### 中文版本：

ChinaTravel使用照片和视频权限来支持核心通信功能。用户可以在与导游聊天时拍照或从相册选择图片进行分享，在提交支持请求时上传图片，以及设置个人头像。这些权限仅在用户主动选择分享照片或视频时使用，是应用核心通信和用户支持功能的重要组成部分。

---

### 其他需要声明的权限

#### 1. 录音权限（RECORD_AUDIO）

**英文版本：**

ChinaTravel uses audio recording permission to enable voice messaging in chat conversations with travel guides. Users can record and send voice messages as an alternative to text communication. This permission is only used when users actively choose to record and send voice messages.

**中文版本：**

ChinaTravel使用录音权限来支持与导游聊天时的语音消息功能。用户可以录制并发送语音消息作为文字交流的替代方式。此权限仅在用户主动选择录制和发送语音消息时使用。

#### 2. 电话状态权限（READ_PHONE_STATE）

**英文版本：**

ChinaTravel uses phone state permission to obtain network operator information for network status detection and location services. This helps ensure stable network connectivity and accurate location-based services.

**中文版本：**

ChinaTravel使用电话状态权限来获取网络运营商信息，用于网络状态检测和位置服务。这有助于确保稳定的网络连接和准确的位置服务。

#### 3. 存储权限（WRITE_EXTERNAL_STORAGE / MANAGE_EXTERNAL_STORAGE）

**英文版本：**

ChinaTravel uses storage permissions to save images captured by users, cache map data for offline use, and store chat attachments. These permissions ensure smooth functionality when users take photos, download map content, or receive media files in chat conversations.

**中文版本：**

ChinaTravel使用存储权限来保存用户拍摄的图片、缓存地图数据以供离线使用，以及存储聊天附件。这些权限确保用户在拍照、下载地图内容或接收聊天中的媒体文件时功能正常运行。

---

## 🎯 操作步骤

### 在 Google Play Console 中声明权限：

1. **进入权限声明页面**
   - 登录 Google Play Console
   - 进入"应用内容"（App content）
   - 点击"敏感应用权限"（Sensitive app permissions）
   - 选择需要声明的权限类型

2. **声明照片和视频权限**
   - 选择"照片和视频权限"（Photo and video permissions）
   - 填写核心功能说明（使用上面提供的英文文本）
   - 说明权限如何支持应用的核心功能

3. **声明其他敏感权限**
   - 逐一声明所有未声明的敏感权限
   - 为每个权限提供清晰的使用说明

4. **提交审核**
   - 完成所有权限声明后
   - 提交应用进行审核

---

## ✅ 检查清单

在提交前，确保：

- [ ] 所有敏感权限都已声明
- [ ] 照片和视频权限的核心功能说明已填写
- [ ] 每个权限的使用场景都已清楚说明
- [ ] 说明文本符合Google Play政策要求
- [ ] 所有声明都使用英文填写（中文作为参考）

---

## 📌 重要提示

1. **只声明实际使用的权限**：如果某些权限实际上没有使用，考虑从 `AndroidManifest.xml` 中移除

2. **核心功能关联**：确保每个权限的声明都明确说明它如何支持应用的核心功能

3. **用户主动使用**：强调权限仅在用户主动使用相关功能时才会被请求

4. **隐私政策**：确保隐私政策中也包含了这些权限的使用说明

