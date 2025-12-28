# Google Play 位置权限视频制作指南

根据 [Google Play官方文档](https://support.google.com/googleplay/android-developer/answer/9799150#video) 的要求

## 📹 视频要求总览

### 基本要求
- **视频时长：** 30秒或更短（推荐）
- **视频格式：** YouTube链接（首选）或 Google Drive链接（MP4等常见格式）
- **设备要求：** 必须在Android设备上录制，不能使用iOS应用

### 必须包含的元素

视频必须包含以下**三个关键元素**：

1. **从后台激活的功能**
   - 展示功能在应用关闭或不在前台时如何工作
   - 演示后台位置访问的实际使用场景

2. **显著的应用内披露对话框（Prominent Disclosure）**
   - 显示在请求系统权限前向用户显示的说明对话框
   - 展示对话框的内容，说明为什么需要位置权限

3. **运行时权限提示（Runtime Prompt）**
   - 显示Android系统的位置权限请求对话框
   - 展示用户同意权限的过程

---

## 🎬 视频制作步骤

### 步骤1：准备录制环境
1. 使用Android设备（手机或平板）
2. 确保应用已安装最新版本
3. 清除应用数据，确保是首次使用状态
4. 准备好屏幕录制工具（Android系统自带或第三方工具）

### 步骤2：录制流程

**场景A：如果功能在后台有可见效果（推荐）**

1. **开场（0-5秒）**
   - 显示应用图标或主界面
   - 简要说明："This video demonstrates the interactive map feature"

2. **展示显著披露对话框（5-10秒）**
   - 打开应用
   - 显示位置权限说明对话框（我们已实现的 `LocationPermissionDisclosureHelper`）
   - 展示对话框内容，包括：
     - 标题："Location Permission Request"
     - 说明文字（解释为什么需要位置权限）
     - "Allow"和"Deny"按钮

3. **用户同意并触发系统权限（10-15秒）**
   - 点击"Allow"按钮
   - 显示Android系统的位置权限请求对话框
   - 用户点击"Allow"或"While using the app"

4. **展示功能使用（15-25秒）**
   - 进入景点详情页或行程详情页
   - 展示地图功能
   - 展示地图上显示的景点位置标记
   - 展示地图的交互功能（缩放、拖动等）
   - 说明："Location permission enables interactive maps showing attraction locations"

5. **后台功能演示（25-30秒）**（如果适用）
   - 按Home键返回主屏幕（应用进入后台）
   - 说明功能在后台的使用（如果适用）
   - 或者说明："Location is only used when viewing map content"

**场景B：如果功能在后台没有可见界面**

如果您的应用主要使用前台位置权限（在Activity中显示地图时），但声明了后台位置权限，需要：

1. 在视频中明确说明功能的使用场景
2. 展示地图功能在应用打开时的使用
3. 在声明表单中注明："This feature uses location when users actively view map content in the app"

---

## 📋 视频脚本示例

### 英文脚本：

```
[0-3秒] 显示应用启动画面
"This video demonstrates ChinaTravel's interactive map feature."

[3-8秒] 显示位置权限说明对话框
"Before requesting location permission, the app shows a disclosure dialog explaining why location is needed."

[8-12秒] 用户点击Allow，显示系统权限请求
"Users can see what features require location access."

[12-20秒] 进入景点详情页，展示地图
"The app uses location to display interactive maps showing attractions, restaurants, and points of interest."

[20-25秒] 展示地图交互功能
"Users can zoom and pan to explore destinations."

[25-30秒] 总结
"Location access enhances travel planning by providing visual context and geographical information."
```

### 中文脚本：

```
[0-3秒] 显示应用启动画面
"本视频演示ChinaTravel的交互式地图功能"

[3-8秒] 显示位置权限说明对话框
"在请求位置权限前，应用会显示说明对话框，解释为什么需要位置权限"

[8-12秒] 用户点击允许，显示系统权限请求
"用户可以了解哪些功能需要位置访问"

[12-20秒] 进入景点详情页，展示地图
"应用使用位置权限来显示交互式地图，展示景点、餐厅和兴趣点的位置"

[20-25秒] 展示地图交互功能
"用户可以缩放和拖动地图来探索目的地"

[25-30秒] 总结
"位置访问通过提供视觉上下文和地理信息，增强旅行规划体验"
```

---

## ✅ 视频检查清单

在提交视频前，确保：

- [ ] 视频时长在30秒以内
- [ ] 在Android设备上录制
- [ ] 包含显著披露对话框
- [ ] 包含系统运行时权限提示
- [ ] 清晰展示地图功能
- [ ] 视频链接可访问（YouTube或Google Drive）
- [ ] 视频质量清晰，可以看清界面内容
- [ ] 视频展示了声明的功能（交互式地图）

---

## 🎯 针对您的应用的特殊说明

根据代码分析，您的应用：
- **主要使用前台位置权限**（在Activity中显示地图时）
- **已实现显著披露对话框**（`LocationPermissionDisclosureHelper`）
- **地图功能在应用打开时使用**

### 视频重点：

1. **强调前台使用**：在视频中说明位置权限主要用于在应用打开时显示地图
2. **展示披露对话框**：确保视频中清晰显示我们实现的说明对话框
3. **展示地图功能**：展示景点详情页和行程详情页的地图功能
4. **说明使用场景**：明确说明位置权限仅在用户查看地图内容时使用

---

## 📝 视频描述建议

在YouTube视频描述中可以添加：

```
This video demonstrates ChinaTravel's interactive map feature that requires location permission. The app shows a prominent disclosure dialog before requesting location access, explaining that location is used to display maps with attractions, restaurants, and points of interest. Location access is only used when users actively view map content in the app.
```

---

## 🔗 上传和提交

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

---

## ⚠️ 常见错误避免

1. ❌ 不要使用iOS应用录制
2. ❌ 不要跳过显著披露对话框
3. ❌ 不要超过30秒（尽量控制在30秒内）
4. ❌ 不要使用模拟器（如果可能，使用真实设备）
5. ❌ 不要忘记展示系统权限请求对话框
6. ❌ 不要使用模糊或低质量的视频

---

## 💡 提示

- 可以多次录制，选择最好的版本
- 确保设备屏幕亮度足够，便于观看
- 可以添加简单的文字说明（但不要遮挡重要内容）
- 如果功能复杂，可以放慢操作速度，确保观众能看清

