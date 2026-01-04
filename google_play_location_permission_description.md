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

---

# Google Play 内容评级设置（年龄限制）

## 设置内容评级为"所有人"（不限制）

在 Google Play Console 中设置应用的内容评级（Content Rating），将其设置为"所有人"（Everyone/不限制）的步骤如下：

### 步骤 1：进入内容评级页面

1. 登录 [Google Play Console](https://play.google.com/console)
2. 选择您的应用（ChinaTravel）
3. 在左侧菜单中，点击 **"政策"** → **"应用内容"** → **"内容分级"**（Content rating）
   - 或者直接访问：**"应用内容"** → **"内容分级"**

### 步骤 2：填写 IARC 问卷

Google Play 使用 IARC（International Age Rating Coalition）问卷来确定内容评级。要获得"所有人"（Everyone）评级，需要如实回答以下问题：

#### 关键问题回答指南（针对旅游应用）

**1. 暴力内容（Violence）**
- ✅ **选择：无暴力内容** 或 **卡通/幻想暴力**
- 旅游应用通常不包含暴力内容

**2. 性内容（Sexual Content）**
- ✅ **选择：无性内容**
- 旅游应用不包含性相关内容

**3. 语言（Language）**
- ✅ **选择：无不当语言** 或 **轻微不当语言**
- 如果应用中有用户生成内容（如聊天），选择"轻微不当语言"更安全

**4. 酒精、烟草和毒品（Alcohol, Tobacco, and Drugs）**
- ✅ **选择：无相关内容**
- 旅游应用可能提到餐厅或酒吧，但不宣传酒精使用

**5. 赌博和竞赛（Gambling and Contests）**
- ✅ **选择：无赌博内容**
- 旅游应用不包含赌博功能

**6. 恐怖内容（Horror）**
- ✅ **选择：无恐怖内容**

**7. 应用内购买（In-App Purchases）**
- ⚠️ **如实选择：是/否**
- 如果应用有内购功能（如购买旅游产品），选择"是"
- 有内购不会影响"所有人"评级，但需要在应用描述中说明

**8. 用户生成内容（User-Generated Content）**
- ⚠️ **如实选择：是/否**
- 如果应用有聊天、评论等功能，选择"是"
- 需要说明有内容审核机制

**9. 位置共享（Location Sharing）**
- ⚠️ **如实选择：是**
- 应用使用位置权限显示地图，这是核心功能
- 已在权限声明中说明

**10. 个人信息收集（Personal Information Collection）**
- ⚠️ **如实选择：是/否**
- 如果收集用户信息（如注册、聊天），选择"是"
- 需要在隐私政策中说明

### 步骤 3：提交问卷

1. 完成所有问题后，点击 **"保存"** 或 **"提交"**
2. IARC 系统会自动计算内容评级
3. 如果所有问题都选择了最安全的选项，通常会获得 **"Everyone"**（所有人）评级

### 步骤 4：查看评级结果

提交后，您会看到：
- **全球评级：** Everyone（所有人）
- **各地区评级：** 可能因地区法规略有不同，但通常都是最低评级

### 重要提示

1. **如实回答：** 必须如实回答所有问题，虚假信息可能导致应用被下架
2. **定期更新：** 如果应用功能发生变化（如新增聊天功能），需要重新填写问卷
3. **地区差异：** 某些地区可能有特殊要求，但"所有人"评级在大多数地区都适用
4. **内购说明：** 如果有内购，需要在应用描述中明确说明

### 常见问题

**Q: 为什么我的应用被评级为"Everyone 10+"而不是"Everyone"？**
A: 可能是因为选择了"轻微不当语言"或"用户生成内容"。对于旅游应用，如果确保内容审核严格，通常可以获得"Everyone"评级。

**Q: 内容评级会影响应用分发吗？**
A: 不会。内容评级只是告诉用户应用适合的年龄段，不会限制应用在 Google Play 上的分发。

**Q: 可以手动选择评级吗？**
A: 不可以。评级由 IARC 系统根据您的问卷答案自动计算，无法手动设置。

### 参考链接

- [Google Play 内容分级官方文档](https://support.google.com/googleplay/android-developer/answer/9888179)
- [IARC 评级系统说明](https://www.globalratings.com/)

---

# Google Play 权限声明表单（Permissions Declaration Form）

## ⚠️ 重要：这是"Need attention"中未完成的项目

根据您的截图，内容评级已经完成，但"Need attention (1)"表示还有 **1 个声明需要完成**。这很可能是 **权限声明表单（Permissions Declaration Form）**。

### 需要声明的权限

根据 `AndroidManifest.xml` 分析，应用使用了以下敏感权限，需要在 Google Play Console 中声明：

#### 1. **位置权限（Location Permissions）** ⚠️ **必须声明**
- `ACCESS_FINE_LOCATION` - 精确位置
- `ACCESS_COARSE_LOCATION` - 大致位置
- `ACCESS_BACKGROUND_LOCATION` - **后台位置权限（需要特别声明）**

#### 2. **照片和视频权限（Photo and Video Permissions）** ⚠️ **必须声明**
- `CAMERA` - 相机权限
- `READ_MEDIA_IMAGES` - 读取图片（Android 13+）
- `READ_MEDIA_VIDEO` - 读取视频（Android 13+）
- `READ_EXTERNAL_STORAGE` - 读取外部存储（Android 12 及以下）

#### 3. **其他权限（通常不需要特别声明）**
- `RECORD_AUDIO` - 录音权限（用于语音消息）
- `INTERNET` - 网络权限（不需要声明）
- `READ_PHONE_STATE` - 读取手机状态（不需要声明）

### 如何完成权限声明

#### 步骤 1：进入权限声明页面

1. 登录 [Google Play Console](https://play.google.com/console)
2. 选择您的应用（ChinaTravel）
3. 在左侧菜单中，点击 **"政策"** → **"应用内容"** → **"权限声明"**（Permissions Declaration）
   - 或者直接访问：**"应用内容"** → **"权限声明"**

#### 步骤 2：填写位置权限声明

**1. 选择权限类型：**
- 选择 **"位置权限"**（Location permissions）
- 选择 **"后台位置"**（Background location）如果使用了 `ACCESS_BACKGROUND_LOCATION`

**2. 填写功能描述：**
使用文档中已准备好的内容：

**英文版本（500字符以内）：**
```
Our app uses location permission to provide interactive map features for travel destinations. When viewing sightseeing details or trip itineraries, the app displays maps showing exact locations of attractions, restaurants, and points of interest. This enhances travel planning by providing visual context and geographical layout. Location access is only used when users actively view map content, and this feature is clearly disclosed in our privacy policy.
```

**3. 上传视频说明（如果要求）：**
- 如果 Google 要求视频说明，请按照文档中的"视频说明"部分录制并上传
- 视频链接：[待上传后填写]

**4. 选择使用场景：**
- ✅ **前台位置访问**：用于显示地图
- ⚠️ **后台位置访问**（如果使用）：需要特别说明使用场景

#### 步骤 3：填写照片和视频权限声明

**1. 选择权限类型：**
- 选择 **"照片和视频权限"**（Photo and video permissions）

**2. 填写核心功能说明：**
使用文档中已准备好的内容：

**英文版本（500字符以内）：**
```
ChinaTravel uses photo and video permissions to enable essential communication features. Users can take photos or select images from their gallery to share with travel guides during chat conversations, upload images when submitting support requests, and set profile pictures. These permissions are only used when users actively choose to share photos or videos, and are essential for the app's core communication and user support functionality.
```

**3. 选择使用场景：**
- ✅ 聊天功能中的图片发送
- ✅ 用户支持功能中的图片上传
- ✅ 用户头像设置

#### 步骤 4：提交声明

1. 完成所有权限声明后，点击 **"保存"** 或 **"提交"**
2. 等待 Google 审核（通常需要几天时间）
3. 审核通过后，"Need attention"中的项目会消失

### 检查清单

在提交前，确保：

- [ ] 位置权限声明已填写并提交
- [ ] 照片和视频权限声明已填写并提交
- [ ] 所有功能描述都符合实际应用功能
- [ ] 视频说明已上传（如果要求）
- [ ] 隐私政策中已包含相关权限说明
- [ ] 应用内已实现权限披露对话框

### 常见问题

**Q: 为什么需要声明权限？**
A: Google Play 要求所有使用敏感权限的应用都必须说明权限的用途，以保护用户隐私。

**Q: 审核需要多长时间？**
A: 通常需要 1-3 个工作日，复杂情况可能需要更长时间。

**Q: 如果审核被拒绝怎么办？**
A: Google 会提供拒绝原因，根据反馈修改声明内容后重新提交。

**Q: 后台位置权限必须声明吗？**
A: 是的，`ACCESS_BACKGROUND_LOCATION` 是敏感权限，必须详细说明使用场景。

### 参考链接

- [Google Play 权限声明官方文档](https://support.google.com/googleplay/android-developer/answer/9888170)
- [后台位置权限声明指南](https://support.google.com/googleplay/android-developer/answer/9799150)

---

# Google Play 设备目录（Device Catalogue）

## 页面说明

"设备目录"页面用于查看和管理应用兼容的设备。您可以查看哪些设备可以安装您的应用，哪些设备被排除，以及设备的兼容性状态。

## 关键概念

### 1. 设备类型筛选

页面顶部的下拉菜单可以筛选不同类型的设备：

- **所有设备（All devices）**：设备目录中的所有设备
- **支持的设备（Supported devices）**：应用清单文件（AndroidManifest.xml）中声明支持的设备。这些设备的用户可以在 Google Play 上发现并安装您的应用（前提是这些设备没有被排除）
- **排除的设备（Excluded devices）**：您手动排除或通过规则排除的设备。这些设备的用户无法在 Google Play 上发现或安装您的应用。设备排除会覆盖应用清单中声明的支持设备
- **目标设备（Targeted devices）**：用户可以在 Google Play 上发现并安装您的应用的设备（前提是这些设备也在您支持的国家/地区）。目标设备 = 支持的设备 - 排除的设备

### 2. 设备状态（Targeting status）

表格中的"Targeting status"列显示每个设备的兼容性状态：

#### ✅ **支持的（Supported）**
- 应用支持该设备
- 用户可以在 Google Play 上看到并安装应用

#### ❌ **不支持的（Unsupported）**
- 应用不支持该设备
- 可能的原因：
  - 设备不满足应用的最低要求（如 Android 版本、RAM、硬件功能等）
  - 应用清单文件中声明了该设备不支持的功能
- 用户无法在 Google Play 上安装应用

#### ⚠️ **未选择加入（Not opted in）**
- 设备可能支持，但您还没有明确选择支持该设备
- 通常出现在新设备或特殊设备上
- 需要手动选择是否支持

#### 🔒 **已排除（Excluded）**
- 您手动或通过规则排除了该设备
- 即使用户设备满足要求，也无法安装应用

## 设备信息列

表格显示以下设备信息：

- **设备名称**：设备型号（如 OPPO PBFMOO）
- **Android 版本**：设备运行的 Android 版本（如 11, 8.1, 16 Beta）
- **RAM**：设备内存大小（如 1.6-1.7 GB, 8.0-8.1 GB）
- **芯片（System on Chip）**：设备使用的处理器（如 Qualcomm SDM450, Spreadtrum SC7731E）
- **Targeting status**：兼容性状态（如上所述）

## 常用操作

### 1. 管理设备排除规则

点击右上角的 **"Manage exclusion rules"** 可以：
- 设置自动排除规则（如排除特定 RAM 大小以下的设备）
- 手动排除特定设备
- 查看和管理现有的排除规则

### 2. 导出设备列表

点击 **"Export device list"** 可以：
- 导出设备列表为 CSV 文件
- 用于分析和报告

### 3. 搜索和筛选设备

- 使用 **"Search devices"** 搜索特定设备
- 使用 **"Add filter"** 添加筛选条件（如按 Android 版本、RAM、芯片等筛选）

### 4. 管理设备

点击 **"Manage devices"** 可以：
- 批量选择设备
- 手动添加或移除设备支持
- 设置设备排除规则

## 重要提示

1. **默认行为**：如果应用清单文件中没有明确声明不支持某个设备，Google Play 会默认支持该设备（如果设备满足最低要求）

2. **排除优先级**：设备排除会覆盖应用清单中的支持声明

3. **影响范围**：排除设备会影响该设备用户发现和安装应用的能力

4. **谨慎排除**：除非有特殊原因（如设备性能问题、兼容性问题），否则不建议排除设备，这可能会减少潜在用户

5. **定期检查**：建议定期检查设备目录，确保应用支持尽可能多的设备

## 常见问题

**Q: 为什么有些设备显示"Unsupported"？**
A: 可能是因为设备不满足应用的最低要求（如 Android 版本太低、RAM 不足、缺少必需的硬件功能等）。

**Q: 如何让更多设备支持我的应用？**
A: 
- 降低应用的最低 Android 版本要求（在 `build.gradle` 中调整 `minSdk`）
- 移除不必要的硬件功能要求（在 `AndroidManifest.xml` 中检查 `<uses-feature>`）
- 检查并移除不必要的权限要求

**Q: "Not opted in" 是什么意思？**
A: 这通常表示新设备或特殊设备，Google Play 不确定您的应用是否支持。您可以手动选择是否支持这些设备。

**Q: 排除设备会影响现有用户吗？**
A: 排除设备只会影响新安装，不会影响已经安装应用的用户。

### 参考链接

- [Google Play 设备目录官方文档](https://support.google.com/googleplay/android-developer/answer/7353455)
- [管理设备兼容性](https://support.google.com/googleplay/android-developer/answer/9888177)

---

# Android 硬件功能声明说明

## android.hardware.autofocus 的作用

### 基本概念

`android.hardware.autofocus` 是一个硬件功能声明，用于告诉 Android 系统和 Google Play 商店：**您的应用需要设备支持相机自动对焦功能**。

### 在您的项目中的使用

在 `AndroidManifest.xml` 中，您声明了：

```23:24:app/src/main/AndroidManifest.xml
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.autofocus" /> <!-- 声网 -->
```

### 具体作用

#### 1. **设备过滤（Device Filtering）**
- Google Play 会根据这个声明过滤设备
- **只有支持自动对焦的设备**才能在 Google Play 上看到并安装您的应用
- 不支持自动对焦的设备会被标记为"Unsupported"

#### 2. **运行时检查**
- 在代码中，您可以使用 `PackageManager.hasSystemFeature()` 检查设备是否支持自动对焦：
```kotlin
val hasAutofocus = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)
```

#### 3. **相机功能要求**
- 自动对焦功能主要用于：
  - **视频通话**：声网（Agora）SDK 在视频通话时需要自动对焦来保持画面清晰
  - **拍照功能**：确保拍摄的照片清晰对焦
  - **视频录制**：录制视频时自动调整焦点

### 为什么需要这个声明？

从注释 `<!-- 声网 -->` 可以看出，这是**声网（Agora）SDK 的要求**。声网 SDK 用于实时音视频通话，需要自动对焦功能来：

1. **视频通话质量**：在视频通话时自动对焦，确保画面清晰
2. **用户体验**：避免用户手动对焦，提供更好的通话体验
3. **SDK 要求**：声网 SDK 可能依赖这个功能来正常工作

### 对应用分发的影响

#### ✅ **优点**
- 确保应用在支持的设备上正常运行
- 避免在不支持自动对焦的设备上出现功能问题
- 提升视频通话和拍照的用户体验

#### ⚠️ **潜在影响**
- **可能减少支持的设备数量**：一些低端设备或旧设备可能不支持自动对焦
- **设备兼容性**：在"设备目录"页面中，不支持自动对焦的设备会显示为"Unsupported"

### 如何判断是否需要这个声明？

#### 需要保留的情况：
- ✅ 应用使用声网（Agora）SDK 进行视频通话
- ✅ 拍照功能需要自动对焦
- ✅ 视频录制功能需要自动对焦
- ✅ 应用的核心功能依赖自动对焦

#### 可以考虑移除的情况：
- ❌ 应用只是偶尔使用相机，不需要自动对焦
- ❌ 应用只使用前置摄像头（通常不需要自动对焦）
- ❌ 希望支持更多设备（包括不支持自动对焦的设备）

### 如果移除这个声明会怎样？

如果移除 `android.hardware.autofocus` 声明：

1. **更多设备可以安装应用**：不支持自动对焦的设备也能在 Google Play 上看到应用
2. **需要代码处理**：在代码中需要检查设备是否支持自动对焦，如果不支持则禁用相关功能
3. **可能影响功能**：如果声网 SDK 确实需要自动对焦，可能会影响视频通话质量

### 建议

根据您的应用情况：

1. **如果视频通话是核心功能**：保留这个声明，确保视频通话质量
2. **如果只是偶尔使用相机**：可以考虑移除，并添加运行时检查
3. **如果希望支持更多设备**：可以移除，但需要在代码中处理不支持自动对焦的情况

### 相关代码检查

您可以在代码中检查是否真的需要这个功能：

```kotlin
// 检查设备是否支持自动对焦
fun hasAutofocus(): Boolean {
    return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)
}

// 在使用相机前检查
if (hasAutofocus()) {
    // 使用自动对焦功能
} else {
    // 使用固定焦点或手动对焦
}
```

### 参考链接

- [Android 硬件功能声明官方文档](https://developer.android.com/guide/topics/manifest/uses-feature-element)
- [相机自动对焦功能](https://developer.android.com/reference/android/hardware/Camera.Parameters#FOCUS_MODE_AUTO)

---

## ACCESS_WIFI_STATE 权限说明

### 基本概念

`ACCESS_WIFI_STATE` 是一个**普通权限（Normal Permission）**，用于允许应用访问 WiFi 网络状态信息。这是一个**只读权限**，只能查看 WiFi 状态，不能修改 WiFi 设置。

### 在您的项目中的使用

在 `AndroidManifest.xml` 中，您声明了：

```26:26:app/src/main/AndroidManifest.xml
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

### 具体作用

#### 1. **检查 WiFi 连接状态**
在 `NetworkUtil.java` 中，用于检查设备是否连接到 WiFi：

```89:110:common/common_http/src/main/java/com/example/http/util/NetworkUtil.java
    public static boolean isWifiConnected() {
        return isWifiConnected(App.getContext());
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null) {
            return false;
        }
        NetworkInfo networkInfo = null;
        try {
            // maybe throw exception in android framework
            networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // can not use pingSupplicant (), on cm9 or some other roms it will
        // block whole wifi network!
        return (networkInfo != null && networkInfo.isConnected());
    }
```

#### 2. **网络定位功能**
在 `common_base` 模块的注释中说明，用于获取 WiFi 信息进行网络定位：

```22:23:common/common_base/src/main/AndroidManifest.xml
    <!--用于访问wifi网络信息，wifi信息会用于进行网络定位-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

#### 3. **声网 SDK 需求**
声网（Agora）SDK 可能需要这个权限来：
- 检测网络类型（WiFi 或移动网络）
- 优化音视频通话质量
- 根据网络状态调整传输策略

### 权限特点

#### ✅ **普通权限（Normal Permission）**
- **不需要运行时请求**：在 AndroidManifest.xml 中声明即可
- **自动授予**：系统会自动授予此权限
- **用户无感知**：用户不会看到权限请求对话框

#### ✅ **只读权限**
- 只能**查看** WiFi 状态信息
- **不能修改** WiFi 设置（需要 `CHANGE_WIFI_STATE` 权限）
- **不能连接/断开** WiFi（需要 `CHANGE_WIFI_STATE` 权限）

### 可以获取的信息

使用 `ACCESS_WIFI_STATE` 权限可以获取：

1. **WiFi 连接状态**：是否已连接到 WiFi
2. **WiFi 信息**：
   - SSID（WiFi 名称）
   - BSSID（WiFi MAC 地址）
   - 信号强度
   - IP 地址
   - 网络速度
3. **WiFi 列表**：扫描到的 WiFi 网络列表（需要位置权限）

### 与 ACCESS_NETWORK_STATE 的区别

| 权限 | 作用范围 | 用途 |
|------|---------|------|
| `ACCESS_NETWORK_STATE` | 所有网络类型（WiFi、移动网络、以太网等） | 检查网络连接状态 |
| `ACCESS_WIFI_STATE` | 仅 WiFi 网络 | 获取 WiFi 详细信息 |

**注意**：通常 `ACCESS_NETWORK_STATE` 就足够检查网络状态了，但如果需要获取 WiFi 的详细信息（如 SSID、BSSID），则需要 `ACCESS_WIFI_STATE`。

### 在您的项目中的使用场景

1. **网络状态检查**：`NetworkUtil.isWifiConnected()` 检查是否连接到 WiFi
2. **网络定位**：获取 WiFi 信息用于辅助定位
3. **声网 SDK**：优化音视频通话的网络策略

### Google Play 要求

#### ✅ **不需要特别声明**
- `ACCESS_WIFI_STATE` 是普通权限
- **不需要**在 Google Play Console 中填写权限声明表单
- **不需要**在隐私政策中特别说明（除非您收集并存储了 WiFi 信息）

#### ⚠️ **注意事项**
- 如果应用**收集并存储**了 WiFi 信息（如 SSID、BSSID），建议在隐私政策中说明
- 如果用于**网络定位**，需要说明位置权限的用途

### 是否可以移除？

#### ❌ **不建议移除**
- 应用需要检查 WiFi 连接状态
- 网络定位功能需要 WiFi 信息
- 声网 SDK 可能需要这个权限

#### ✅ **如果确实不需要**
如果应用完全不需要 WiFi 相关信息，可以移除，但需要：
- 移除 `NetworkUtil.isWifiConnected()` 中对 WiFi 状态的检查
- 确保网络定位功能不依赖 WiFi 信息
- 确认声网 SDK 不依赖此权限

### 相关权限对比

| 权限 | 类型 | 是否需要运行时请求 | 用途 |
|------|------|------------------|------|
| `ACCESS_WIFI_STATE` | 普通权限 | ❌ 否 | 查看 WiFi 状态 |
| `CHANGE_WIFI_STATE` | 普通权限 | ❌ 否 | 修改 WiFi 设置（连接/断开） |
| `ACCESS_NETWORK_STATE` | 普通权限 | ❌ 否 | 查看所有网络状态 |
| `ACCESS_FINE_LOCATION` | 危险权限 | ✅ 是 | 获取精确位置（WiFi 扫描需要） |

### 参考链接

- [Android WiFi 权限官方文档](https://developer.android.com/reference/android/Manifest.permission#ACCESS_WIFI_STATE)
- [网络状态检查最佳实践](https://developer.android.com/training/basics/network-ops/managing)




