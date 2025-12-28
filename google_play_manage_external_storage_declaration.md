# Google Play "All files access" 权限声明

## 权限说明

**权限名称：** `android.permission.MANAGE_EXTERNAL_STORAGE`  
**权限用途：** 所有文件访问权限

---

## 1. 功能描述（All files access）

### 英文版本（500字符以内）：

ChinaTravel uses the all files access permission to enable core chat functionality through the integrated IM SDK. The app needs to download, store, and manage chat attachments (images, videos, audio messages, and documents) sent between users and travel guides. These files must be saved to external storage and accessed directly by file path for efficient message delivery and offline access. The IM SDK requires direct file system access to manage attachment directories, handle file downloads, and ensure reliable message synchronization across devices.

**字符数：** 约 485 字符

### 中文版本：

ChinaTravel使用所有文件访问权限来支持核心聊天功能。应用需要下载、存储和管理用户与导游之间发送的聊天附件（图片、视频、语音消息和文档）。这些文件必须保存到外部存储，并通过直接文件路径访问，以确保高效的消息传递和离线访问。IM SDK需要直接文件系统访问来管理附件目录、处理文件下载，并确保跨设备的消息同步。

---

## 2. 使用原因（Usage）

选择以下选项：

✅ **Core functionality**（核心功能）

**说明：** 聊天功能是应用的核心功能之一，用户需要通过聊天与导游沟通，发送和接收各种类型的附件。所有文件访问权限对于实现可靠的聊天附件管理是必需的。

---

## 3. 技术原因（Technical reason）

### 英文版本：

The IM SDK requires direct file system access to manage chat attachments efficiently. Storage Access Framework (SAF) and Media Store API are insufficient because:

1. **Batch File Management**: The IM SDK needs to download, organize, and manage multiple attachment files (images, videos, audio, documents) simultaneously. SAF requires user interaction for each file operation, which is impractical for automated background downloads.

2. **Direct File Path Access**: The SDK requires direct file paths to access attachments for message rendering, caching, and synchronization. Media Store API only provides URIs, which cannot be used directly by the SDK's file management system.

3. **Directory Structure**: The SDK maintains a specific directory structure for organizing attachments by type (images, videos, audio, documents) and conversation. This structure requires direct file system access that SAF cannot provide.

4. **Offline Access**: Users need to access downloaded attachments offline. Direct file path access ensures reliable offline functionality, while SAF-based solutions may fail when files are moved or when the app is not in foreground.

5. **Performance Requirements**: Real-time chat requires fast file access for message rendering. Direct file system access provides the performance needed for smooth chat experience, which SAF cannot match due to its abstraction layer overhead.

**字符数：** 约 980 字符

### 中文版本：

IM SDK需要直接文件系统访问来高效管理聊天附件。存储访问框架（SAF）和媒体存储API不足的原因如下：

1. **批量文件管理**：IM SDK需要同时下载、组织和管理多个附件文件（图片、视频、语音、文档）。SAF要求每个文件操作都需要用户交互，这对于自动化的后台下载来说是不切实际的。

2. **直接文件路径访问**：SDK需要直接文件路径来访问附件，用于消息渲染、缓存和同步。媒体存储API只提供URI，无法被SDK的文件管理系统直接使用。

3. **目录结构**：SDK维护特定的目录结构，按类型（图片、视频、语音、文档）和会话组织附件。这种结构需要直接文件系统访问，而SAF无法提供。

4. **离线访问**：用户需要离线访问已下载的附件。直接文件路径访问确保可靠的离线功能，而基于SAF的解决方案在文件移动或应用不在前台时可能会失败。

5. **性能要求**：实时聊天需要快速文件访问以进行消息渲染。直接文件系统访问提供了流畅聊天体验所需的性能，而SAF由于其抽象层开销无法匹配。

---

## 4. 使用场景

根据代码分析，`MANAGE_EXTERNAL_STORAGE` 权限用于以下场景：

1. **聊天附件下载**
   - 位置：`V2THolderMessageUtil.kt`
   - 功能：下载聊天中的图片、视频、语音消息和文档附件

2. **文件存储管理**
   - 位置：`FileUtil.java`, `UIConfig.java`
   - 功能：管理下载的附件文件，按类型组织到不同目录（图片、视频、语音、文档）

3. **文件访问和打开**
   - 位置：`FileUtil.java` - `openFile()` 方法
   - 功能：打开和查看下载的附件文件

4. **离线消息同步**
   - 功能：确保已下载的附件可以在离线状态下访问，支持跨设备消息同步

---

## 5. 提交到 Google Play Console

### 步骤：

1. **进入权限声明页面**
   - 登录 Google Play Console
   - 进入"应用内容"（App content）
   - 点击"敏感应用权限"（Sensitive app permissions）
   - 选择"All files access permission"

2. **填写功能描述**
   - 在"All files access"字段中，粘贴上面的英文功能描述（485字符）

3. **选择使用原因**
   - 勾选"Core functionality"

4. **填写技术原因**
   - 在"Technical reason"字段中，粘贴上面的英文技术原因说明（980字符）

5. **提交审核**
   - 完成所有字段后，提交进行审核

---

## 6. 注意事项

1. **只描述一个功能**：Google要求只描述一个主要功能，我们选择了"聊天附件管理"作为核心功能。

2. **批准范围**：一旦批准，将授予整个应用的所有文件访问权限，而不仅仅是描述的功能。

3. **应用内披露**：根据Google Play的要求，可能需要在应用内添加显著披露，说明为什么需要所有文件访问权限。建议参考位置权限的披露实现方式。

4. **隐私政策**：确保隐私政策中包含了所有文件访问权限的使用说明。

---

## 7. 应用内披露实现

### ✅ 已实现

**实现位置：**
- `StoragePermissionDisclosureHelper.kt` - 所有文件访问权限披露工具类
- `dialog_storage_permission_disclosure.xml` - 披露对话框布局

**功能：**
- 在请求所有文件访问权限前，显示自定义披露对话框
- 说明权限用途：用于下载和管理聊天附件
- 说明数据保护：文件仅用于应用内聊天功能，不会与第三方共享
- 符合Google Play的显著披露要求

**使用方式：**

```kotlin
// 在需要请求所有文件访问权限时
StoragePermissionDisclosureHelper.showStoragePermissionDisclosure(
    this,
    onAllow = {
        // 用户同意后，请求系统权限或引导到设置页面
        // 注意：MANAGE_EXTERNAL_STORAGE 需要通过系统设置页面授予
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            XXPermissions.startPermissionActivity(this, listOf(Permission.MANAGE_EXTERNAL_STORAGE))
        }
    },
    onDeny = {
        // 用户拒绝，不执行任何操作
    }
)
```

**注意：** `MANAGE_EXTERNAL_STORAGE` 是特殊权限，在 Android 11 (API 30) 及以上版本需要通过系统设置页面手动授予，不能通过运行时权限对话框直接授予。
