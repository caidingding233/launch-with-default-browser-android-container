# 鸿蒙浏览器转发器

这是一个Android应用，用于在鸿蒙系统的安卓容器（如出境易、卓易通）中拦截浏览器Intent并转发到系统设定的**默认浏览器（如海泰浏览器）。

## 功能特性

- ✅ 拦截所有http/https的浏览器Intent
- ✅ 自动转发到系统默认浏览器
- ✅ 可设置为默认浏览器选项
- ✅ 完整保留原始Intent信息（URL、extras、flags等）
- ✅ 详细的日志记录便于调试

## 工作原理

1. **拦截Intent**：应用在AndroidManifest中声明为浏览器，可以拦截`ACTION_VIEW`的http/https链接
2. **转发Intent**：将拦截到的Intent完整转发给其他真实浏览器
3. **自动关闭**：转发后自动关闭，用户无感知

这样当你在鸿蒙系统中设置了海泰浏览器为默认浏览器，安卓容器内的链接也会自动跳转到海泰浏览器。

## 使用方法

### 方式一：下载预构建的APK（推荐）

1. 前往 [Releases](../../releases) 页面
2. 下载最新版本的 `app-debug.apk`
3. 安装到设备：
```bash
adb install app-debug.apk
```

### 方式二：自己构建

1. 克隆仓库：
```bash
git clone <仓库地址>
cd launch-with-default-browser-android-container
```

2. 构建APK：
```bash
./gradlew assembleDebug
```

3. 安装到设备：
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 配置步骤

1. 在安卓容器系统设置中，将此应用设置为**默认浏览器**
2. 在鸿蒙系统中，将海泰浏览器设置为默认浏览器

### 使用效果

- 在安卓容器内点击任何网页链接
- 此应用自动拦截并转发
- 最终打开的是鸿蒙系统的海泰浏览器，而不是华为浏览器

## 日志查看

应用会输出详细的日志，可以通过以下命令查看运行状态：

```bash
adb logcat | grep BrowserForwarder
```

日志内容包括：
- 接收到的Intent信息（action、data、extras、flags）
- 找到的可用浏览器数量
- 最终启动的浏览器

## 技术细节

- **Intent过滤器**：支持http和https协议
- **Intent转发**：完整复制原始Intent，包括所有extras和flags
- **自动过滤**：自动过滤掉自己，避免循环调用
- **智能选择**：如果有多个浏览器，会显示选择器；如果只有一个，直接启动

## 注意事项

- ⚠️ 必须在安卓容器系统设置中将此应用设置为默认浏览器
- ⚠️ 在鸿蒙系统中设置海泰浏览器为默认浏览器
- ⚠️ 此应用本身不是浏览器，只是一个转发器

## 自动构建

本项目配置了GitHub Actions自动构建：

- **推送到main分支**：自动构建并创建Release，包含Debug和Release版本的APK
- **创建Tag**：推送版本标签（如`v1.0.0`）时会创建正式版本Release

### 创建正式版本

```bash
git tag v1.0.0
git push origin v1.0.0
```

## 许可证

MIT License
