# Google Play 应用信息声明

## 应用主要目的（What is the main purpose of your app?）

### 英文版本（500字符以内）：

ChinaTravel is a travel planning application that helps users discover and plan trips to China. The app provides detailed information about cities, attractions, restaurants, and travel itineraries, along with interactive maps to visualize destinations. Users can browse travel products, view sightseeing details, and access customized travel services through chat with travel guides.

**字符数：** 383字符

### 中文版本：

ChinaTravel是一个综合性的旅游规划应用，旨在帮助用户发现、探索和规划中国之旅。该应用提供城市、景点、餐厅和旅行行程的详细信息，以及用于可视化目的地的交互式地图。用户可以通过与导游的集成聊天功能浏览旅游产品、查看景点详情并访问定制化旅游服务。该应用旨在通过提供视觉上下文、地理信息和个性化旅游推荐来增强旅行规划体验。

---

# Google Play 位置权限声明

## 功能描述（Location access）

**英文版本（500字符以内）：**

Our app uses location permission to provide interactive map features for travel destinations. When viewing sightseeing details or trip itineraries, the app displays maps showing exact locations of attractions, restaurants, and points of interest. This enhances travel planning by providing visual context and geographical layout. Location access is only used when users actively view map content, and this feature is clearly disclosed in our privacy policy.

**字符数：** 398字符

**中文版本：**

我们的应用使用位置权限来提供交互式地图功能。当用户查看景点详情或行程安排时，应用会显示地图，展示景点、餐厅和兴趣点的确切位置。这项服务通过提供视觉上下文和地理布局，增强旅行规划体验。位置访问仅在用户主动查看地图内容时使用，此功能在我们的隐私政策中有明确说明。

---

## 视频说明（Video instructions）

根据 [Google Play官方文档](https://support.google.com/googleplay/android-developer/answer/9799150#video) 的要求

### 视频基本要求

- **视频时长：** 30秒或更短（推荐）
- **视频格式：** YouTube链接（首选）或 Google Drive链接（MP4等常见格式）
- **设备要求：** 必须在Android设备上录制，不能使用iOS应用

### 必须包含的三个关键元素

1. **显著的应用内披露对话框（Prominent Disclosure）**
   - 显示我们实现的 `LocationPermissionDisclosureHelper` 对话框
   - 展示对话框内容，说明为什么需要位置权限
   - 显示"Allow"和"Deny"按钮

2. **运行时权限提示（Runtime Prompt）**
   - 显示Android系统的位置权限请求对话框
   - 展示用户同意权限的过程

3. **功能演示（Feature Demonstration）**
   - 展示交互式地图功能
   - 显示景点位置标记
   - 展示地图的交互功能（缩放、拖动等）

### 视频录制步骤（30秒脚本）

**0-5秒：应用启动和说明**
- 显示应用图标或主界面
- 简要说明："This video demonstrates the interactive map feature"

**5-10秒：展示显著披露对话框**
- 打开应用，首次启动
- 显示位置权限说明对话框（`LocationPermissionDisclosureHelper`）
- 展示对话框内容：
  - 标题："Location Permission Request"
  - 说明文字（解释为什么需要位置权限）
  - "Allow"和"Deny"按钮

**10-15秒：用户同意并触发系统权限**
- 点击"Allow"按钮
- 显示Android系统的位置权限请求对话框
- 用户点击"Allow"或"While using the app"

**15-25秒：展示地图功能**
- 进入景点详情页或行程详情页
- 展示地图功能，显示景点位置标记
- 展示地图的交互功能（缩放、拖动等）
- 说明："Location permission enables interactive maps showing attraction locations"

**25-30秒：总结**
- 说明："Location access enhances travel planning by providing visual context"

### 视频检查清单

在提交视频前，确保：
- [ ] 视频时长在30秒以内
- [ ] 在Android设备上录制（不是iOS）
- [ ] 包含显著披露对话框（我们实现的对话框）
- [ ] 包含系统运行时权限提示
- [ ] 清晰展示地图功能
- [ ] 视频链接可访问（YouTube或Google Drive）
- [ ] 视频质量清晰，可以看清界面内容

### 上传和提交

1. **上传到YouTube**
   - 创建YouTube账号（如果还没有）
   - 上传视频，设置为"公开"或"不公开列表"
   - 复制视频链接

2. **或上传到Google Drive**
   - 上传MP4文件到Google Drive
   - 设置分享权限为"任何有链接的人都可以查看"
   - 复制分享链接

3. **提交到Google Play Console**
   - 在"Permissions Declaration Form"的"Video instructions"字段中
   - 粘贴YouTube或Google Drive链接

### 常见错误避免

- ❌ 不要使用iOS应用录制
- ❌ 不要跳过显著披露对话框
- ❌ 不要超过30秒（尽量控制在30秒内）
- ❌ 不要使用模拟器（如果可能，使用真实设备）
- ❌ 不要忘记展示系统权限请求对话框

**视频链接：** [待上传YouTube后填写]

---

# Google Play 照片和视频权限声明

## 核心功能说明（Photo and Video Permissions - Core Functionality）

根据 Google Play Console 的要求，需要说明照片和视频权限如何支持应用的核心功能。

### 英文版本（500字符以内）：

ChinaTravel uses photo and video permissions to enable essential communication features. Users can take photos or select images from their gallery to share with travel guides during chat conversations, upload images when submitting support requests, and set profile pictures. These permissions are only used when users actively choose to share photos or videos, and are essential for the app's core communication and user support functionality.

**字符数：** 约 380 字符

### 中文版本：

ChinaTravel使用照片和视频权限来支持核心通信功能。用户可以在与导游聊天时拍照或从相册选择图片进行分享，在提交支持请求时上传图片，以及设置个人头像。这些权限仅在用户主动选择分享照片或视频时使用，是应用核心通信和用户支持功能的重要组成部分。

### 使用场景：

1. **聊天功能中的图片发送**
   - 用户在与导游聊天时，可以拍照或从相册选择图片发送
   - 位置：`BaseChatFragmentController.kt`

2. **用户支持功能**
   - 用户在上传支持请求时，可以拍照或选择图片作为附件
   - 位置：`UserSupportActivity.kt`

3. **用户头像设置**
   - 用户设置个人头像时，可以拍照或从相册选择

---

## 应用内权限披露实现

### ✅ 位置权限披露（已实现）

**实现位置：**
- `LocationPermissionDisclosureHelper.kt` - 位置权限披露工具类
- `dialog_location_permission_disclosure.xml` - 披露对话框布局
- `MainActivity.kt` - 在请求位置权限前显示披露对话框

**功能：**
- 在请求系统位置权限前，先显示自定义披露对话框
- 说明位置权限的用途和使用场景
- 符合Google Play的显著披露要求

### ✅ 照片和视频权限披露（已实现）

**实现位置：**
- `PhotoVideoPermissionDisclosureHelper.kt` - 照片和视频权限披露工具类
- `dialog_photo_video_permission_disclosure.xml` - 披露对话框布局
- `BaseChatFragmentController.kt` - 聊天中发送图片时显示披露对话框
- `UserSupportActivity.kt` - 用户支持页面上传图片时显示披露对话框

**功能：**
- 在请求相机权限前，先显示自定义披露对话框
- 在请求相册权限前，先显示自定义披露对话框
- 说明照片和视频权限的用途和使用场景
- 符合Google Play的显著披露要求

**使用场景：**
1. **聊天功能**：用户在与导游聊天时发送图片（拍照或从相册选择）
2. **用户支持**：用户在上传支持请求时添加图片附件
3. **用户头像**：用户设置个人头像时选择图片

### ✅ 所有文件访问权限披露（已实现）

**实现位置：**
- `StoragePermissionDisclosureHelper.kt` - 所有文件访问权限披露工具类
- `dialog_storage_permission_disclosure.xml` - 披露对话框布局

**功能：**
- 在请求所有文件访问权限前，显示自定义披露对话框
- 说明权限用途：用于下载和管理聊天附件
- 说明数据保护：文件仅用于应用内聊天功能，不会与第三方共享
- 符合Google Play的显著披露要求

**注意：** `MANAGE_EXTERNAL_STORAGE` 是特殊权限，在 Android 11 (API 30) 及以上版本需要通过系统设置页面手动授予。

---

## 隐私政策披露检查

### 当前披露情况

✅ **已包含位置权限说明**
- 位置：`PrivacyPolicyDialogHelper.kt` 第23行
- 内容："Location permission (to provide location-based services and map features)"

✅ **已包含相机权限说明**
- 位置：`PrivacyPolicyDialogHelper.kt` 第21行
- 内容："Camera (to take photos for user avatar or upload images)"

### 建议增强

为了符合Google Play的要求，建议在隐私政策中添加更详细的说明：

1. **照片和视频权限的详细说明**
   - 说明何时使用这些权限
   - 说明数据如何存储和使用
   - 说明是否与第三方共享

2. **在设置页面提供权限使用说明**
   - 为每个权限提供详细的使用说明链接

---

## 注意事项

1. **后台位置使用：** 从代码分析来看，应用主要使用前台位置权限（在Activity中显示地图时）。如果确实需要后台位置权限，需要明确说明使用场景。

2. **功能选择：** Google要求只描述一个主要功能。建议选择"交互式地图功能"作为主要功能，因为这是最核心的位置使用场景。

3. **视频要求：** 视频必须真实展示功能，不能是模拟或演示版本。

4. **批准范围：** 一旦批准，将授予整个应用的后台位置权限，而不仅仅是描述的功能。

