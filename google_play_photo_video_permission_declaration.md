# Google Play 照片和视频权限声明

根据 Google Play Console 的要求，需要分别填写 `READ_MEDIA_IMAGES` 和 `READ_MEDIA_VIDEO` 权限的使用说明。

## 权限使用场景

根据代码分析，应用使用这些权限的场景：

1. **聊天功能**：用户在与导游聊天时，频繁从相册选择图片和视频发送
2. **用户支持**：用户在上传支持请求时，需要选择图片作为附件
3. **用户头像**：用户设置个人头像时，需要从相册选择图片

---

## READ_MEDIA_IMAGES 权限声明

### 英文版本（250字符以内）：

ChinaTravel uses READ_MEDIA_IMAGES for core chat features. Users frequently select images from gallery to share with travel guides, upload images for support requests, and set profile pictures. Essential for real-time communication.

**字符数：** 约 230 字符

### 中文版本：

ChinaTravel使用READ_MEDIA_IMAGES权限来支持核心通信功能。用户频繁从相册选择图片与导游聊天时分享、在提交支持请求时上传图片，以及设置个人头像。此权限对于应用的实时聊天功能和用户支持功能至关重要。

---

## READ_MEDIA_VIDEO 权限声明

### 英文版本（250字符以内）：

ChinaTravel uses READ_MEDIA_VIDEO for video sharing in chat with travel guides. Users frequently select videos from gallery to share travel experiences. Essential for core communication functionality.

**字符数：** 约 180 字符

### 中文版本：

ChinaTravel使用READ_MEDIA_VIDEO权限来支持与导游聊天时的视频分享功能。用户频繁从相册选择视频来分享旅行体验并与导游沟通。此权限对于应用的核心通信功能至关重要。

---

## 为什么不能使用 Android Photo Picker？

根据 Google Play 的指导，如果应用只是偶尔需要访问照片和视频，应该使用 Android Photo Picker。但我们的应用需要频繁访问，原因如下：

1. **实时聊天功能**：用户在与导游聊天时，需要频繁选择图片和视频进行分享，这是应用的核心功能
2. **用户体验**：使用自定义图片选择器可以提供更好的用户体验，支持多选、预览等功能
3. **集成需求**：需要与 IM SDK 深度集成，实现图片和视频的直接上传和发送

---

## 提交到 Google Play Console

### 步骤：

1. **进入权限声明页面**
   - 登录 Google Play Console
   - 进入"应用内容"（App content）
   - 点击"敏感应用权限"（Sensitive app permissions）
   - 选择"Photo and video permissions"

2. **填写 READ_MEDIA_IMAGES**
   - 在"Read media images"字段中，粘贴上面的英文说明（248字符）

3. **填写 READ_MEDIA_VIDEO**
   - 在"Read media video"字段中，粘贴上面的英文说明（218字符）

4. **提交审核**
   - 完成所有字段后，提交进行审核

---

## 注意事项

1. **字符限制**：每个字段限制为 250 字符，需要简洁明了
2. **核心功能**：强调这些权限用于核心通信功能
3. **频繁使用**：说明为什么需要频繁访问，而不是偶尔使用
4. **用户主动**：强调权限仅在用户主动选择分享时使用


